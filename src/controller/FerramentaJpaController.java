/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Saidafer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import modelo.Mecancoferramenta;
import modelo.Entradafer;
import modelo.Ferramenta;

/**
 *
 * @author Ussimane
 */
public class FerramentaJpaController implements Serializable {

    public FerramentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

     public void create(Ferramenta ferramenta) {
        if (ferramenta.getSaidaferList() == null) {
            ferramenta.setSaidaferList(new ArrayList<Saidafer>());
        }
        if (ferramenta.getMecancoferramentaList() == null) {
            ferramenta.setMecancoferramentaList(new ArrayList<Mecancoferramenta>());
        }
        if (ferramenta.getEntradaferList() == null) {
            ferramenta.setEntradaferList(new ArrayList<Entradafer>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Saidafer> attachedSaidaferList = new ArrayList<Saidafer>();
            for (Saidafer saidaferListSaidaferToAttach : ferramenta.getSaidaferList()) {
                saidaferListSaidaferToAttach = em.getReference(saidaferListSaidaferToAttach.getClass(), saidaferListSaidaferToAttach.getSaidaferPK());
                attachedSaidaferList.add(saidaferListSaidaferToAttach);
            }
            ferramenta.setSaidaferList(attachedSaidaferList);
            List<Mecancoferramenta> attachedMecancoferramentaList = new ArrayList<Mecancoferramenta>();
            for (Mecancoferramenta mecancoferramentaListMecancoferramentaToAttach : ferramenta.getMecancoferramentaList()) {
                mecancoferramentaListMecancoferramentaToAttach = em.getReference(mecancoferramentaListMecancoferramentaToAttach.getClass(), mecancoferramentaListMecancoferramentaToAttach.getMecancoferramentaPK());
                attachedMecancoferramentaList.add(mecancoferramentaListMecancoferramentaToAttach);
            }
            ferramenta.setMecancoferramentaList(attachedMecancoferramentaList);
            List<Entradafer> attachedEntradaferList = new ArrayList<Entradafer>();
            for (Entradafer entradaferListEntradaferToAttach : ferramenta.getEntradaferList()) {
                entradaferListEntradaferToAttach = em.getReference(entradaferListEntradaferToAttach.getClass(), entradaferListEntradaferToAttach.getEntradaferPK());
                attachedEntradaferList.add(entradaferListEntradaferToAttach);
            }
            ferramenta.setEntradaferList(attachedEntradaferList);
            em.persist(ferramenta);
            for (Saidafer saidaferListSaidafer : ferramenta.getSaidaferList()) {
                Ferramenta oldFerramentaOfSaidaferListSaidafer = saidaferListSaidafer.getFerramenta();
                saidaferListSaidafer.setFerramenta(ferramenta);
                saidaferListSaidafer = em.merge(saidaferListSaidafer);
                if (oldFerramentaOfSaidaferListSaidafer != null) {
                    oldFerramentaOfSaidaferListSaidafer.getSaidaferList().remove(saidaferListSaidafer);
                    oldFerramentaOfSaidaferListSaidafer = em.merge(oldFerramentaOfSaidaferListSaidafer);
                }
            }
            for (Mecancoferramenta mecancoferramentaListMecancoferramenta : ferramenta.getMecancoferramentaList()) {
                Ferramenta oldIdferramentaOfMecancoferramentaListMecancoferramenta = mecancoferramentaListMecancoferramenta.getIdferramenta();
                mecancoferramentaListMecancoferramenta.setIdferramenta(ferramenta);
                mecancoferramentaListMecancoferramenta = em.merge(mecancoferramentaListMecancoferramenta);
                if (oldIdferramentaOfMecancoferramentaListMecancoferramenta != null) {
                    oldIdferramentaOfMecancoferramentaListMecancoferramenta.getMecancoferramentaList().remove(mecancoferramentaListMecancoferramenta);
                    oldIdferramentaOfMecancoferramentaListMecancoferramenta = em.merge(oldIdferramentaOfMecancoferramentaListMecancoferramenta);
                }
            }
            for (Entradafer entradaferListEntradafer : ferramenta.getEntradaferList()) {
                Ferramenta oldFerramentaOfEntradaferListEntradafer = entradaferListEntradafer.getFerramenta();
                entradaferListEntradafer.setFerramenta(ferramenta);
                entradaferListEntradafer = em.merge(entradaferListEntradafer);
                if (oldFerramentaOfEntradaferListEntradafer != null) {
                    oldFerramentaOfEntradaferListEntradafer.getEntradaferList().remove(entradaferListEntradafer);
                    oldFerramentaOfEntradaferListEntradafer = em.merge(oldFerramentaOfEntradaferListEntradafer);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ferramenta ferramenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ferramenta persistentFerramenta = em.find(Ferramenta.class, ferramenta.getIdferramenta());
            List<Saidafer> saidaferListOld = persistentFerramenta.getSaidaferList();
            List<Saidafer> saidaferListNew = ferramenta.getSaidaferList();
            List<Mecancoferramenta> mecancoferramentaListOld = persistentFerramenta.getMecancoferramentaList();
            List<Mecancoferramenta> mecancoferramentaListNew = ferramenta.getMecancoferramentaList();
            List<Entradafer> entradaferListOld = persistentFerramenta.getEntradaferList();
            List<Entradafer> entradaferListNew = ferramenta.getEntradaferList();
            List<String> illegalOrphanMessages = null;
            for (Saidafer saidaferListOldSaidafer : saidaferListOld) {
                if (!saidaferListNew.contains(saidaferListOldSaidafer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Saidafer " + saidaferListOldSaidafer + " since its ferramenta field is not nullable.");
                }
            }
            for (Entradafer entradaferListOldEntradafer : entradaferListOld) {
                if (!entradaferListNew.contains(entradaferListOldEntradafer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entradafer " + entradaferListOldEntradafer + " since its ferramenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Saidafer> attachedSaidaferListNew = new ArrayList<Saidafer>();
            for (Saidafer saidaferListNewSaidaferToAttach : saidaferListNew) {
                saidaferListNewSaidaferToAttach = em.getReference(saidaferListNewSaidaferToAttach.getClass(), saidaferListNewSaidaferToAttach.getSaidaferPK());
                attachedSaidaferListNew.add(saidaferListNewSaidaferToAttach);
            }
            saidaferListNew = attachedSaidaferListNew;
            ferramenta.setSaidaferList(saidaferListNew);
            List<Mecancoferramenta> attachedMecancoferramentaListNew = new ArrayList<Mecancoferramenta>();
            for (Mecancoferramenta mecancoferramentaListNewMecancoferramentaToAttach : mecancoferramentaListNew) {
                mecancoferramentaListNewMecancoferramentaToAttach = em.getReference(mecancoferramentaListNewMecancoferramentaToAttach.getClass(), mecancoferramentaListNewMecancoferramentaToAttach.getMecancoferramentaPK());
                attachedMecancoferramentaListNew.add(mecancoferramentaListNewMecancoferramentaToAttach);
            }
            mecancoferramentaListNew = attachedMecancoferramentaListNew;
            ferramenta.setMecancoferramentaList(mecancoferramentaListNew);
            List<Entradafer> attachedEntradaferListNew = new ArrayList<Entradafer>();
            for (Entradafer entradaferListNewEntradaferToAttach : entradaferListNew) {
                entradaferListNewEntradaferToAttach = em.getReference(entradaferListNewEntradaferToAttach.getClass(), entradaferListNewEntradaferToAttach.getEntradaferPK());
                attachedEntradaferListNew.add(entradaferListNewEntradaferToAttach);
            }
            entradaferListNew = attachedEntradaferListNew;
            ferramenta.setEntradaferList(entradaferListNew);
            ferramenta = em.merge(ferramenta);
            for (Saidafer saidaferListNewSaidafer : saidaferListNew) {
                if (!saidaferListOld.contains(saidaferListNewSaidafer)) {
                    Ferramenta oldFerramentaOfSaidaferListNewSaidafer = saidaferListNewSaidafer.getFerramenta();
                    saidaferListNewSaidafer.setFerramenta(ferramenta);
                    saidaferListNewSaidafer = em.merge(saidaferListNewSaidafer);
                    if (oldFerramentaOfSaidaferListNewSaidafer != null && !oldFerramentaOfSaidaferListNewSaidafer.equals(ferramenta)) {
                        oldFerramentaOfSaidaferListNewSaidafer.getSaidaferList().remove(saidaferListNewSaidafer);
                        oldFerramentaOfSaidaferListNewSaidafer = em.merge(oldFerramentaOfSaidaferListNewSaidafer);
                    }
                }
            }
            for (Mecancoferramenta mecancoferramentaListOldMecancoferramenta : mecancoferramentaListOld) {
                if (!mecancoferramentaListNew.contains(mecancoferramentaListOldMecancoferramenta)) {
                    mecancoferramentaListOldMecancoferramenta.setIdferramenta(null);
                    mecancoferramentaListOldMecancoferramenta = em.merge(mecancoferramentaListOldMecancoferramenta);
                }
            }
            for (Mecancoferramenta mecancoferramentaListNewMecancoferramenta : mecancoferramentaListNew) {
                if (!mecancoferramentaListOld.contains(mecancoferramentaListNewMecancoferramenta)) {
                    Ferramenta oldIdferramentaOfMecancoferramentaListNewMecancoferramenta = mecancoferramentaListNewMecancoferramenta.getIdferramenta();
                    mecancoferramentaListNewMecancoferramenta.setIdferramenta(ferramenta);
                    mecancoferramentaListNewMecancoferramenta = em.merge(mecancoferramentaListNewMecancoferramenta);
                    if (oldIdferramentaOfMecancoferramentaListNewMecancoferramenta != null && !oldIdferramentaOfMecancoferramentaListNewMecancoferramenta.equals(ferramenta)) {
                        oldIdferramentaOfMecancoferramentaListNewMecancoferramenta.getMecancoferramentaList().remove(mecancoferramentaListNewMecancoferramenta);
                        oldIdferramentaOfMecancoferramentaListNewMecancoferramenta = em.merge(oldIdferramentaOfMecancoferramentaListNewMecancoferramenta);
                    }
                }
            }
            for (Entradafer entradaferListNewEntradafer : entradaferListNew) {
                if (!entradaferListOld.contains(entradaferListNewEntradafer)) {
                    Ferramenta oldFerramentaOfEntradaferListNewEntradafer = entradaferListNewEntradafer.getFerramenta();
                    entradaferListNewEntradafer.setFerramenta(ferramenta);
                    entradaferListNewEntradafer = em.merge(entradaferListNewEntradafer);
                    if (oldFerramentaOfEntradaferListNewEntradafer != null && !oldFerramentaOfEntradaferListNewEntradafer.equals(ferramenta)) {
                        oldFerramentaOfEntradaferListNewEntradafer.getEntradaferList().remove(entradaferListNewEntradafer);
                        oldFerramentaOfEntradaferListNewEntradafer = em.merge(oldFerramentaOfEntradaferListNewEntradafer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ferramenta.getIdferramenta();
                if (findFerramenta(id) == null) {
                    throw new NonexistentEntityException("The ferramenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Ferramenta persistentFerramenta, Ferramenta ferramenta, EntityManager em) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Ferramenta persistentFerramenta = em.find(Ferramenta.class, ferramenta.getIdferramenta());
            List<Saidafer> saidaferListOld = persistentFerramenta.getSaidaferList();
            List<Saidafer> saidaferListNew = ferramenta.getSaidaferList();
            List<Mecancoferramenta> mecancoferramentaListOld = persistentFerramenta.getMecancoferramentaList();
            List<Mecancoferramenta> mecancoferramentaListNew = ferramenta.getMecancoferramentaList();
            List<Entradafer> entradaferListOld = persistentFerramenta.getEntradaferList();
            List<Entradafer> entradaferListNew = ferramenta.getEntradaferList();
            List<String> illegalOrphanMessages = null;
            for (Saidafer saidaferListOldSaidafer : saidaferListOld) {
                if (!saidaferListNew.contains(saidaferListOldSaidafer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Saidafer " + saidaferListOldSaidafer + " since its ferramenta field is not nullable.");
                }
            }
            for (Entradafer entradaferListOldEntradafer : entradaferListOld) {
                if (!entradaferListNew.contains(entradaferListOldEntradafer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entradafer " + entradaferListOldEntradafer + " since its ferramenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Saidafer> attachedSaidaferListNew = new ArrayList<Saidafer>();
            for (Saidafer saidaferListNewSaidaferToAttach : saidaferListNew) {
                saidaferListNewSaidaferToAttach = em.getReference(saidaferListNewSaidaferToAttach.getClass(), saidaferListNewSaidaferToAttach.getSaidaferPK());
                attachedSaidaferListNew.add(saidaferListNewSaidaferToAttach);
            }
            saidaferListNew = attachedSaidaferListNew;
            ferramenta.setSaidaferList(saidaferListNew);
            List<Mecancoferramenta> attachedMecancoferramentaListNew = new ArrayList<Mecancoferramenta>();
            for (Mecancoferramenta mecancoferramentaListNewMecancoferramentaToAttach : mecancoferramentaListNew) {
                mecancoferramentaListNewMecancoferramentaToAttach = em.getReference(mecancoferramentaListNewMecancoferramentaToAttach.getClass(), mecancoferramentaListNewMecancoferramentaToAttach.getMecancoferramentaPK());
                attachedMecancoferramentaListNew.add(mecancoferramentaListNewMecancoferramentaToAttach);
            }
            mecancoferramentaListNew = attachedMecancoferramentaListNew;
            ferramenta.setMecancoferramentaList(mecancoferramentaListNew);
            List<Entradafer> attachedEntradaferListNew = new ArrayList<Entradafer>();
            for (Entradafer entradaferListNewEntradaferToAttach : entradaferListNew) {
                entradaferListNewEntradaferToAttach = em.getReference(entradaferListNewEntradaferToAttach.getClass(), entradaferListNewEntradaferToAttach.getEntradaferPK());
                attachedEntradaferListNew.add(entradaferListNewEntradaferToAttach);
            }
            entradaferListNew = attachedEntradaferListNew;
            ferramenta.setEntradaferList(entradaferListNew);
            ferramenta = em.merge(ferramenta);
            for (Saidafer saidaferListNewSaidafer : saidaferListNew) {
                if (!saidaferListOld.contains(saidaferListNewSaidafer)) {
                    Ferramenta oldFerramentaOfSaidaferListNewSaidafer = saidaferListNewSaidafer.getFerramenta();
                    saidaferListNewSaidafer.setFerramenta(ferramenta);
                    saidaferListNewSaidafer = em.merge(saidaferListNewSaidafer);
                    if (oldFerramentaOfSaidaferListNewSaidafer != null && !oldFerramentaOfSaidaferListNewSaidafer.equals(ferramenta)) {
                        oldFerramentaOfSaidaferListNewSaidafer.getSaidaferList().remove(saidaferListNewSaidafer);
                        oldFerramentaOfSaidaferListNewSaidafer = em.merge(oldFerramentaOfSaidaferListNewSaidafer);
                    }
                }
            }
            for (Mecancoferramenta mecancoferramentaListOldMecancoferramenta : mecancoferramentaListOld) {
                if (!mecancoferramentaListNew.contains(mecancoferramentaListOldMecancoferramenta)) {
                    mecancoferramentaListOldMecancoferramenta.setIdferramenta(null);
                    mecancoferramentaListOldMecancoferramenta = em.merge(mecancoferramentaListOldMecancoferramenta);
                }
            }
            for (Mecancoferramenta mecancoferramentaListNewMecancoferramenta : mecancoferramentaListNew) {
                if (!mecancoferramentaListOld.contains(mecancoferramentaListNewMecancoferramenta)) {
                    Ferramenta oldIdferramentaOfMecancoferramentaListNewMecancoferramenta = mecancoferramentaListNewMecancoferramenta.getIdferramenta();
                    mecancoferramentaListNewMecancoferramenta.setIdferramenta(ferramenta);
                    mecancoferramentaListNewMecancoferramenta = em.merge(mecancoferramentaListNewMecancoferramenta);
                    if (oldIdferramentaOfMecancoferramentaListNewMecancoferramenta != null && !oldIdferramentaOfMecancoferramentaListNewMecancoferramenta.equals(ferramenta)) {
                        oldIdferramentaOfMecancoferramentaListNewMecancoferramenta.getMecancoferramentaList().remove(mecancoferramentaListNewMecancoferramenta);
                        oldIdferramentaOfMecancoferramentaListNewMecancoferramenta = em.merge(oldIdferramentaOfMecancoferramentaListNewMecancoferramenta);
                    }
                }
            }
            for (Entradafer entradaferListNewEntradafer : entradaferListNew) {
                if (!entradaferListOld.contains(entradaferListNewEntradafer)) {
                    Ferramenta oldFerramentaOfEntradaferListNewEntradafer = entradaferListNewEntradafer.getFerramenta();
                    entradaferListNewEntradafer.setFerramenta(ferramenta);
                    entradaferListNewEntradafer = em.merge(entradaferListNewEntradafer);
                    if (oldFerramentaOfEntradaferListNewEntradafer != null && !oldFerramentaOfEntradaferListNewEntradafer.equals(ferramenta)) {
                        oldFerramentaOfEntradaferListNewEntradafer.getEntradaferList().remove(entradaferListNewEntradafer);
                        oldFerramentaOfEntradaferListNewEntradafer = em.merge(oldFerramentaOfEntradaferListNewEntradafer);
                    }
                }
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = ferramenta.getIdferramenta();
//                if (findFerramenta(id) == null) {
//                    throw new NonexistentEntityException("The ferramenta with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ferramenta ferramenta;
            try {
                ferramenta = em.getReference(Ferramenta.class, id);
                ferramenta.getIdferramenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ferramenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Saidafer> saidaferListOrphanCheck = ferramenta.getSaidaferList();
            for (Saidafer saidaferListOrphanCheckSaidafer : saidaferListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ferramenta (" + ferramenta + ") cannot be destroyed since the Saidafer " + saidaferListOrphanCheckSaidafer + " in its saidaferList field has a non-nullable ferramenta field.");
            }
            List<Entradafer> entradaferListOrphanCheck = ferramenta.getEntradaferList();
            for (Entradafer entradaferListOrphanCheckEntradafer : entradaferListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ferramenta (" + ferramenta + ") cannot be destroyed since the Entradafer " + entradaferListOrphanCheckEntradafer + " in its entradaferList field has a non-nullable ferramenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Mecancoferramenta> mecancoferramentaList = ferramenta.getMecancoferramentaList();
            for (Mecancoferramenta mecancoferramentaListMecancoferramenta : mecancoferramentaList) {
                mecancoferramentaListMecancoferramenta.setIdferramenta(null);
                mecancoferramentaListMecancoferramenta = em.merge(mecancoferramentaListMecancoferramenta);
            }
            em.remove(ferramenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ferramenta> findFerramentaEntities() {
        return findFerramentaEntities(true, -1, -1);
    }

    public List<Ferramenta> findFerramentaEntities(int maxResults, int firstResult) {
        return findFerramentaEntities(false, maxResults, firstResult);
    }

    private List<Ferramenta> findFerramentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ferramenta.class));
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

    public Ferramenta findFerramenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ferramenta.class, id);
        } finally {
            em.close();
        }
    }
    
     public Ferramenta findFerramentaS(Integer id,EntityManager em) {
//        EntityManager em = getEntityManager();
//        try {
            Ferramenta f =em.find(Ferramenta.class, id);
            em.refresh(f, LockModeType.PESSIMISTIC_WRITE);
            return f;
//        } finally {
//            em.close();
//        }
    }

    public int getFerramentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ferramenta> rt = cq.from(Ferramenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Ferramenta> getFerramentaAsc() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Ferramenta p order by p.descricao asc");
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
