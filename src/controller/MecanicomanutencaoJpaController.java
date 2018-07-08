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
import modelo.Mecanico;
import modelo.Mecanicomanutencao;
import modelo.MecanicomanutencaoPK;

/**
 *
 * @author Ussimane
 */
public class MecanicomanutencaoJpaController implements Serializable {

    public MecanicomanutencaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mecanicomanutencao mecanicomanutencao) throws PreexistingEntityException, Exception {
        if (mecanicomanutencao.getMecanicomanutencaoPK() == null) {
            mecanicomanutencao.setMecanicomanutencaoPK(new MecanicomanutencaoPK());
        }
        mecanicomanutencao.getMecanicomanutencaoPK().setIdentrada(mecanicomanutencao.getEntradaviatura().getIdentrada());
        mecanicomanutencao.getMecanicomanutencaoPK().setIdmecanico(mecanicomanutencao.getMecanico().getIdmecanico());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradaviatura entradaviatura = mecanicomanutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura = em.getReference(entradaviatura.getClass(), entradaviatura.getIdentrada());
                mecanicomanutencao.setEntradaviatura(entradaviatura);
            }
            Mecanico mecanico = mecanicomanutencao.getMecanico();
            if (mecanico != null) {
                mecanico = em.getReference(mecanico.getClass(), mecanico.getIdmecanico());
                mecanicomanutencao.setMecanico(mecanico);
            }
            em.persist(mecanicomanutencao);
            if (entradaviatura != null) {
                entradaviatura.getMecanicomanutencaoList().add(mecanicomanutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            if (mecanico != null) {
                mecanico.getMecanicomanutencaoList().add(mecanicomanutencao);
                mecanico = em.merge(mecanico);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMecanicomanutencao(mecanicomanutencao.getMecanicomanutencaoPK()) != null) {
                throw new PreexistingEntityException("Mecanicomanutencao " + mecanicomanutencao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mecanicomanutencao mecanicomanutencao) throws NonexistentEntityException, Exception {
        mecanicomanutencao.getMecanicomanutencaoPK().setIdentrada(mecanicomanutencao.getEntradaviatura().getIdentrada());
        mecanicomanutencao.getMecanicomanutencaoPK().setIdmecanico(mecanicomanutencao.getMecanico().getIdmecanico());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecanicomanutencao persistentMecanicomanutencao = em.find(Mecanicomanutencao.class, mecanicomanutencao.getMecanicomanutencaoPK());
            Entradaviatura entradaviaturaOld = persistentMecanicomanutencao.getEntradaviatura();
            Entradaviatura entradaviaturaNew = mecanicomanutencao.getEntradaviatura();
            Mecanico mecanicoOld = persistentMecanicomanutencao.getMecanico();
            Mecanico mecanicoNew = mecanicomanutencao.getMecanico();
            if (entradaviaturaNew != null) {
                entradaviaturaNew = em.getReference(entradaviaturaNew.getClass(), entradaviaturaNew.getIdentrada());
                mecanicomanutencao.setEntradaviatura(entradaviaturaNew);
            }
            if (mecanicoNew != null) {
                mecanicoNew = em.getReference(mecanicoNew.getClass(), mecanicoNew.getIdmecanico());
                mecanicomanutencao.setMecanico(mecanicoNew);
            }
            mecanicomanutencao = em.merge(mecanicomanutencao);
            if (entradaviaturaOld != null && !entradaviaturaOld.equals(entradaviaturaNew)) {
                entradaviaturaOld.getMecanicomanutencaoList().remove(mecanicomanutencao);
                entradaviaturaOld = em.merge(entradaviaturaOld);
            }
            if (entradaviaturaNew != null && !entradaviaturaNew.equals(entradaviaturaOld)) {
                entradaviaturaNew.getMecanicomanutencaoList().add(mecanicomanutencao);
                entradaviaturaNew = em.merge(entradaviaturaNew);
            }
            if (mecanicoOld != null && !mecanicoOld.equals(mecanicoNew)) {
                mecanicoOld.getMecanicomanutencaoList().remove(mecanicomanutencao);
                mecanicoOld = em.merge(mecanicoOld);
            }
            if (mecanicoNew != null && !mecanicoNew.equals(mecanicoOld)) {
                mecanicoNew.getMecanicomanutencaoList().add(mecanicomanutencao);
                mecanicoNew = em.merge(mecanicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MecanicomanutencaoPK id = mecanicomanutencao.getMecanicomanutencaoPK();
                if (findMecanicomanutencao(id) == null) {
                    throw new NonexistentEntityException("The mecanicomanutencao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MecanicomanutencaoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecanicomanutencao mecanicomanutencao;
            try {
                mecanicomanutencao = em.getReference(Mecanicomanutencao.class, id);
                mecanicomanutencao.getMecanicomanutencaoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mecanicomanutencao with id " + id + " no longer exists.", enfe);
            }
            Entradaviatura entradaviatura = mecanicomanutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura.getMecanicomanutencaoList().remove(mecanicomanutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            Mecanico mecanico = mecanicomanutencao.getMecanico();
            if (mecanico != null) {
                mecanico.getMecanicomanutencaoList().remove(mecanicomanutencao);
                mecanico = em.merge(mecanico);
            }
            em.remove(mecanicomanutencao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mecanicomanutencao> findMecanicomanutencaoEntities() {
        return findMecanicomanutencaoEntities(true, -1, -1);
    }

    public List<Mecanicomanutencao> findMecanicomanutencaoEntities(int maxResults, int firstResult) {
        return findMecanicomanutencaoEntities(false, maxResults, firstResult);
    }

    private List<Mecanicomanutencao> findMecanicomanutencaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mecanicomanutencao.class));
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

    public Mecanicomanutencao findMecanicomanutencao(MecanicomanutencaoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mecanicomanutencao.class, id);
        } finally {
            em.close();
        }
    }

    public int getMecanicomanutencaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mecanicomanutencao> rt = cq.from(Mecanicomanutencao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public List<Mecanicomanutencao> getMecanicomanutencao(Entradaviatura e) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Mecanicomanutencao p where p.mecanicomanutencaoPK.identrada = :e order by p.data desc");
            q.setParameter("e", e.getIdentrada());
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
      
       public int existeMecanico(Mecanico m) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Mecanicomanutencao p where p.mecanico = :i");
            q.setParameter("i", m);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
