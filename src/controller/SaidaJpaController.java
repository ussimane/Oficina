/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Produto;
import modelo.Saida;
import modelo.SaidaPK;
import modelo.Tiposaida;
import modelo.Vendedor;

/**
 *
 * @author USSIMANE
 */
public class SaidaJpaController implements Serializable {

    public SaidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Saida saida) throws PreexistingEntityException, Exception {
        if (saida.getSaidaPK() == null) {
            saida.setSaidaPK(new SaidaPK());
        }
        saida.getSaidaPK().setIdproduto(saida.getSaidaPK().getIdproduto());
        saida.getSaidaPK().setData(saida.getSaidaPK().getData());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = saida.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                saida.setProduto(produto);
            }
             Tiposaida tiposaida = saida.getTiposaida();
            if (tiposaida != null) {
                tiposaida = em.getReference(tiposaida.getClass(), tiposaida.getIdtiposaida());
                saida.setTiposaida(tiposaida);
            }
             Vendedor utilizador = saida.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                saida.setUtilizador(utilizador);
            }
            em.persist(saida);
            if (produto != null) {
                produto.getSaidaList().add(saida);
                produto = em.merge(produto);
            }
            if (tiposaida != null) {
                tiposaida.getSaidaList().add(saida);
                tiposaida = em.merge(tiposaida);
            }
            if (utilizador != null) {
                utilizador.getSaidaList().add(saida);
                utilizador = em.merge(utilizador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaida(saida.getSaidaPK()) != null) {
                throw new PreexistingEntityException("Saida " + saida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(Saida saida,EntityManager em) throws PreexistingEntityException, Exception {
        if (saida.getSaidaPK() == null) {
            saida.setSaidaPK(new SaidaPK());
        }
        saida.getSaidaPK().setIdproduto(saida.getSaidaPK().getIdproduto());
        saida.getSaidaPK().setData(saida.getSaidaPK().getData());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Produto produto = saida.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                saida.setProduto(produto);
            }
              Tiposaida tiposaida = saida.getTiposaida();
            if (tiposaida != null) {
                tiposaida = em.getReference(tiposaida.getClass(), tiposaida.getIdtiposaida());
                saida.setTiposaida(tiposaida);
            }
            Vendedor utilizador = saida.getUtilizador();
            if (utilizador != null) {
                utilizador = em.getReference(utilizador.getClass(), utilizador.getIdvendedor());
                saida.setUtilizador(utilizador);
            }
            em.persist(saida);
            if (produto != null) {
                produto.getSaidaList().add(saida);
                produto = em.merge(produto);
            }
             if (tiposaida != null) {
                tiposaida.getSaidaList().add(saida);
                tiposaida = em.merge(tiposaida);
            }
             if (utilizador != null) {
                utilizador.getSaidaList().add(saida);
                utilizador = em.merge(utilizador);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findSaida(saida.getSaidaPK()) != null) {
//                throw new PreexistingEntityException("Saida " + saida + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Saida saida) throws NonexistentEntityException, Exception {
        saida.getSaidaPK().setIdproduto(saida.getProduto().getIdproduto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saida persistentSaida = em.find(Saida.class, saida.getSaidaPK());
            Produto produtoOld = persistentSaida.getProduto();
            Produto produtoNew = saida.getProduto();
             Tiposaida tiposaidaOld = persistentSaida.getTiposaida();
            Tiposaida tiposaidaNew = saida.getTiposaida();
             Vendedor utilizadorOld = persistentSaida.getUtilizador();
            Vendedor utilizadorNew = saida.getUtilizador();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                saida.setProduto(produtoNew);
            }
            if (tiposaidaNew != null) {
                tiposaidaNew = em.getReference(tiposaidaNew.getClass(), tiposaidaNew.getIdtiposaida());
                saida.setTiposaida(tiposaidaNew);
            }
            if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getIdvendedor());
                saida.setUtilizador(utilizadorNew);
            }
            saida = em.merge(saida);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getSaidaList().remove(saida);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getSaidaList().add(saida);
                produtoNew = em.merge(produtoNew);
            }
             if (tiposaidaOld != null && !tiposaidaOld.equals(tiposaidaNew)) {
                tiposaidaOld.getSaidaList().remove(saida);
                tiposaidaOld = em.merge(tiposaidaOld);
            }
            if (tiposaidaNew != null && !tiposaidaNew.equals(tiposaidaOld)) {
                tiposaidaNew.getSaidaList().add(saida);
                tiposaidaNew = em.merge(tiposaidaNew);
            }
            if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getSaidaList().remove(saida);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getSaidaList().add(saida);
                utilizadorNew = em.merge(utilizadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SaidaPK id = saida.getSaidaPK();
                if (findSaida(id) == null) {
                    throw new NonexistentEntityException("The saida with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Saida persistentSaida,Saida saida,EntityManager em) throws NonexistentEntityException, Exception {
        saida.getSaidaPK().setIdproduto(saida.getProduto().getIdproduto());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Saida persistentSaida = em.find(Saida.class, saida.getSaidaPK());
            Produto produtoOld = persistentSaida.getProduto();
            Produto produtoNew = saida.getProduto();
             Tiposaida tiposaidaOld = persistentSaida.getTiposaida();
            Tiposaida tiposaidaNew = saida.getTiposaida();
            Vendedor utilizadorOld = persistentSaida.getUtilizador();
            Vendedor utilizadorNew = saida.getUtilizador();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                saida.setProduto(produtoNew);
            }
             if (tiposaidaNew != null) {
                tiposaidaNew = em.getReference(tiposaidaNew.getClass(), tiposaidaNew.getIdtiposaida());
                saida.setTiposaida(tiposaidaNew);
            }
             if (utilizadorNew != null) {
                utilizadorNew = em.getReference(utilizadorNew.getClass(), utilizadorNew.getIdvendedor());
                saida.setUtilizador(utilizadorNew);
            }
            saida = em.merge(saida);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getSaidaList().remove(saida);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getSaidaList().add(saida);
                produtoNew = em.merge(produtoNew);
            }
             if (tiposaidaOld != null && !tiposaidaOld.equals(tiposaidaNew)) {
                tiposaidaOld.getSaidaList().remove(saida);
                tiposaidaOld = em.merge(tiposaidaOld);
            }
            if (tiposaidaNew != null && !tiposaidaNew.equals(tiposaidaOld)) {
                tiposaidaNew.getSaidaList().add(saida);
                tiposaidaNew = em.merge(tiposaidaNew);
            }
             if (utilizadorOld != null && !utilizadorOld.equals(utilizadorNew)) {
                utilizadorOld.getSaidaList().remove(saida);
                utilizadorOld = em.merge(utilizadorOld);
            }
            if (utilizadorNew != null && !utilizadorNew.equals(utilizadorOld)) {
                utilizadorNew.getSaidaList().add(saida);
                utilizadorNew = em.merge(utilizadorNew);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                SaidaPK id = saida.getSaidaPK();
//                if (findSaida(id) == null) {
//                    throw new NonexistentEntityException("The saida with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(SaidaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saida saida;
            try {
                saida = em.getReference(Saida.class, id);
                saida.getSaidaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saida with id " + id + " no longer exists.", enfe);
            }
            Produto produto = saida.getProduto();
            if (produto != null) {
                produto.getSaidaList().remove(saida);
                produto = em.merge(produto);
            }
            Tiposaida tiposaida = saida.getTiposaida();
            if (tiposaida != null) {
                tiposaida.getSaidaList().remove(saida);
                tiposaida = em.merge(tiposaida);
            }
            Vendedor utilizador = saida.getUtilizador();
            if (utilizador != null) {
                utilizador.getSaidaList().remove(saida);
                utilizador = em.merge(utilizador);
            }
            em.remove(saida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void destroy(SaidaPK id,EntityManager em) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Saida saida;
            try {
                saida = em.getReference(Saida.class, id);
                saida.getSaidaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saida with id " + id + " no longer exists.", enfe);
            }
            Produto produto = saida.getProduto();
            if (produto != null) {
                produto.getSaidaList().remove(saida);
                produto = em.merge(produto);
            }
             Tiposaida tiposaida = saida.getTiposaida();
            if (tiposaida != null) {
                tiposaida.getSaidaList().remove(saida);
                tiposaida = em.merge(tiposaida);
            }
            Vendedor utilizador = saida.getUtilizador();
            if (utilizador != null) {
                utilizador.getSaidaList().remove(saida);
                utilizador = em.merge(utilizador);
            }
            em.remove(saida);
         //   em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Saida> findSaidaEntities() {
        return findSaidaEntities(true, -1, -1);
    }

    public List<Saida> findSaidaEntities(int maxResults, int firstResult) {
        return findSaidaEntities(false, maxResults, firstResult);
    }

    private List<Saida> findSaidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Saida.class));
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

    public Saida findSaida(SaidaPK id) {
        EntityManager em = getEntityManager();
        try {
            Saida s = em.find(Saida.class, id);
            em.refresh(s);
            return s;
        } finally {
            em.close();
        }
    }
    
    public Saida findSaidaS(SaidaPK id,EntityManager em) {
//        EntityManager em = getEntityManager();
//        try {
            Saida s = em.find(Saida.class, id);
            em.refresh(s,LockModeType.PESSIMISTIC_WRITE);
            return s;
//        } finally {
//            em.close();
//        }
    }

    public int getSaidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Saida> rt = cq.from(Saida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Saida> getSaidaPeriodo(Date i, Date f, Produto p) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            if (f == null) {
                q = em.createQuery("from Saida v where v.saidaPK.data >= :i and  v.saidaPK.idproduto = :p");
                i.setHours(1);
                q.setParameter("i", i);
                q.setParameter("p", p.getIdproduto());
            } else {
                q = em.createQuery("from Saida v where v.saidaPK.data >= :i and v.saidaPK.data <= :f and v.saidaPK.idproduto = :p");
                i.setHours(1);
                f.setHours(23);
                q.setParameter("i", i);
                q.setParameter("f", f);
                q.setParameter("p", p.getIdproduto());
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boolean getSaidaM(Date d) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Saida v where v.datae = :d");
            q.setParameter("d", d);
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }

}
