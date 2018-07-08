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
import modelo.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Imposto;

/**
 *
 * @author USSIMANE
 */
public class ImpostoJpaController implements Serializable {

    public ImpostoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Imposto imposto) {
        if (imposto.getProdutoList() == null) {
            imposto.setProdutoList(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Produto> attachedProdutoList = new ArrayList<Produto>();
            for (Produto produtoListProdutoToAttach : imposto.getProdutoList()) {
                produtoListProdutoToAttach = em.getReference(produtoListProdutoToAttach.getClass(), produtoListProdutoToAttach.getIdproduto());
                attachedProdutoList.add(produtoListProdutoToAttach);
            }
            imposto.setProdutoList(attachedProdutoList);
            em.persist(imposto);
            for (Produto produtoListProduto : imposto.getProdutoList()) {
                Imposto oldIdimpostoOfProdutoListProduto = produtoListProduto.getIdimposto();
                produtoListProduto.setIdimposto(imposto);
                produtoListProduto = em.merge(produtoListProduto);
                if (oldIdimpostoOfProdutoListProduto != null) {
                    oldIdimpostoOfProdutoListProduto.getProdutoList().remove(produtoListProduto);
                    oldIdimpostoOfProdutoListProduto = em.merge(oldIdimpostoOfProdutoListProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Imposto imposto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imposto persistentImposto = em.find(Imposto.class, imposto.getIdimposto());
            List<Produto> produtoListOld = persistentImposto.getProdutoList();
            List<Produto> produtoListNew = imposto.getProdutoList();
            List<Produto> attachedProdutoListNew = new ArrayList<Produto>();
            for (Produto produtoListNewProdutoToAttach : produtoListNew) {
                produtoListNewProdutoToAttach = em.getReference(produtoListNewProdutoToAttach.getClass(), produtoListNewProdutoToAttach.getIdproduto());
                attachedProdutoListNew.add(produtoListNewProdutoToAttach);
            }
            produtoListNew = attachedProdutoListNew;
            imposto.setProdutoList(produtoListNew);
            imposto = em.merge(imposto);
            for (Produto produtoListOldProduto : produtoListOld) {
                if (!produtoListNew.contains(produtoListOldProduto)) {
                    produtoListOldProduto.setIdimposto(null);
                    produtoListOldProduto = em.merge(produtoListOldProduto);
                }
            }
            for (Produto produtoListNewProduto : produtoListNew) {
                if (!produtoListOld.contains(produtoListNewProduto)) {
                    Imposto oldIdimpostoOfProdutoListNewProduto = produtoListNewProduto.getIdimposto();
                    produtoListNewProduto.setIdimposto(imposto);
                    produtoListNewProduto = em.merge(produtoListNewProduto);
                    if (oldIdimpostoOfProdutoListNewProduto != null && !oldIdimpostoOfProdutoListNewProduto.equals(imposto)) {
                        oldIdimpostoOfProdutoListNewProduto.getProdutoList().remove(produtoListNewProduto);
                        oldIdimpostoOfProdutoListNewProduto = em.merge(oldIdimpostoOfProdutoListNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imposto.getIdimposto();
                if (findImposto(id) == null) {
                    throw new NonexistentEntityException("The imposto with id " + id + " no longer exists.");
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
            Imposto imposto;
            try {
                imposto = em.getReference(Imposto.class, id);
                imposto.getIdimposto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imposto with id " + id + " no longer exists.", enfe);
            }
            List<Produto> produtoList = imposto.getProdutoList();
            for (Produto produtoListProduto : produtoList) {
                produtoListProduto.setIdimposto(null);
                produtoListProduto = em.merge(produtoListProduto);
            }
            em.remove(imposto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Imposto> findImpostoEntities() {
        return findImpostoEntities(true, -1, -1);
    }

    public List<Imposto> findImpostoEntities(int maxResults, int firstResult) {
        return findImpostoEntities(false, maxResults, firstResult);
    }

    private List<Imposto> findImpostoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Imposto.class));
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

    public Imposto findImposto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Imposto.class, id);
        } finally {
            em.close();
        }
    }

    public int getImpostoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Imposto> rt = cq.from(Imposto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
