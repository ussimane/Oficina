/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Entradaviatura;
import modelo.Pecamanutencao;
import modelo.PecamanutencaoPK;
import modelo.Produto;

/**
 *
 * @author Ussimane
 */
public class PecamanutencaoJpaController implements Serializable {

    public PecamanutencaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pecamanutencao pecamanutencao) throws PreexistingEntityException, Exception {
        if (pecamanutencao.getPecamanutencaoPK() == null) {
            pecamanutencao.setPecamanutencaoPK(new PecamanutencaoPK());
        }
        pecamanutencao.getPecamanutencaoPK().setIdentrada(pecamanutencao.getEntradaviatura().getIdentrada());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradaviatura entradaviatura = pecamanutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura = em.getReference(entradaviatura.getClass(), entradaviatura.getIdentrada());
                pecamanutencao.setEntradaviatura(entradaviatura);
            }
            Produto idproduto = pecamanutencao.getIdproduto();
            if (idproduto != null) {
                idproduto = em.getReference(idproduto.getClass(), idproduto.getIdproduto());
                pecamanutencao.setIdproduto(idproduto);
            }
            em.persist(pecamanutencao);
            if (entradaviatura != null) {
                entradaviatura.getPecamanutencaoList().add(pecamanutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            if (idproduto != null) {
                idproduto.getPecamanutencaoList().add(pecamanutencao);
                idproduto = em.merge(idproduto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPecamanutencao(pecamanutencao.getPecamanutencaoPK()) != null) {
                throw new PreexistingEntityException("Pecamanutencao " + pecamanutencao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(Pecamanutencao pecamanutencao,EntityManager em) throws PreexistingEntityException, Exception {
        if (pecamanutencao.getPecamanutencaoPK() == null) {
            pecamanutencao.setPecamanutencaoPK(new PecamanutencaoPK());
        }
        pecamanutencao.getPecamanutencaoPK().setIdentrada(pecamanutencao.getEntradaviatura().getIdentrada());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Entradaviatura entradaviatura = pecamanutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura = em.getReference(entradaviatura.getClass(), entradaviatura.getIdentrada());
                pecamanutencao.setEntradaviatura(entradaviatura);
            }
            Produto idproduto = pecamanutencao.getIdproduto();
            if (idproduto != null) {
                idproduto = em.getReference(idproduto.getClass(), idproduto.getIdproduto());
                pecamanutencao.setIdproduto(idproduto);
            }
            em.persist(pecamanutencao);
            if (entradaviatura != null) {
                entradaviatura.getPecamanutencaoList().add(pecamanutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            if (idproduto != null) {
                idproduto.getPecamanutencaoList().add(pecamanutencao);
                idproduto = em.merge(idproduto);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findPecamanutencao(pecamanutencao.getPecamanutencaoPK()) != null) {
//                throw new PreexistingEntityException("Pecamanutencao " + pecamanutencao + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Pecamanutencao pecamanutencao) throws NonexistentEntityException, Exception {
        pecamanutencao.getPecamanutencaoPK().setIdentrada(pecamanutencao.getEntradaviatura().getIdentrada());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pecamanutencao persistentPecamanutencao = em.find(Pecamanutencao.class, pecamanutencao.getPecamanutencaoPK());
            Entradaviatura entradaviaturaOld = persistentPecamanutencao.getEntradaviatura();
            Entradaviatura entradaviaturaNew = pecamanutencao.getEntradaviatura();
            Produto idprodutoOld = persistentPecamanutencao.getIdproduto();
            Produto idprodutoNew = pecamanutencao.getIdproduto();
            if (entradaviaturaNew != null) {
                entradaviaturaNew = em.getReference(entradaviaturaNew.getClass(), entradaviaturaNew.getIdentrada());
                pecamanutencao.setEntradaviatura(entradaviaturaNew);
            }
            if (idprodutoNew != null) {
                idprodutoNew = em.getReference(idprodutoNew.getClass(), idprodutoNew.getIdproduto());
                pecamanutencao.setIdproduto(idprodutoNew);
            }
            pecamanutencao = em.merge(pecamanutencao);
            if (entradaviaturaOld != null && !entradaviaturaOld.equals(entradaviaturaNew)) {
                entradaviaturaOld.getPecamanutencaoList().remove(pecamanutencao);
                entradaviaturaOld = em.merge(entradaviaturaOld);
            }
            if (entradaviaturaNew != null && !entradaviaturaNew.equals(entradaviaturaOld)) {
                entradaviaturaNew.getPecamanutencaoList().add(pecamanutencao);
                entradaviaturaNew = em.merge(entradaviaturaNew);
            }
            if (idprodutoOld != null && !idprodutoOld.equals(idprodutoNew)) {
                idprodutoOld.getPecamanutencaoList().remove(pecamanutencao);
                idprodutoOld = em.merge(idprodutoOld);
            }
            if (idprodutoNew != null && !idprodutoNew.equals(idprodutoOld)) {
                idprodutoNew.getPecamanutencaoList().add(pecamanutencao);
                idprodutoNew = em.merge(idprodutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PecamanutencaoPK id = pecamanutencao.getPecamanutencaoPK();
                if (findPecamanutencao(id) == null) {
                    throw new NonexistentEntityException("The pecamanutencao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PecamanutencaoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pecamanutencao pecamanutencao;
            try {
                pecamanutencao = em.getReference(Pecamanutencao.class, id);
                pecamanutencao.getPecamanutencaoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pecamanutencao with id " + id + " no longer exists.", enfe);
            }
            Entradaviatura entradaviatura = pecamanutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura.getPecamanutencaoList().remove(pecamanutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            Produto idproduto = pecamanutencao.getIdproduto();
            if (idproduto != null) {
                idproduto.getPecamanutencaoList().remove(pecamanutencao);
                idproduto = em.merge(idproduto);
            }
            em.remove(pecamanutencao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void destroy(PecamanutencaoPK id,EntityManager em) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Pecamanutencao pecamanutencao;
            try {
                pecamanutencao = em.getReference(Pecamanutencao.class, id);
                pecamanutencao.getPecamanutencaoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pecamanutencao with id " + id + " no longer exists.", enfe);
            }
            Entradaviatura entradaviatura = pecamanutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura.getPecamanutencaoList().remove(pecamanutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            Produto idproduto = pecamanutencao.getIdproduto();
            if (idproduto != null) {
                idproduto.getPecamanutencaoList().remove(pecamanutencao);
                idproduto = em.merge(idproduto);
            }
            em.remove(pecamanutencao);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Pecamanutencao> findPecamanutencaoEntities() {
        return findPecamanutencaoEntities(true, -1, -1);
    }

    public List<Pecamanutencao> findPecamanutencaoEntities(int maxResults, int firstResult) {
        return findPecamanutencaoEntities(false, maxResults, firstResult);
    }

    private List<Pecamanutencao> findPecamanutencaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pecamanutencao.class));
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

    public Pecamanutencao findPecamanutencao(PecamanutencaoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pecamanutencao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPecamanutencaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pecamanutencao> rt = cq.from(Pecamanutencao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Produto getProdCod(String n) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Produto p where p.codigo = :i");
            q.setParameter("i", n);
            return (Produto) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Pecamanutencao> getPecamanutencao(Entradaviatura e) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Pecamanutencao p where p.pecamanutencaoPK.identrada = :e order by p.pecamanutencaoPK.data desc");
            q.setParameter("e", e.getIdentrada());
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
