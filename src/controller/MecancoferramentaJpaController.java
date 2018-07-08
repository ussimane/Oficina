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
import modelo.Ferramenta;
import modelo.Mecancoferramenta;
import modelo.MecancoferramentaPK;
import modelo.Mecanico;

/**
 *
 * @author Ussimane
 */
public class MecancoferramentaJpaController implements Serializable {

    public MecancoferramentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mecancoferramenta mecancoferramenta) throws PreexistingEntityException, Exception {
        if (mecancoferramenta.getMecancoferramentaPK() == null) {
            mecancoferramenta.setMecancoferramentaPK(new MecancoferramentaPK());
        }
        mecancoferramenta.getMecancoferramentaPK().setIdmecanico(mecancoferramenta.getMecanico().getIdmecanico());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ferramenta idferramenta = mecancoferramenta.getIdferramenta();
            if (idferramenta != null) {
                idferramenta = em.getReference(idferramenta.getClass(), idferramenta.getIdferramenta());
                mecancoferramenta.setIdferramenta(idferramenta);
            }
            Mecanico mecanico = mecancoferramenta.getMecanico();
            if (mecanico != null) {
                mecanico = em.getReference(mecanico.getClass(), mecanico.getIdmecanico());
                mecancoferramenta.setMecanico(mecanico);
            }
            em.persist(mecancoferramenta);
            if (idferramenta != null) {
                idferramenta.getMecancoferramentaList().add(mecancoferramenta);
                idferramenta = em.merge(idferramenta);
            }
            if (mecanico != null) {
                mecanico.getMecancoferramentaList().add(mecancoferramenta);
                mecanico = em.merge(mecanico);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMecancoferramenta(mecancoferramenta.getMecancoferramentaPK()) != null) {
                throw new PreexistingEntityException("Mecancoferramenta " + mecancoferramenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void create(Mecancoferramenta mecancoferramenta,EntityManager em) throws PreexistingEntityException, Exception {
        if (mecancoferramenta.getMecancoferramentaPK() == null) {
            mecancoferramenta.setMecancoferramentaPK(new MecancoferramentaPK());
        }
        mecancoferramenta.getMecancoferramentaPK().setIdmecanico(mecancoferramenta.getMecanico().getIdmecanico());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Ferramenta idferramenta = mecancoferramenta.getIdferramenta();
            if (idferramenta != null) {
                idferramenta = em.getReference(idferramenta.getClass(), idferramenta.getIdferramenta());
                mecancoferramenta.setIdferramenta(idferramenta);
            }
            Mecanico mecanico = mecancoferramenta.getMecanico();
            if (mecanico != null) {
                mecanico = em.getReference(mecanico.getClass(), mecanico.getIdmecanico());
                mecancoferramenta.setMecanico(mecanico);
            }
            em.persist(mecancoferramenta);
            if (idferramenta != null) {
                idferramenta.getMecancoferramentaList().add(mecancoferramenta);
                idferramenta = em.merge(idferramenta);
            }
            if (mecanico != null) {
                mecanico.getMecancoferramentaList().add(mecancoferramenta);
                mecanico = em.merge(mecanico);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findMecancoferramenta(mecancoferramenta.getMecancoferramentaPK()) != null) {
//                throw new PreexistingEntityException("Mecancoferramenta " + mecancoferramenta + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Mecancoferramenta mecancoferramenta) throws NonexistentEntityException, Exception {
        mecancoferramenta.getMecancoferramentaPK().setIdmecanico(mecancoferramenta.getMecanico().getIdmecanico());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecancoferramenta persistentMecancoferramenta = em.find(Mecancoferramenta.class, mecancoferramenta.getMecancoferramentaPK());
            Ferramenta idferramentaOld = persistentMecancoferramenta.getIdferramenta();
            Ferramenta idferramentaNew = mecancoferramenta.getIdferramenta();
            Mecanico mecanicoOld = persistentMecancoferramenta.getMecanico();
            Mecanico mecanicoNew = mecancoferramenta.getMecanico();
            if (idferramentaNew != null) {
                idferramentaNew = em.getReference(idferramentaNew.getClass(), idferramentaNew.getIdferramenta());
                mecancoferramenta.setIdferramenta(idferramentaNew);
            }
            if (mecanicoNew != null) {
                mecanicoNew = em.getReference(mecanicoNew.getClass(), mecanicoNew.getIdmecanico());
                mecancoferramenta.setMecanico(mecanicoNew);
            }
            mecancoferramenta = em.merge(mecancoferramenta);
            if (idferramentaOld != null && !idferramentaOld.equals(idferramentaNew)) {
                idferramentaOld.getMecancoferramentaList().remove(mecancoferramenta);
                idferramentaOld = em.merge(idferramentaOld);
            }
            if (idferramentaNew != null && !idferramentaNew.equals(idferramentaOld)) {
                idferramentaNew.getMecancoferramentaList().add(mecancoferramenta);
                idferramentaNew = em.merge(idferramentaNew);
            }
            if (mecanicoOld != null && !mecanicoOld.equals(mecanicoNew)) {
                mecanicoOld.getMecancoferramentaList().remove(mecancoferramenta);
                mecanicoOld = em.merge(mecanicoOld);
            }
            if (mecanicoNew != null && !mecanicoNew.equals(mecanicoOld)) {
                mecanicoNew.getMecancoferramentaList().add(mecancoferramenta);
                mecanicoNew = em.merge(mecanicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MecancoferramentaPK id = mecancoferramenta.getMecancoferramentaPK();
                if (findMecancoferramenta(id) == null) {
                    throw new NonexistentEntityException("The mecancoferramenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MecancoferramentaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecancoferramenta mecancoferramenta;
            try {
                mecancoferramenta = em.getReference(Mecancoferramenta.class, id);
                mecancoferramenta.getMecancoferramentaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mecancoferramenta with id " + id + " no longer exists.", enfe);
            }
            Ferramenta idferramenta = mecancoferramenta.getIdferramenta();
            if (idferramenta != null) {
                idferramenta.getMecancoferramentaList().remove(mecancoferramenta);
                idferramenta = em.merge(idferramenta);
            }
            Mecanico mecanico = mecancoferramenta.getMecanico();
            if (mecanico != null) {
                mecanico.getMecancoferramentaList().remove(mecancoferramenta);
                mecanico = em.merge(mecanico);
            }
            em.remove(mecancoferramenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
      public void destroy(MecancoferramentaPK id,EntityManager em) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Mecancoferramenta mecancoferramenta;
            try {
                mecancoferramenta = em.getReference(Mecancoferramenta.class, id);
                mecancoferramenta.getMecancoferramentaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mecancoferramenta with id " + id + " no longer exists.", enfe);
            }
            Ferramenta idferramenta = mecancoferramenta.getIdferramenta();
            if (idferramenta != null) {
                idferramenta.getMecancoferramentaList().remove(mecancoferramenta);
                idferramenta = em.merge(idferramenta);
            }
            Mecanico mecanico = mecancoferramenta.getMecanico();
            if (mecanico != null) {
                mecanico.getMecancoferramentaList().remove(mecancoferramenta);
                mecanico = em.merge(mecanico);
            }
            em.remove(mecancoferramenta);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Mecancoferramenta> findMecancoferramentaEntities() {
        return findMecancoferramentaEntities(true, -1, -1);
    }

    public List<Mecancoferramenta> findMecancoferramentaEntities(int maxResults, int firstResult) {
        return findMecancoferramentaEntities(false, maxResults, firstResult);
    }

    private List<Mecancoferramenta> findMecancoferramentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mecancoferramenta.class));
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

    public Mecancoferramenta findMecancoferramenta(MecancoferramentaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mecancoferramenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getMecancoferramentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mecancoferramenta> rt = cq.from(Mecancoferramenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Mecancoferramenta> getMecanicoFerramenta() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Mecancoferramenta p order by p.mecancoferramentaPK.data desc");
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
     public int existeMecanico(Mecanico m) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Mecancoferramenta p where p.mecanico = :i");
            q.setParameter("i", m);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
     
     public int existeFerramenta(Ferramenta m) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Mecancoferramenta p where p.idferramenta = :i");
            q.setParameter("i", m);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
