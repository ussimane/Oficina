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
import modelo.Manutencao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Tipomanutencao;

/**
 *
 * @author Ussimane
 */
public class TipomanutencaoJpaController implements Serializable {

    public TipomanutencaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipomanutencao tipomanutencao) {
        if (tipomanutencao.getManutencaoList() == null) {
            tipomanutencao.setManutencaoList(new ArrayList<Manutencao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Manutencao> attachedManutencaoList = new ArrayList<Manutencao>();
            for (Manutencao manutencaoListManutencaoToAttach : tipomanutencao.getManutencaoList()) {
                manutencaoListManutencaoToAttach = em.getReference(manutencaoListManutencaoToAttach.getClass(), manutencaoListManutencaoToAttach.getManutencaoPK());
                attachedManutencaoList.add(manutencaoListManutencaoToAttach);
            }
            tipomanutencao.setManutencaoList(attachedManutencaoList);
            em.persist(tipomanutencao);
            for (Manutencao manutencaoListManutencao : tipomanutencao.getManutencaoList()) {
                Tipomanutencao oldIdtipomanutencaoOfManutencaoListManutencao = manutencaoListManutencao.getIdtipomanutencao();
                manutencaoListManutencao.setIdtipomanutencao(tipomanutencao);
                manutencaoListManutencao = em.merge(manutencaoListManutencao);
                if (oldIdtipomanutencaoOfManutencaoListManutencao != null) {
                    oldIdtipomanutencaoOfManutencaoListManutencao.getManutencaoList().remove(manutencaoListManutencao);
                    oldIdtipomanutencaoOfManutencaoListManutencao = em.merge(oldIdtipomanutencaoOfManutencaoListManutencao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipomanutencao tipomanutencao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipomanutencao persistentTipomanutencao = em.find(Tipomanutencao.class, tipomanutencao.getIdtipomanutencao());
            List<Manutencao> manutencaoListOld = persistentTipomanutencao.getManutencaoList();
            List<Manutencao> manutencaoListNew = tipomanutencao.getManutencaoList();
            List<String> illegalOrphanMessages = null;
            for (Manutencao manutencaoListOldManutencao : manutencaoListOld) {
                if (!manutencaoListNew.contains(manutencaoListOldManutencao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Manutencao " + manutencaoListOldManutencao + " since its idtipomanutencao field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Manutencao> attachedManutencaoListNew = new ArrayList<Manutencao>();
            for (Manutencao manutencaoListNewManutencaoToAttach : manutencaoListNew) {
                manutencaoListNewManutencaoToAttach = em.getReference(manutencaoListNewManutencaoToAttach.getClass(), manutencaoListNewManutencaoToAttach.getManutencaoPK());
                attachedManutencaoListNew.add(manutencaoListNewManutencaoToAttach);
            }
            manutencaoListNew = attachedManutencaoListNew;
            tipomanutencao.setManutencaoList(manutencaoListNew);
            tipomanutencao = em.merge(tipomanutencao);
            for (Manutencao manutencaoListNewManutencao : manutencaoListNew) {
                if (!manutencaoListOld.contains(manutencaoListNewManutencao)) {
                    Tipomanutencao oldIdtipomanutencaoOfManutencaoListNewManutencao = manutencaoListNewManutencao.getIdtipomanutencao();
                    manutencaoListNewManutencao.setIdtipomanutencao(tipomanutencao);
                    manutencaoListNewManutencao = em.merge(manutencaoListNewManutencao);
                    if (oldIdtipomanutencaoOfManutencaoListNewManutencao != null && !oldIdtipomanutencaoOfManutencaoListNewManutencao.equals(tipomanutencao)) {
                        oldIdtipomanutencaoOfManutencaoListNewManutencao.getManutencaoList().remove(manutencaoListNewManutencao);
                        oldIdtipomanutencaoOfManutencaoListNewManutencao = em.merge(oldIdtipomanutencaoOfManutencaoListNewManutencao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipomanutencao.getIdtipomanutencao();
                if (findTipomanutencao(id) == null) {
                    throw new NonexistentEntityException("The tipomanutencao with id " + id + " no longer exists.");
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
            Tipomanutencao tipomanutencao;
            try {
                tipomanutencao = em.getReference(Tipomanutencao.class, id);
                tipomanutencao.getIdtipomanutencao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipomanutencao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Manutencao> manutencaoListOrphanCheck = tipomanutencao.getManutencaoList();
            for (Manutencao manutencaoListOrphanCheckManutencao : manutencaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipomanutencao (" + tipomanutencao + ") cannot be destroyed since the Manutencao " + manutencaoListOrphanCheckManutencao + " in its manutencaoList field has a non-nullable idtipomanutencao field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipomanutencao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipomanutencao> findTipomanutencaoEntities() {
        return findTipomanutencaoEntities(true, -1, -1);
    }

    public List<Tipomanutencao> findTipomanutencaoEntities(int maxResults, int firstResult) {
        return findTipomanutencaoEntities(false, maxResults, firstResult);
    }

    private List<Tipomanutencao> findTipomanutencaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipomanutencao.class));
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

    public Tipomanutencao findTipomanutencao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipomanutencao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipomanutencaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipomanutencao> rt = cq.from(Tipomanutencao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
     public List<Tipomanutencao> getTipomanuteAsc() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Tipomanutencao p order by p.designacao asc");
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
