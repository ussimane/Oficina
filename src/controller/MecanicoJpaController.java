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
import modelo.Mecanicomanutencao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Entradaviatura;
import modelo.Mecancoferramenta;
import modelo.Mecanico;
import modelo.Pecamanutencao;

/**
 *
 * @author Ussimane
 */
public class MecanicoJpaController implements Serializable {

    public MecanicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mecanico mecanico) {
        if (mecanico.getMecancoferramentaList() == null) {
            mecanico.setMecancoferramentaList(new ArrayList<Mecancoferramenta>());
        }
        if (mecanico.getMecanicomanutencaoList() == null) {
            mecanico.setMecanicomanutencaoList(new ArrayList<Mecanicomanutencao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mecancoferramenta> attachedMecancoferramentaList = new ArrayList<Mecancoferramenta>();
            for (Mecancoferramenta mecancoferramentaListMecancoferramentaToAttach : mecanico.getMecancoferramentaList()) {
                mecancoferramentaListMecancoferramentaToAttach = em.getReference(mecancoferramentaListMecancoferramentaToAttach.getClass(), mecancoferramentaListMecancoferramentaToAttach.getMecancoferramentaPK());
                attachedMecancoferramentaList.add(mecancoferramentaListMecancoferramentaToAttach);
            }
            mecanico.setMecancoferramentaList(attachedMecancoferramentaList);
            List<Mecanicomanutencao> attachedMecanicomanutencaoList = new ArrayList<Mecanicomanutencao>();
            for (Mecanicomanutencao mecanicomanutencaoListMecanicomanutencaoToAttach : mecanico.getMecanicomanutencaoList()) {
                mecanicomanutencaoListMecanicomanutencaoToAttach = em.getReference(mecanicomanutencaoListMecanicomanutencaoToAttach.getClass(), mecanicomanutencaoListMecanicomanutencaoToAttach.getMecanicomanutencaoPK());
                attachedMecanicomanutencaoList.add(mecanicomanutencaoListMecanicomanutencaoToAttach);
            }
            mecanico.setMecanicomanutencaoList(attachedMecanicomanutencaoList);
            em.persist(mecanico);
            for (Mecancoferramenta mecancoferramentaListMecancoferramenta : mecanico.getMecancoferramentaList()) {
                Mecanico oldMecanicoOfMecancoferramentaListMecancoferramenta = mecancoferramentaListMecancoferramenta.getMecanico();
                mecancoferramentaListMecancoferramenta.setMecanico(mecanico);
                mecancoferramentaListMecancoferramenta = em.merge(mecancoferramentaListMecancoferramenta);
                if (oldMecanicoOfMecancoferramentaListMecancoferramenta != null) {
                    oldMecanicoOfMecancoferramentaListMecancoferramenta.getMecancoferramentaList().remove(mecancoferramentaListMecancoferramenta);
                    oldMecanicoOfMecancoferramentaListMecancoferramenta = em.merge(oldMecanicoOfMecancoferramentaListMecancoferramenta);
                }
            }
            for (Mecanicomanutencao mecanicomanutencaoListMecanicomanutencao : mecanico.getMecanicomanutencaoList()) {
                Mecanico oldMecanicoOfMecanicomanutencaoListMecanicomanutencao = mecanicomanutencaoListMecanicomanutencao.getMecanico();
                mecanicomanutencaoListMecanicomanutencao.setMecanico(mecanico);
                mecanicomanutencaoListMecanicomanutencao = em.merge(mecanicomanutencaoListMecanicomanutencao);
                if (oldMecanicoOfMecanicomanutencaoListMecanicomanutencao != null) {
                    oldMecanicoOfMecanicomanutencaoListMecanicomanutencao.getMecanicomanutencaoList().remove(mecanicomanutencaoListMecanicomanutencao);
                    oldMecanicoOfMecanicomanutencaoListMecanicomanutencao = em.merge(oldMecanicoOfMecanicomanutencaoListMecanicomanutencao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mecanico mecanico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecanico persistentMecanico = em.find(Mecanico.class, mecanico.getIdmecanico());
            List<Mecancoferramenta> mecancoferramentaListOld = persistentMecanico.getMecancoferramentaList();
            List<Mecancoferramenta> mecancoferramentaListNew = mecanico.getMecancoferramentaList();
            List<Mecanicomanutencao> mecanicomanutencaoListOld = persistentMecanico.getMecanicomanutencaoList();
            List<Mecanicomanutencao> mecanicomanutencaoListNew = mecanico.getMecanicomanutencaoList();
            List<String> illegalOrphanMessages = null;
            for (Mecancoferramenta mecancoferramentaListOldMecancoferramenta : mecancoferramentaListOld) {
                if (!mecancoferramentaListNew.contains(mecancoferramentaListOldMecancoferramenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mecancoferramenta " + mecancoferramentaListOldMecancoferramenta + " since its mecanico field is not nullable.");
                }
            }
            for (Mecanicomanutencao mecanicomanutencaoListOldMecanicomanutencao : mecanicomanutencaoListOld) {
                if (!mecanicomanutencaoListNew.contains(mecanicomanutencaoListOldMecanicomanutencao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mecanicomanutencao " + mecanicomanutencaoListOldMecanicomanutencao + " since its mecanico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Mecancoferramenta> attachedMecancoferramentaListNew = new ArrayList<Mecancoferramenta>();
            for (Mecancoferramenta mecancoferramentaListNewMecancoferramentaToAttach : mecancoferramentaListNew) {
                mecancoferramentaListNewMecancoferramentaToAttach = em.getReference(mecancoferramentaListNewMecancoferramentaToAttach.getClass(), mecancoferramentaListNewMecancoferramentaToAttach.getMecancoferramentaPK());
                attachedMecancoferramentaListNew.add(mecancoferramentaListNewMecancoferramentaToAttach);
            }
            mecancoferramentaListNew = attachedMecancoferramentaListNew;
            mecanico.setMecancoferramentaList(mecancoferramentaListNew);
            List<Mecanicomanutencao> attachedMecanicomanutencaoListNew = new ArrayList<Mecanicomanutencao>();
            for (Mecanicomanutencao mecanicomanutencaoListNewMecanicomanutencaoToAttach : mecanicomanutencaoListNew) {
                mecanicomanutencaoListNewMecanicomanutencaoToAttach = em.getReference(mecanicomanutencaoListNewMecanicomanutencaoToAttach.getClass(), mecanicomanutencaoListNewMecanicomanutencaoToAttach.getMecanicomanutencaoPK());
                attachedMecanicomanutencaoListNew.add(mecanicomanutencaoListNewMecanicomanutencaoToAttach);
            }
            mecanicomanutencaoListNew = attachedMecanicomanutencaoListNew;
            mecanico.setMecanicomanutencaoList(mecanicomanutencaoListNew);
            mecanico = em.merge(mecanico);
            for (Mecancoferramenta mecancoferramentaListNewMecancoferramenta : mecancoferramentaListNew) {
                if (!mecancoferramentaListOld.contains(mecancoferramentaListNewMecancoferramenta)) {
                    Mecanico oldMecanicoOfMecancoferramentaListNewMecancoferramenta = mecancoferramentaListNewMecancoferramenta.getMecanico();
                    mecancoferramentaListNewMecancoferramenta.setMecanico(mecanico);
                    mecancoferramentaListNewMecancoferramenta = em.merge(mecancoferramentaListNewMecancoferramenta);
                    if (oldMecanicoOfMecancoferramentaListNewMecancoferramenta != null && !oldMecanicoOfMecancoferramentaListNewMecancoferramenta.equals(mecanico)) {
                        oldMecanicoOfMecancoferramentaListNewMecancoferramenta.getMecancoferramentaList().remove(mecancoferramentaListNewMecancoferramenta);
                        oldMecanicoOfMecancoferramentaListNewMecancoferramenta = em.merge(oldMecanicoOfMecancoferramentaListNewMecancoferramenta);
                    }
                }
            }
            for (Mecanicomanutencao mecanicomanutencaoListNewMecanicomanutencao : mecanicomanutencaoListNew) {
                if (!mecanicomanutencaoListOld.contains(mecanicomanutencaoListNewMecanicomanutencao)) {
                    Mecanico oldMecanicoOfMecanicomanutencaoListNewMecanicomanutencao = mecanicomanutencaoListNewMecanicomanutencao.getMecanico();
                    mecanicomanutencaoListNewMecanicomanutencao.setMecanico(mecanico);
                    mecanicomanutencaoListNewMecanicomanutencao = em.merge(mecanicomanutencaoListNewMecanicomanutencao);
                    if (oldMecanicoOfMecanicomanutencaoListNewMecanicomanutencao != null && !oldMecanicoOfMecanicomanutencaoListNewMecanicomanutencao.equals(mecanico)) {
                        oldMecanicoOfMecanicomanutencaoListNewMecanicomanutencao.getMecanicomanutencaoList().remove(mecanicomanutencaoListNewMecanicomanutencao);
                        oldMecanicoOfMecanicomanutencaoListNewMecanicomanutencao = em.merge(oldMecanicoOfMecanicomanutencaoListNewMecanicomanutencao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mecanico.getIdmecanico();
                if (findMecanico(id) == null) {
                    throw new NonexistentEntityException("The mecanico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecanico mecanico;
            try {
                mecanico = em.getReference(Mecanico.class, id);
                mecanico.getIdmecanico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mecanico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mecancoferramenta> mecancoferramentaListOrphanCheck = mecanico.getMecancoferramentaList();
            for (Mecancoferramenta mecancoferramentaListOrphanCheckMecancoferramenta : mecancoferramentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mecanico (" + mecanico + ") cannot be destroyed since the Mecancoferramenta " + mecancoferramentaListOrphanCheckMecancoferramenta + " in its mecancoferramentaList field has a non-nullable mecanico field.");
            }
            List<Mecanicomanutencao> mecanicomanutencaoListOrphanCheck = mecanico.getMecanicomanutencaoList();
            for (Mecanicomanutencao mecanicomanutencaoListOrphanCheckMecanicomanutencao : mecanicomanutencaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mecanico (" + mecanico + ") cannot be destroyed since the Mecanicomanutencao " + mecanicomanutencaoListOrphanCheckMecanicomanutencao + " in its mecanicomanutencaoList field has a non-nullable mecanico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(mecanico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mecanico> findMecanicoEntities() {
        return findMecanicoEntities(true, -1, -1);
    }

    public List<Mecanico> findMecanicoEntities(int maxResults, int firstResult) {
        return findMecanicoEntities(false, maxResults, firstResult);
    }

    private List<Mecanico> findMecanicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mecanico.class));
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

    public Mecanico findMecanico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mecanico.class, id);
        } finally {
            em.close();
        }
    }

    public int getMecanicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mecanico> rt = cq.from(Mecanico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public List<Mecanico> getMecanicoAsc() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Mecanico p order by p.nome asc");
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
      
         public List<Mecanico> getMecanicos(Entradaviatura e) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Mecanico p where p.mecanicoPK.identrada = :e order by p.data desc");
            q.setParameter("e", e.getIdentrada());
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
