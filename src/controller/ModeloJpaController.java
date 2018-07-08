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
import modelo.Marca;
import modelo.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Modelo;

/**
 *
 * @author Ussimane
 */
public class ModeloJpaController implements Serializable {

    public ModeloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modelo modelo) {
        if (modelo.getProdutoList() == null) {
            modelo.setProdutoList(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marca = modelo.getMarca();
            if (marca != null) {
                marca = em.getReference(marca.getClass(), marca.getIdmarca());
                modelo.setMarca(marca);
            }
            List<Produto> attachedProdutoList = new ArrayList<Produto>();
            for (Produto produtoListProdutoToAttach : modelo.getProdutoList()) {
                produtoListProdutoToAttach = em.getReference(produtoListProdutoToAttach.getClass(), produtoListProdutoToAttach.getIdproduto());
                attachedProdutoList.add(produtoListProdutoToAttach);
            }
            modelo.setProdutoList(attachedProdutoList);
            em.persist(modelo);
            if (marca != null) {
                marca.getModeloList().add(modelo);
                marca = em.merge(marca);
            }
            for (Produto produtoListProduto : modelo.getProdutoList()) {
                Modelo oldModeloOfProdutoListProduto = produtoListProduto.getModelo();
                produtoListProduto.setModelo(modelo);
                produtoListProduto = em.merge(produtoListProduto);
                if (oldModeloOfProdutoListProduto != null) {
                    oldModeloOfProdutoListProduto.getProdutoList().remove(produtoListProduto);
                    oldModeloOfProdutoListProduto = em.merge(oldModeloOfProdutoListProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modelo modelo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelo persistentModelo = em.find(Modelo.class, modelo.getIdmodelo());
            Marca marcaOld = persistentModelo.getMarca();
            Marca marcaNew = modelo.getMarca();
            List<Produto> produtoListOld = persistentModelo.getProdutoList();
            List<Produto> produtoListNew = modelo.getProdutoList();
            if (marcaNew != null) {
                marcaNew = em.getReference(marcaNew.getClass(), marcaNew.getIdmarca());
                modelo.setMarca(marcaNew);
            }
            List<Produto> attachedProdutoListNew = new ArrayList<Produto>();
            for (Produto produtoListNewProdutoToAttach : produtoListNew) {
                produtoListNewProdutoToAttach = em.getReference(produtoListNewProdutoToAttach.getClass(), produtoListNewProdutoToAttach.getIdproduto());
                attachedProdutoListNew.add(produtoListNewProdutoToAttach);
            }
            produtoListNew = attachedProdutoListNew;
            modelo.setProdutoList(produtoListNew);
            modelo = em.merge(modelo);
            if (marcaOld != null && !marcaOld.equals(marcaNew)) {
                marcaOld.getModeloList().remove(modelo);
                marcaOld = em.merge(marcaOld);
            }
            if (marcaNew != null && !marcaNew.equals(marcaOld)) {
                marcaNew.getModeloList().add(modelo);
                marcaNew = em.merge(marcaNew);
            }
            for (Produto produtoListOldProduto : produtoListOld) {
                if (!produtoListNew.contains(produtoListOldProduto)) {
                    produtoListOldProduto.setModelo(null);
                    produtoListOldProduto = em.merge(produtoListOldProduto);
                }
            }
            for (Produto produtoListNewProduto : produtoListNew) {
                if (!produtoListOld.contains(produtoListNewProduto)) {
                    Modelo oldModeloOfProdutoListNewProduto = produtoListNewProduto.getModelo();
                    produtoListNewProduto.setModelo(modelo);
                    produtoListNewProduto = em.merge(produtoListNewProduto);
                    if (oldModeloOfProdutoListNewProduto != null && !oldModeloOfProdutoListNewProduto.equals(modelo)) {
                        oldModeloOfProdutoListNewProduto.getProdutoList().remove(produtoListNewProduto);
                        oldModeloOfProdutoListNewProduto = em.merge(oldModeloOfProdutoListNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = modelo.getIdmodelo();
                if (findModelo(id) == null) {
                    throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelo modelo;
            try {
                modelo = em.getReference(Modelo.class, id);
                modelo.getIdmodelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.", enfe);
            }
            Marca marca = modelo.getMarca();
            if (marca != null) {
                marca.getModeloList().remove(modelo);
                marca = em.merge(marca);
            }
            List<Produto> produtoList = modelo.getProdutoList();
            for (Produto produtoListProduto : produtoList) {
                produtoListProduto.setModelo(null);
                produtoListProduto = em.merge(produtoListProduto);
            }
            em.remove(modelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modelo> findModeloEntities() {
        return findModeloEntities(true, -1, -1);
    }

    public List<Modelo> findModeloEntities(int maxResults, int firstResult) {
        return findModeloEntities(false, maxResults, firstResult);
    }

    private List<Modelo> findModeloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modelo.class));
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

    public Modelo findModelo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModeloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modelo> rt = cq.from(Modelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public int existeModelo(String s){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Modelo p where p.descricao = :i");
            q.setParameter("i", s);
            return ((Long)q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public int existeMarca(Marca i) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Produto p where p.idmarca = :i");
            q.setParameter("i", i);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
     
      public List<Modelo> getModeloAsc() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Modelo p order by p.marca.descricao asc, p.descricao asc");
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
