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
import modelo.Saidafer;
import modelo.SaidaferPK;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class SaidaferJpaController implements Serializable {

    public SaidaferJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Saidafer saidafer) throws PreexistingEntityException, Exception {
        if (saidafer.getSaidaferPK() == null) {
            saidafer.setSaidaferPK(new SaidaferPK());
        }
        saidafer.getSaidaferPK().setIdferramenta(saidafer.getFerramenta().getIdferramenta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ferramenta ferramenta = saidafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta = em.getReference(ferramenta.getClass(), ferramenta.getIdferramenta());
                saidafer.setFerramenta(ferramenta);
            }
            Vendedor utilizador = saidafer.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                saidafer.setUtilizador(utilizador);
            }
            em.persist(saidafer);
            if (ferramenta != null) {
                ferramenta.getSaidaferList().add(saidafer);
                ferramenta = em.merge(ferramenta);
            }
            if (utilizador != null) {
                utilizador.getSaidaferList().add(saidafer);
                utilizador = em.merge(utilizador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaidafer(saidafer.getSaidaferPK()) != null) {
                throw new PreexistingEntityException("Saidafer " + saidafer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void create(Saidafer saidafer,EntityManager em) throws PreexistingEntityException, Exception {
        if (saidafer.getSaidaferPK() == null) {
            saidafer.setSaidaferPK(new SaidaferPK());
        }
        saidafer.getSaidaferPK().setIdferramenta(saidafer.getFerramenta().getIdferramenta());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Ferramenta ferramenta = saidafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta = em.getReference(ferramenta.getClass(), ferramenta.getIdferramenta());
                saidafer.setFerramenta(ferramenta);
            }
            Vendedor utilizador = saidafer.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                saidafer.setUtilizador(utilizador);
            }
            em.persist(saidafer);
            if (ferramenta != null) {
                ferramenta.getSaidaferList().add(saidafer);
                ferramenta = em.merge(ferramenta);
            }
            if (utilizador != null) {
                utilizador.getSaidaferList().add(saidafer);
                utilizador = em.merge(utilizador);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findSaidafer(saidafer.getSaidaferPK()) != null) {
//                throw new PreexistingEntityException("Saidafer " + saidafer + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Saidafer saidafer) throws NonexistentEntityException, Exception {
        saidafer.getSaidaferPK().setIdferramenta(saidafer.getFerramenta().getIdferramenta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saidafer persistentSaidafer = em.find(Saidafer.class, saidafer.getSaidaferPK());
            Ferramenta ferramentaOld = persistentSaidafer.getFerramenta();
            Ferramenta ferramentaNew = saidafer.getFerramenta();
            Vendedor utilizadorOld = persistentSaidafer.getUtilizador();
            Vendedor utilizadorNew = saidafer.getUtilizador();
            if (ferramentaNew != null) {
                ferramentaNew = em.getReference(ferramentaNew.getClass(), ferramentaNew.getIdferramenta());
                saidafer.setFerramenta(ferramentaNew);
            }
             if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getIdvendedor());
                saidafer.setUtilizador(utilizadorNew);
            }
            saidafer = em.merge(saidafer);
            if (ferramentaOld != null && !ferramentaOld.equals(ferramentaNew)) {
                ferramentaOld.getSaidaferList().remove(saidafer);
                ferramentaOld = em.merge(ferramentaOld);
            }
            if (ferramentaNew != null && !ferramentaNew.equals(ferramentaOld)) {
                ferramentaNew.getSaidaferList().add(saidafer);
                ferramentaNew = em.merge(ferramentaNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getSaidaferList().remove(saidafer);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getSaidaferList().add(saidafer);
                utilizadorNew = em.merge(utilizadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SaidaferPK id = saidafer.getSaidaferPK();
                if (findSaidafer(id) == null) {
                    throw new NonexistentEntityException("The saidafer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SaidaferPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saidafer saidafer;
            try {
                saidafer = em.getReference(Saidafer.class, id);
                saidafer.getSaidaferPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saidafer with id " + id + " no longer exists.", enfe);
            }
            Ferramenta ferramenta = saidafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta.getSaidaferList().remove(saidafer);
                ferramenta = em.merge(ferramenta);
            }
            Vendedor utilizador = saidafer.getUtilizador();
            if (utilizador != null) {
                utilizador.getSaidaferList().remove(saidafer);
                utilizador = em.merge(utilizador);
            }
            em.remove(saidafer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Saidafer> findSaidaferEntities() {
        return findSaidaferEntities(true, -1, -1);
    }

    public List<Saidafer> findSaidaferEntities(int maxResults, int firstResult) {
        return findSaidaferEntities(false, maxResults, firstResult);
    }

    private List<Saidafer> findSaidaferEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Saidafer.class));
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

    public Saidafer findSaidafer(SaidaferPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Saidafer.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaidaferCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Saidafer> rt = cq.from(Saidafer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
