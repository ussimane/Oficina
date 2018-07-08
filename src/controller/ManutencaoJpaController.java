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
import modelo.Manutencao;
import modelo.ManutencaoPK;
import modelo.Produto;
import modelo.Tipomanutencao;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class ManutencaoJpaController implements Serializable {

    public ManutencaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Manutencao manutencao) throws PreexistingEntityException, Exception {
        if (manutencao.getManutencaoPK() == null) {
            manutencao.setManutencaoPK(new ManutencaoPK());
        }
        manutencao.getManutencaoPK().setIdentrada(manutencao.getEntradaviatura().getIdentrada());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradaviatura entradaviatura = manutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura = em.getReference(entradaviatura.getClass(), entradaviatura.getIdentrada());
                manutencao.setEntradaviatura(entradaviatura);
            }
            Tipomanutencao idtipomanutencao = manutencao.getIdtipomanutencao();
            if (idtipomanutencao != null) {
                idtipomanutencao = em.getReference(idtipomanutencao.getClass(), idtipomanutencao.getIdtipomanutencao());
                manutencao.setIdtipomanutencao(idtipomanutencao);
            }
            Vendedor utilizador = manutencao.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                manutencao.setUtilizador(utilizador);
            }
            em.persist(manutencao);
            if (entradaviatura != null) {
                entradaviatura.getManutencaoList().add(manutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            if (idtipomanutencao != null) {
                idtipomanutencao.getManutencaoList().add(manutencao);
                idtipomanutencao = em.merge(idtipomanutencao);
            }
            if (utilizador != null) {
                utilizador.getManutencaoList().add(manutencao);
                utilizador = em.merge(utilizador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findManutencao(manutencao.getManutencaoPK()) != null) {
                throw new PreexistingEntityException("Manutencao " + manutencao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Manutencao manutencao) throws NonexistentEntityException, Exception {
        manutencao.getManutencaoPK().setIdentrada(manutencao.getEntradaviatura().getIdentrada());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Manutencao persistentManutencao = em.find(Manutencao.class, manutencao.getManutencaoPK());
            Entradaviatura entradaviaturaOld = persistentManutencao.getEntradaviatura();
            Entradaviatura entradaviaturaNew = manutencao.getEntradaviatura();
            Tipomanutencao idtipomanutencaoOld = persistentManutencao.getIdtipomanutencao();
            Tipomanutencao idtipomanutencaoNew = manutencao.getIdtipomanutencao();
            Vendedor utilizadorOld = persistentManutencao.getUtilizador();
            Vendedor utilizadorNew = manutencao.getUtilizador();
            if (entradaviaturaNew != null) {
                entradaviaturaNew = em.getReference(entradaviaturaNew.getClass(), entradaviaturaNew.getIdentrada());
                manutencao.setEntradaviatura(entradaviaturaNew);
            }
            if (idtipomanutencaoNew != null) {
                idtipomanutencaoNew = em.getReference(idtipomanutencaoNew.getClass(), idtipomanutencaoNew.getIdtipomanutencao());
                manutencao.setIdtipomanutencao(idtipomanutencaoNew);
            }
            if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getIdvendedor());
                manutencao.setUtilizador(utilizadorNew);
            }
            manutencao = em.merge(manutencao);
            if (entradaviaturaOld != null && !entradaviaturaOld.equals(entradaviaturaNew)) {
                entradaviaturaOld.getManutencaoList().remove(manutencao);
                entradaviaturaOld = em.merge(entradaviaturaOld);
            }
            if (entradaviaturaNew != null && !entradaviaturaNew.equals(entradaviaturaOld)) {
                entradaviaturaNew.getManutencaoList().add(manutencao);
                entradaviaturaNew = em.merge(entradaviaturaNew);
            }
            if (idtipomanutencaoOld != null && !idtipomanutencaoOld.equals(idtipomanutencaoNew)) {
                idtipomanutencaoOld.getManutencaoList().remove(manutencao);
                idtipomanutencaoOld = em.merge(idtipomanutencaoOld);
            }
            if (idtipomanutencaoNew != null && !idtipomanutencaoNew.equals(idtipomanutencaoOld)) {
                idtipomanutencaoNew.getManutencaoList().add(manutencao);
                idtipomanutencaoNew = em.merge(idtipomanutencaoNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getManutencaoList().remove(manutencao);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getManutencaoList().add(manutencao);
                utilizadorNew = em.merge(utilizadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ManutencaoPK id = manutencao.getManutencaoPK();
                if (findManutencao(id) == null) {
                    throw new NonexistentEntityException("The manutencao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ManutencaoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Manutencao manutencao;
            try {
                manutencao = em.getReference(Manutencao.class, id);
                manutencao.getManutencaoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The manutencao with id " + id + " no longer exists.", enfe);
            }
            Entradaviatura entradaviatura = manutencao.getEntradaviatura();
            if (entradaviatura != null) {
                entradaviatura.getManutencaoList().remove(manutencao);
                entradaviatura = em.merge(entradaviatura);
            }
            Tipomanutencao idtipomanutencao = manutencao.getIdtipomanutencao();
            if (idtipomanutencao != null) {
                idtipomanutencao.getManutencaoList().remove(manutencao);
                idtipomanutencao = em.merge(idtipomanutencao);
            }
            Vendedor utilizador = manutencao.getUtilizador();
            if (utilizador != null) {
                utilizador.getManutencaoList().remove(manutencao);
                utilizador = em.merge(utilizador);
            }
            em.remove(manutencao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Manutencao> findManutencaoEntities() {
        return findManutencaoEntities(true, -1, -1);
    }

    public List<Manutencao> findManutencaoEntities(int maxResults, int firstResult) {
        return findManutencaoEntities(false, maxResults, firstResult);
    }

    private List<Manutencao> findManutencaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Manutencao.class));
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

    public Manutencao findManutencao(ManutencaoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Manutencao.class, id);
        } finally {
            em.close();
        }
    }

    public int getManutencaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Manutencao> rt = cq.from(Manutencao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Manutencao> getManutencao(Entradaviatura e) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Manutencao p where p.manutencaoPK.identrada = :e order by p.manutencaoPK.data desc");
             q.setParameter("e", e.getIdentrada());
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
     public int existeTipoManutencao(Tipomanutencao i) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Manutencao p where p.idtipomanutencao = :i");
            q.setParameter("i", i);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
