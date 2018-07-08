/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Seriefactura;

/**
 *
 * @author USSIMANE
 */
public class SeriefacturaJpaController implements Serializable {

    public SeriefacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Seriefactura seriefactura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(seriefactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Seriefactura seriefactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            seriefactura = em.merge(seriefactura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seriefactura.getIdf();
                if (findSeriefactura(id) == null) {
                    throw new NonexistentEntityException("The seriefactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void edit(Seriefactura seriefactura,EntityManager em) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            seriefactura = em.merge(seriefactura);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = seriefactura.getIdf();
//                if (findSeriefactura(id) == null) {
//                    throw new NonexistentEntityException("The seriefactura with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }
     
      public String addSerie(EntityManager em) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
          Seriefactura s = em.find(Seriefactura.class, 1);
          em.refresh(s,LockModeType.PESSIMISTIC_WRITE);
          String ss = s.getSeriefactura();
          ss = (Integer.parseInt(ss) + 1) + "";
          s.setSeriefactura(ss);
          em.merge(s);
          return ss;
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = seriefactura.getIdf();
//                if (findSeriefactura(id) == null) {
//                    throw new NonexistentEntityException("The seriefactura with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seriefactura seriefactura;
            try {
                seriefactura = em.getReference(Seriefactura.class, id);
                seriefactura.getIdf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seriefactura with id " + id + " no longer exists.", enfe);
            }
            em.remove(seriefactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Seriefactura> findSeriefacturaEntities() {
        return findSeriefacturaEntities(true, -1, -1);
    }

    public List<Seriefactura> findSeriefacturaEntities(int maxResults, int firstResult) {
        return findSeriefacturaEntities(false, maxResults, firstResult);
    }

    private List<Seriefactura> findSeriefacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Seriefactura.class));
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

    public Seriefactura findSeriefactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Seriefactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeriefacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Seriefactura> rt = cq.from(Seriefactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
