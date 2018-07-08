/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Modelo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Marca;

/**
 *
 * @author Ussimane
 */
public class MarcaJpaController implements Serializable {

    public MarcaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        if (marca.getModeloList() == null) {
            marca.setModeloList(new ArrayList<Modelo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Modelo> attachedModeloList = new ArrayList<Modelo>();
            for (Modelo modeloListModeloToAttach : marca.getModeloList()) {
                modeloListModeloToAttach = em.getReference(modeloListModeloToAttach.getClass(), modeloListModeloToAttach.getIdmodelo());
                attachedModeloList.add(modeloListModeloToAttach);
            }
            marca.setModeloList(attachedModeloList);
            em.persist(marca);
            for (Modelo modeloListModelo : marca.getModeloList()) {
                Marca oldMarcaOfModeloListModelo = modeloListModelo.getMarca();
                modeloListModelo.setMarca(marca);
                modeloListModelo = em.merge(modeloListModelo);
                if (oldMarcaOfModeloListModelo != null) {
                    oldMarcaOfModeloListModelo.getModeloList().remove(modeloListModelo);
                    oldMarcaOfModeloListModelo = em.merge(oldMarcaOfModeloListModelo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getIdmarca());
            List<Modelo> modeloListOld = persistentMarca.getModeloList();
            List<Modelo> modeloListNew = marca.getModeloList();
            List<Modelo> attachedModeloListNew = new ArrayList<Modelo>();
            for (Modelo modeloListNewModeloToAttach : modeloListNew) {
                modeloListNewModeloToAttach = em.getReference(modeloListNewModeloToAttach.getClass(), modeloListNewModeloToAttach.getIdmodelo());
                attachedModeloListNew.add(modeloListNewModeloToAttach);
            }
            modeloListNew = attachedModeloListNew;
            marca.setModeloList(modeloListNew);
            marca = em.merge(marca);
            for (Modelo modeloListOldModelo : modeloListOld) {
                if (!modeloListNew.contains(modeloListOldModelo)) {
                    modeloListOldModelo.setMarca(null);
                    modeloListOldModelo = em.merge(modeloListOldModelo);
                }
            }
            for (Modelo modeloListNewModelo : modeloListNew) {
                if (!modeloListOld.contains(modeloListNewModelo)) {
                    Marca oldMarcaOfModeloListNewModelo = modeloListNewModelo.getMarca();
                    modeloListNewModelo.setMarca(marca);
                    modeloListNewModelo = em.merge(modeloListNewModelo);
                    if (oldMarcaOfModeloListNewModelo != null && !oldMarcaOfModeloListNewModelo.equals(marca)) {
                        oldMarcaOfModeloListNewModelo.getModeloList().remove(modeloListNewModelo);
                        oldMarcaOfModeloListNewModelo = em.merge(oldMarcaOfModeloListNewModelo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getIdmarca();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getIdmarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<Modelo> modeloList = marca.getModeloList();
            for (Modelo modeloListModelo : modeloList) {
                modeloListModelo.setMarca(null);
                modeloListModelo = em.merge(modeloListModelo);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public int existeMarca(String s){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Marca p where p.descricao = :i");
            q.setParameter("i", s);
            return ((Long)q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
