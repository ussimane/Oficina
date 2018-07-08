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
import modelo.Saida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Tiposaida;

/**
 *
 * @author Ussimane
 */
public class TiposaidaJpaController implements Serializable {

    public TiposaidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tiposaida tiposaida) throws PreexistingEntityException, Exception {
        if (tiposaida.getSaidaList() == null) {
            tiposaida.setSaidaList(new ArrayList<Saida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Saida> attachedSaidaList = new ArrayList<Saida>();
            for (Saida saidaListSaidaToAttach : tiposaida.getSaidaList()) {
                saidaListSaidaToAttach = em.getReference(saidaListSaidaToAttach.getClass(), saidaListSaidaToAttach.getSaidaPK());
                attachedSaidaList.add(saidaListSaidaToAttach);
            }
            tiposaida.setSaidaList(attachedSaidaList);
            em.persist(tiposaida);
            for (Saida saidaListSaida : tiposaida.getSaidaList()) {
                Tiposaida oldTiposaidaOfSaidaListSaida = saidaListSaida.getTiposaida();
                saidaListSaida.setTiposaida(tiposaida);
                saidaListSaida = em.merge(saidaListSaida);
                if (oldTiposaidaOfSaidaListSaida != null) {
                    oldTiposaidaOfSaidaListSaida.getSaidaList().remove(saidaListSaida);
                    oldTiposaidaOfSaidaListSaida = em.merge(oldTiposaidaOfSaidaListSaida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposaida(tiposaida.getIdtiposaida()) != null) {
                throw new PreexistingEntityException("Tiposaida " + tiposaida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tiposaida tiposaida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiposaida persistentTiposaida = em.find(Tiposaida.class, tiposaida.getIdtiposaida());
            List<Saida> saidaListOld = persistentTiposaida.getSaidaList();
            List<Saida> saidaListNew = tiposaida.getSaidaList();
            List<Saida> attachedSaidaListNew = new ArrayList<Saida>();
            for (Saida saidaListNewSaidaToAttach : saidaListNew) {
                saidaListNewSaidaToAttach = em.getReference(saidaListNewSaidaToAttach.getClass(), saidaListNewSaidaToAttach.getSaidaPK());
                attachedSaidaListNew.add(saidaListNewSaidaToAttach);
            }
            saidaListNew = attachedSaidaListNew;
            tiposaida.setSaidaList(saidaListNew);
            tiposaida = em.merge(tiposaida);
            for (Saida saidaListOldSaida : saidaListOld) {
                if (!saidaListNew.contains(saidaListOldSaida)) {
                    saidaListOldSaida.setTiposaida(null);
                    saidaListOldSaida = em.merge(saidaListOldSaida);
                }
            }
            for (Saida saidaListNewSaida : saidaListNew) {
                if (!saidaListOld.contains(saidaListNewSaida)) {
                    Tiposaida oldTiposaidaOfSaidaListNewSaida = saidaListNewSaida.getTiposaida();
                    saidaListNewSaida.setTiposaida(tiposaida);
                    saidaListNewSaida = em.merge(saidaListNewSaida);
                    if (oldTiposaidaOfSaidaListNewSaida != null && !oldTiposaidaOfSaidaListNewSaida.equals(tiposaida)) {
                        oldTiposaidaOfSaidaListNewSaida.getSaidaList().remove(saidaListNewSaida);
                        oldTiposaidaOfSaidaListNewSaida = em.merge(oldTiposaidaOfSaidaListNewSaida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposaida.getIdtiposaida();
                if (findTiposaida(id) == null) {
                    throw new NonexistentEntityException("The tiposaida with id " + id + " no longer exists.");
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
            Tiposaida tiposaida;
            try {
                tiposaida = em.getReference(Tiposaida.class, id);
                tiposaida.getIdtiposaida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposaida with id " + id + " no longer exists.", enfe);
            }
            List<Saida> saidaList = tiposaida.getSaidaList();
            for (Saida saidaListSaida : saidaList) {
                saidaListSaida.setTiposaida(null);
                saidaListSaida = em.merge(saidaListSaida);
            }
            em.remove(tiposaida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tiposaida> findTiposaidaEntities() {
        return findTiposaidaEntities(true, -1, -1);
    }

    public List<Tiposaida> findTiposaidaEntities(int maxResults, int firstResult) {
        return findTiposaidaEntities(false, maxResults, firstResult);
    }

    private List<Tiposaida> findTiposaidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tiposaida.class));
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

    public Tiposaida findTiposaida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tiposaida.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposaidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tiposaida> rt = cq.from(Tiposaida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
