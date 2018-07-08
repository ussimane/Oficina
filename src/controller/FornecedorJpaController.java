/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Entrada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Fornecedor;

/**
 *
 * @author USSIMANE
 */
public class FornecedorJpaController implements Serializable {

    public FornecedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedor fornecedor) throws PreexistingEntityException, Exception {
        if (fornecedor.getEntradaList() == null) {
            fornecedor.setEntradaList(new ArrayList<Entrada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Entrada> attachedEntradaList = new ArrayList<Entrada>();
            for (Entrada entradaListEntradaToAttach : fornecedor.getEntradaList()) {
                entradaListEntradaToAttach = em.getReference(entradaListEntradaToAttach.getClass(), entradaListEntradaToAttach.getEntradaPK());
                attachedEntradaList.add(entradaListEntradaToAttach);
            }
            fornecedor.setEntradaList(attachedEntradaList);
            em.persist(fornecedor);
            for (Entrada entradaListEntrada : fornecedor.getEntradaList()) {
                Fornecedor oldIdfornecedorOfEntradaListEntrada = entradaListEntrada.getIdfornecedor();
                entradaListEntrada.setIdfornecedor(fornecedor);
                entradaListEntrada = em.merge(entradaListEntrada);
                if (oldIdfornecedorOfEntradaListEntrada != null) {
                    oldIdfornecedorOfEntradaListEntrada.getEntradaList().remove(entradaListEntrada);
                    oldIdfornecedorOfEntradaListEntrada = em.merge(oldIdfornecedorOfEntradaListEntrada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFornecedor(fornecedor.getIdfornecedor()) != null) {
                throw new PreexistingEntityException("Fornecedor " + fornecedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedor fornecedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor persistentFornecedor = em.find(Fornecedor.class, fornecedor.getIdfornecedor());
            List<Entrada> entradaListOld = persistentFornecedor.getEntradaList();
            List<Entrada> entradaListNew = fornecedor.getEntradaList();
            List<Entrada> attachedEntradaListNew = new ArrayList<Entrada>();
            for (Entrada entradaListNewEntradaToAttach : entradaListNew) {
                entradaListNewEntradaToAttach = em.getReference(entradaListNewEntradaToAttach.getClass(), entradaListNewEntradaToAttach.getEntradaPK());
                attachedEntradaListNew.add(entradaListNewEntradaToAttach);
            }
            entradaListNew = attachedEntradaListNew;
            fornecedor.setEntradaList(entradaListNew);
            fornecedor = em.merge(fornecedor);
            for (Entrada entradaListOldEntrada : entradaListOld) {
                if (!entradaListNew.contains(entradaListOldEntrada)) {
                    entradaListOldEntrada.setIdfornecedor(null);
                    entradaListOldEntrada = em.merge(entradaListOldEntrada);
                }
            }
            for (Entrada entradaListNewEntrada : entradaListNew) {
                if (!entradaListOld.contains(entradaListNewEntrada)) {
                    Fornecedor oldIdfornecedorOfEntradaListNewEntrada = entradaListNewEntrada.getIdfornecedor();
                    entradaListNewEntrada.setIdfornecedor(fornecedor);
                    entradaListNewEntrada = em.merge(entradaListNewEntrada);
                    if (oldIdfornecedorOfEntradaListNewEntrada != null && !oldIdfornecedorOfEntradaListNewEntrada.equals(fornecedor)) {
                        oldIdfornecedorOfEntradaListNewEntrada.getEntradaList().remove(entradaListNewEntrada);
                        oldIdfornecedorOfEntradaListNewEntrada = em.merge(oldIdfornecedorOfEntradaListNewEntrada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedor.getIdfornecedor();
                if (findFornecedor(id) == null) {
                    throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.");
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
            Fornecedor fornecedor;
            try {
                fornecedor = em.getReference(Fornecedor.class, id);
                fornecedor.getIdfornecedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.", enfe);
            }
            List<Entrada> entradaList = fornecedor.getEntradaList();
            for (Entrada entradaListEntrada : entradaList) {
                entradaListEntrada.setIdfornecedor(null);
                entradaListEntrada = em.merge(entradaListEntrada);
            }
            em.remove(fornecedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedor> findFornecedorEntities() {
        return findFornecedorEntities(true, -1, -1);
    }

    public List<Fornecedor> findFornecedorEntities(int maxResults, int firstResult) {
        return findFornecedorEntities(false, maxResults, firstResult);
    }

    private List<Fornecedor> findFornecedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedor.class));
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

    public Fornecedor findFornecedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedor> rt = cq.from(Fornecedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Fornecedor> getFornecedorLike(String n){
       String nn = "%" + n.toLowerCase() + "%";
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Fornecedor p where lower(p.nome) like :n or p.cliente = :i");
            q.setParameter("n", nn);
            q.setParameter("i", n);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public int existeCodigo(String c){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Fornecedor p where p.codigo = :i");
            q.setParameter("i", c);
            return ((Long)q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
