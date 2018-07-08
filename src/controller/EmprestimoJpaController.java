/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Empresa;
import modelo.Emprestimo;

/**
 *
 * @author USSIMANE
 */
public class EmprestimoJpaController implements Serializable {

    public EmprestimoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Emprestimo emprestimo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(emprestimo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(Emprestimo emprestimo,EntityManager em) {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            em.persist(emprestimo);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Emprestimo emprestimo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            emprestimo = em.merge(emprestimo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = emprestimo.getIdemp();
                if (findEmprestimo(id) == null) {
                    throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.");
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
            Emprestimo emprestimo;
            try {
                emprestimo = em.getReference(Emprestimo.class, id);
                emprestimo.getIdemp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.", enfe);
            }
            em.remove(emprestimo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
      public void destroy(Long id,EntityManager em) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Emprestimo emprestimo;
            try {
                emprestimo = em.getReference(Emprestimo.class, id);
                emprestimo.getIdemp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.", enfe);
            }
            em.remove(emprestimo);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Emprestimo> findEmprestimoEntities() {
        return findEmprestimoEntities(true, -1, -1);
    }

    public List<Emprestimo> findEmprestimoEntities(int maxResults, int firstResult) {
        return findEmprestimoEntities(false, maxResults, firstResult);
    }

    private List<Emprestimo> findEmprestimoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Emprestimo.class));
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

    public Emprestimo findEmprestimo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Emprestimo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmprestimoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Emprestimo> rt = cq.from(Emprestimo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Emprestimo> getEmprestPeriodo(Date i, Date f, Object e) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            if (e.equals("Todas Empresas")) {
                if (f == null) {
                    q = em.createQuery("from Emprestimo v where v.dataemprestimo >= :i");
                    i.setHours(1);
                    q.setParameter("i", i);
                } else {
                    q = em.createQuery("from Emprestimo v where v.dataemprestimo >= :i and v.dataemprestimo <= :f");
                     i.setHours(1);
                     f.setHours(23);
                    q.setParameter("i", i);
                    q.setParameter("f", f);
                }
            } else {
                if (f == null) {
                    q = em.createQuery("from Emprestimo v where v.dataemprestimo >= :i and v.idcliente.idempresa = :e");
                    i.setHours(1);
                    q.setParameter("i", i);
                    q.setParameter("e", (Empresa)e);
                } else {
                    q = em.createQuery("from Emprestimo v where v.dataemprestimo >= :i and v.dataemprestimo <= :f and v.idcliente.idempresa = :e");
                    i.setHours(1);
                     f.setHours(23);
                    q.setParameter("i", i);
                    q.setParameter("f", f);
                    q.setParameter("e", e);
                }
            }
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
            }finally {
            em.close();
        }
        }
    
    

    public List<Emprestimo> getEmprestimo(Date i) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            q = em.createQuery("from Emprestimo v where v.dataemprestimo = :i order by v.datae asc");
            q.setParameter("i", i);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
