/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Produto;
import modelo.Venda;
import modelo.Vendedor;

/**
 *
 * @author USSIMANE
 */
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void create(Venda venda,EntityManager em) {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            em.persist(venda);
//            em.getTransaction().commit();
//        } finally   
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Venda venda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            venda = em.merge(venda);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = venda.getIdvenda();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Venda venda,EntityManager em) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            venda = em.merge(venda);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Long id = venda.getIdvenda();
//                if (findVenda(id) == null) {
//                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getIdvenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
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

    public Venda findVenda(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    //////////////////////////////////////////////////////////////////////////////

    public List<Venda> getVendaPeriodo(Date i, Date f, Object p) {
        EntityManager em = getEntityManager();
        Query q = null;
        try {
            if (p==null) {
                if (f == null) {
                    q = em.createQuery("from Venda v where v.datavenda >= :i");
                    i.setHours(1);
                    q.setParameter("i", i);
                } else {
                    q = em.createQuery("from Venda v where v.datavenda >= :i and v.datavenda <= :f");
                    i.setHours(1);
                    f.setHours(23);
                    q.setParameter("i", i);
                    q.setParameter("f", f);
                }
            } else {
                if (f == null) {
                    q = em.createQuery("from Venda v where v.datavenda >= :i and v.idproduto = :p");
                    i.setHours(1);
                    q.setParameter("i", i);
                    q.setParameter("p", (Produto)p);
                } else {
                    q = em.createQuery("from Venda v where v.datavenda >= :i and v.datavenda <= :f and v.idproduto = :p");
                    i.setHours(1);
                    f.setHours(23);
                    q.setParameter("i", i);
                    q.setParameter("f", f);
                    q.setParameter("p", (Produto)p);
                }
            }
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Venda> getFacturaPeriodo(Date i, Date f, int j, Object ve) {
        EntityManager em = getEntityManager();
        Query q = null;
        try {
            if (ve.equals("Todos")) {
                if (j == 0) {
                    if (f == null) {
                        q = em.createQuery("from Venda v where v.datavenda >= :i");
                        i.setHours(1);
                        q.setParameter("i", i);
                    } else {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and v.datavenda <= :f");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                    }
                } else {
                    if (f == null) {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and ((v.seriefactura is null"
                                + " and :s='1') or (v.seriefactura is not null and :s = '0'))");
                        i.setHours(1);
                        q.setParameter("i", i);
                        if (j == 1) {
                            q.setParameter("s", "1");
                        } else {
                            q.setParameter("s", "0");
                        }
                    } else {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and v.datavenda <= :f and ((v.seriefactura is null"
                                + " and :s='1') or (v.seriefactura is not null and :s = '0'))");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                        if (j == 1) {
                            q.setParameter("s", "1");
                        } else {
                            q.setParameter("s", "0");
                        }
                    }
                }
            } else {
                if (j == 0) {
                    if (f == null) {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and v.idvendedor = :ve");
                        i.setHours(1);
                        q.setParameter("i", i);
                        q.setParameter("ve", ve);
                    } else {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and v.datavenda <= :f and v.idvendedor = :ve");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                        q.setParameter("ve", ve);
                    }
                } else {
                    if (f == null) {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and ((v.seriefactura is null"
                                + " and :s='1') or (v.seriefactura is not null and :s='0')) and v.idvendedor = :ve");
                        i.setHours(1);
                        q.setParameter("i", i);
                        if (j == 1) {
                            q.setParameter("s", "1");
                        } else {
                            q.setParameter("s", "0");
                        }
                        q.setParameter("ve", ve);
                    } else {
                        q = em.createQuery("from Venda v where v.datavenda >= :i and v.datavenda <= :f and ((v.seriefactura is null"
                                + " and :s='1') or (v.seriefactura is not null and :s='0')) and v.idvendedor = :ve");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                        if (j == 1) {
                            q.setParameter("s", "1");
                        } else {
                            q.setParameter("s", "0");
                        }
                        q.setParameter("ve", ve);
                    }
                }
            }
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Venda> getVenda(Date i) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            q = em.createQuery("from Venda v where v.datavenda = :i order by v.datae asc");
            q.setParameter("i", i);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Venda> getFacturaSerie(String s) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            q = em.createQuery("from Venda v where v.seriefactura = :s group by v.seriefactura");
            q.setParameter("s", s);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Venda> getFacturaDPag(Date d) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            q = em.createQuery("from Venda v where v.datapag > :d and v.datapag < :f");
            d.setHours(1);
            q.setParameter("d", d);
            Date f = new Date();
            f.setTime(d.getTime());
            f.setHours(23);
            q.setParameter("f", f);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boolean getExist(Produto p) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Venda v where v.idproduto = :p");
            q.setParameter("p", p);
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }

    public double getVendaF(Date v) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select sum(v.valor) from Venda v where v.datavenda= :v "
                    + "group by v.datavenda");
            q.setParameter("v", v);
            return (double) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Boolean getExistU(Vendedor p) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Venda v where v.idvendedor = :p");
            q.setParameter("p", p);
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }

    }

    public Boolean getExistC(Cliente p) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Venda v where v.idcliente = :p");
            q.setParameter("p", p);
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }
}
