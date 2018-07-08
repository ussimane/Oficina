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
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Entrada;
import modelo.Entradafer;
import modelo.EntradaferPK;
import modelo.Ferramenta;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class EntradaferJpaController implements Serializable {

    public EntradaferJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entradafer entradafer) throws PreexistingEntityException, Exception {
        if (entradafer.getEntradaferPK() == null) {
            entradafer.setEntradaferPK(new EntradaferPK());
        }
        entradafer.getEntradaferPK().setIdferramenta(entradafer.getFerramenta().getIdferramenta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ferramenta ferramenta = entradafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta = em.getReference(ferramenta.getClass(), ferramenta.getIdferramenta());
                entradafer.setFerramenta(ferramenta);
            }
            Vendedor utilizador = entradafer.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                entradafer.setUtilizador(utilizador);
            }
            em.persist(entradafer);
            if (ferramenta != null) {
                ferramenta.getEntradaferList().add(entradafer);
                ferramenta = em.merge(ferramenta);
            }
            if (utilizador != null) {
                utilizador.getEntradaferList().add(entradafer);
                utilizador = em.merge(utilizador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntradafer(entradafer.getEntradaferPK()) != null) {
                throw new PreexistingEntityException("Entradafer " + entradafer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(Entradafer entradafer, EntityManager em) throws PreexistingEntityException, Exception {
        if (entradafer.getEntradaferPK() == null) {
            entradafer.setEntradaferPK(new EntradaferPK());
        }
        entradafer.getEntradaferPK().setIdferramenta(entradafer.getFerramenta().getIdferramenta());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Ferramenta ferramenta = entradafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta = em.getReference(ferramenta.getClass(), ferramenta.getIdferramenta());
                entradafer.setFerramenta(ferramenta);
            }
            Vendedor utilizador = entradafer.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                entradafer.setUtilizador(utilizador);
            }
            em.persist(entradafer);
            if (ferramenta != null) {
                ferramenta.getEntradaferList().add(entradafer);
                ferramenta = em.merge(ferramenta);
            }
            if (utilizador != null) {
                utilizador.getEntradaferList().add(entradafer);
                utilizador = em.merge(utilizador);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findEntradafer(entradafer.getEntradaferPK()) != null) {
//                throw new PreexistingEntityException("Entradafer " + entradafer + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Entradafer entradafer) throws NonexistentEntityException, Exception {
        entradafer.getEntradaferPK().setIdferramenta(entradafer.getFerramenta().getIdferramenta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradafer persistentEntradafer = em.find(Entradafer.class, entradafer.getEntradaferPK());
            Ferramenta ferramentaOld = persistentEntradafer.getFerramenta();
            Ferramenta ferramentaNew = entradafer.getFerramenta();
            Vendedor utilizadorOld = persistentEntradafer.getUtilizador();
            Vendedor utilizadorNew = entradafer.getUtilizador();
            if (ferramentaNew != null) {
                ferramentaNew = em.getReference(ferramentaNew.getClass(), ferramentaNew.getIdferramenta());
                entradafer.setFerramenta(ferramentaNew);
            }
             if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getIdvendedor());
                entradafer.setUtilizador(utilizadorNew);
            }
            entradafer = em.merge(entradafer);
            if (ferramentaOld != null && !ferramentaOld.equals(ferramentaNew)) {
                ferramentaOld.getEntradaferList().remove(entradafer);
                ferramentaOld = em.merge(ferramentaOld);
            }
            if (ferramentaNew != null && !ferramentaNew.equals(ferramentaOld)) {
                ferramentaNew.getEntradaferList().add(entradafer);
                ferramentaNew = em.merge(ferramentaNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getEntradaferList().remove(entradafer);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getEntradaferList().add(entradafer);
                utilizadorNew = em.merge(utilizadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EntradaferPK id = entradafer.getEntradaferPK();
                if (findEntradafer(id) == null) {
                    throw new NonexistentEntityException("The entradafer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void edit(Entradafer persistentEntradafer,Entradafer entradafer,EntityManager em) throws NonexistentEntityException, Exception {
        entradafer.getEntradaferPK().setIdferramenta(entradafer.getFerramenta().getIdferramenta());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Entradafer persistentEntradafer = em.find(Entradafer.class, entradafer.getEntradaferPK());
            Ferramenta ferramentaOld = persistentEntradafer.getFerramenta();
            Ferramenta ferramentaNew = entradafer.getFerramenta();
            Vendedor utilizadorOld = persistentEntradafer.getUtilizador();
            Vendedor utilizadorNew = entradafer.getUtilizador();
            if (ferramentaNew != null) {
                ferramentaNew = em.getReference(ferramentaNew.getClass(), ferramentaNew.getIdferramenta());
                entradafer.setFerramenta(ferramentaNew);
            }
            if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getIdvendedor());
                entradafer.setUtilizador(utilizadorNew);
            }
            entradafer = em.merge(entradafer);
            if (ferramentaOld != null && !ferramentaOld.equals(ferramentaNew)) {
                ferramentaOld.getEntradaferList().remove(entradafer);
                ferramentaOld = em.merge(ferramentaOld);
            }
            if (ferramentaNew != null && !ferramentaNew.equals(ferramentaOld)) {
                ferramentaNew.getEntradaferList().add(entradafer);
                ferramentaNew = em.merge(ferramentaNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getEntradaferList().remove(entradafer);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getEntradaferList().add(entradafer);
                utilizadorNew = em.merge(utilizadorNew);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                EntradaferPK id = entradafer.getEntradaferPK();
//                if (findEntradafer(id) == null) {
//                    throw new NonexistentEntityException("The entradafer with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(EntradaferPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradafer entradafer;
            try {
                entradafer = em.getReference(Entradafer.class, id);
                entradafer.getEntradaferPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entradafer with id " + id + " no longer exists.", enfe);
            }
            Ferramenta ferramenta = entradafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta.getEntradaferList().remove(entradafer);
                ferramenta = em.merge(ferramenta);
            }
            Vendedor utilizador = entradafer.getUtilizador();
            if (utilizador != null) {
                utilizador.getEntradaferList().remove(entradafer);
                utilizador = em.merge(utilizador);
            }
            em.remove(entradafer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void destroy(EntradaferPK id,EntityManager em) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Entradafer entradafer;
            try {
                entradafer = em.getReference(Entradafer.class, id);
                entradafer.getEntradaferPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entradafer with id " + id + " no longer exists.", enfe);
            }
            Ferramenta ferramenta = entradafer.getFerramenta();
            if (ferramenta != null) {
                ferramenta.getEntradaferList().remove(entradafer);
                ferramenta = em.merge(ferramenta);
            }
             Vendedor utilizador = entradafer.getUtilizador();
            if (utilizador != null) {
                utilizador.getEntradaferList().remove(entradafer);
                utilizador = em.merge(utilizador);
            }
            em.remove(entradafer);
            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Entradafer> findEntradaferEntities() {
        return findEntradaferEntities(true, -1, -1);
    }

    public List<Entradafer> findEntradaferEntities(int maxResults, int firstResult) {
        return findEntradaferEntities(false, maxResults, firstResult);
    }

    private List<Entradafer> findEntradaferEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entradafer.class));
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

    public Entradafer findEntradafer(EntradaferPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entradafer.class, id);
        } finally {
            em.close();
        }
    }
    
    public Entradafer findEntradaferS(EntradaferPK id,EntityManager em) {
//        EntityManager em = getEntityManager();
//        try {
            Entradafer e= em.find(Entradafer.class, id);
            em.refresh(e,LockModeType.PESSIMISTIC_WRITE);
            return e;
//        } finally {
//            em.close();
//        }
    }

    public int getEntradaferCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entradafer> rt = cq.from(Entradafer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
