/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Venda;
import modelo.Vendedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Vendaanulada;

/**
 *
 * @author Ussimane
 */
public class VendaanuladaJpaController implements Serializable {

    public VendaanuladaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendaanulada vendaanulada) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Venda vendaOrphanCheck = vendaanulada.getVenda();
        if (vendaOrphanCheck != null) {
            Vendaanulada oldVendaanuladaOfVenda = vendaOrphanCheck.getVendaanulada();
            if (oldVendaanuladaOfVenda != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Venda " + vendaOrphanCheck + " already has an item of type Vendaanulada whose venda column cannot be null. Please make another selection for the venda field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda venda = vendaanulada.getVenda();
            if (venda != null) {
                venda = em.getReference(venda.getClass(), venda.getIdvenda());
                vendaanulada.setVenda(venda);
            }
            Vendedor idutilizador = vendaanulada.getIdutilizador();
            if (idutilizador != null) {
                idutilizador = em.getReference(idutilizador.getClass(), idutilizador.getIdvendedor());
                vendaanulada.setIdutilizador(idutilizador);
            }
            em.persist(vendaanulada);
            if (venda != null) {
                venda.setVendaanulada(vendaanulada);
                venda = em.merge(venda);
            }
            if (idutilizador != null) {
                idutilizador.getVendaanuladaList().add(vendaanulada);
                idutilizador = em.merge(idutilizador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVendaanulada(vendaanulada.getIdvenda()) != null) {
                throw new PreexistingEntityException("Vendaanulada " + vendaanulada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(Vendaanulada vendaanulada,EntityManager em) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Venda vendaOrphanCheck = vendaanulada.getVenda();
        if (vendaOrphanCheck != null) {
            Vendaanulada oldVendaanuladaOfVenda = vendaOrphanCheck.getVendaanulada();
            if (oldVendaanuladaOfVenda != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Venda " + vendaOrphanCheck + " already has an item of type Vendaanulada whose venda column cannot be null. Please make another selection for the venda field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Venda venda = vendaanulada.getVenda();
            if (venda != null) {
                venda = em.getReference(venda.getClass(), venda.getIdvenda());
                vendaanulada.setVenda(venda);
            }
            Vendedor idutilizador = vendaanulada.getIdutilizador();
            if (idutilizador != null) {
                idutilizador = em.getReference(idutilizador.getClass(), idutilizador.getIdvendedor());
                vendaanulada.setIdutilizador(idutilizador);
            }
            em.persist(vendaanulada);
            if (venda != null) {
                venda.setVendaanulada(vendaanulada);
                venda = em.merge(venda);
            }
            if (idutilizador != null) {
                idutilizador.getVendaanuladaList().add(vendaanulada);
                idutilizador = em.merge(idutilizador);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findVendaanulada(vendaanulada.getIdvenda()) != null) {
//                throw new PreexistingEntityException("Vendaanulada " + vendaanulada + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Vendaanulada vendaanulada) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendaanulada persistentVendaanulada = em.find(Vendaanulada.class, vendaanulada.getIdvenda());
            Venda vendaOld = persistentVendaanulada.getVenda();
            Venda vendaNew = vendaanulada.getVenda();
            Vendedor idutilizadorOld = persistentVendaanulada.getIdutilizador();
            Vendedor idutilizadorNew = vendaanulada.getIdutilizador();
            List<String> illegalOrphanMessages = null;
            if (vendaNew != null && !vendaNew.equals(vendaOld)) {
                Vendaanulada oldVendaanuladaOfVenda = vendaNew.getVendaanulada();
                if (oldVendaanuladaOfVenda != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Venda " + vendaNew + " already has an item of type Vendaanulada whose venda column cannot be null. Please make another selection for the venda field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (vendaNew != null) {
                vendaNew = em.getReference(vendaNew.getClass(), vendaNew.getIdvenda());
                vendaanulada.setVenda(vendaNew);
            }
            if (idutilizadorNew != null) {
                idutilizadorNew = em.getReference(idutilizadorNew.getClass(), idutilizadorNew.getIdvendedor());
                vendaanulada.setIdutilizador(idutilizadorNew);
            }
            vendaanulada = em.merge(vendaanulada);
            if (vendaOld != null && !vendaOld.equals(vendaNew)) {
                vendaOld.setVendaanulada(null);
                vendaOld = em.merge(vendaOld);
            }
            if (vendaNew != null && !vendaNew.equals(vendaOld)) {
                vendaNew.setVendaanulada(vendaanulada);
                vendaNew = em.merge(vendaNew);
            }
            if (idutilizadorOld != null && !idutilizadorOld.equals(idutilizadorNew)) {
                idutilizadorOld.getVendaanuladaList().remove(vendaanulada);
                idutilizadorOld = em.merge(idutilizadorOld);
            }
            if (idutilizadorNew != null && !idutilizadorNew.equals(idutilizadorOld)) {
                idutilizadorNew.getVendaanuladaList().add(vendaanulada);
                idutilizadorNew = em.merge(idutilizadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vendaanulada.getIdvenda();
                if (findVendaanulada(id) == null) {
                    throw new NonexistentEntityException("The vendaanulada with id " + id + " no longer exists.");
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
            Vendaanulada vendaanulada;
            try {
                vendaanulada = em.getReference(Vendaanulada.class, id);
                vendaanulada.getIdvenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendaanulada with id " + id + " no longer exists.", enfe);
            }
            Venda venda = vendaanulada.getVenda();
            if (venda != null) {
                venda.setVendaanulada(null);
                venda = em.merge(venda);
            }
            Vendedor idutilizador = vendaanulada.getIdutilizador();
            if (idutilizador != null) {
                idutilizador.getVendaanuladaList().remove(vendaanulada);
                idutilizador = em.merge(idutilizador);
            }
            em.remove(vendaanulada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendaanulada> findVendaanuladaEntities() {
        return findVendaanuladaEntities(true, -1, -1);
    }

    public List<Vendaanulada> findVendaanuladaEntities(int maxResults, int firstResult) {
        return findVendaanuladaEntities(false, maxResults, firstResult);
    }

    private List<Vendaanulada> findVendaanuladaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendaanulada.class));
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

    public Vendaanulada findVendaanulada(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendaanulada.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaanuladaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendaanulada> rt = cq.from(Vendaanulada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
