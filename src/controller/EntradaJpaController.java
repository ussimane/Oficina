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
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Fornecedor;
import modelo.Produto;

/**
 *
 * @author USSIMANE
 */
public class EntradaJpaController implements Serializable {

    public EntradaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entrada entrada) throws PreexistingEntityException, Exception {
        if (entrada.getEntradaPK() == null) {
            entrada.setEntradaPK(new EntradaPK());
        }
        entrada.getEntradaPK().setIdproduto(entrada.getEntradaPK().getIdproduto());
        entrada.getEntradaPK().setData(entrada.getEntradaPK().getData());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor idfornecedor = entrada.getIdfornecedor();
            if (idfornecedor != null) {
                idfornecedor = em.getReference(idfornecedor.getClass(), idfornecedor.getIdfornecedor());
                entrada.setIdfornecedor(idfornecedor);
            }
            Produto produto = entrada.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                entrada.setProduto(produto);
            }
            em.persist(entrada);
            if (idfornecedor != null) {
                idfornecedor.getEntradaList().add(entrada);
                idfornecedor = em.merge(idfornecedor);
            }
            if (produto != null) {
                produto.getEntradaList().add(entrada);
                produto = em.merge(produto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntrada(entrada.getEntradaPK()) != null) {
                throw new PreexistingEntityException("Entrada " + entrada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(Entrada entrada, EntityManager em) throws PreexistingEntityException, Exception {
        if (entrada.getEntradaPK() == null) {
            entrada.setEntradaPK(new EntradaPK());
        }
        entrada.getEntradaPK().setIdproduto(entrada.getEntradaPK().getIdproduto());
        entrada.getEntradaPK().setData(entrada.getEntradaPK().getData());
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Fornecedor idfornecedor = entrada.getIdfornecedor();
            if (idfornecedor != null) {
                idfornecedor = em.getReference(idfornecedor.getClass(), idfornecedor.getIdfornecedor());
                entrada.setIdfornecedor(idfornecedor);
            }
            Produto produto = entrada.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                entrada.setProduto(produto);
            }
            em.persist(entrada);
            if (idfornecedor != null) {
                idfornecedor.getEntradaList().add(entrada);
                idfornecedor = em.merge(idfornecedor);
            }
            if (produto != null) {
                produto.getEntradaList().add(entrada);
                produto = em.merge(produto);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findEntrada(entrada.getEntradaPK()) != null) {
//                throw new PreexistingEntityException("Entrada " + entrada + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(Entrada entrada) throws NonexistentEntityException, Exception {
        entrada.getEntradaPK().setIdproduto(entrada.getProduto().getIdproduto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrada persistentEntrada = em.find(Entrada.class, entrada.getEntradaPK());
            
            Fornecedor idfornecedorOld = persistentEntrada.getIdfornecedor();
            Fornecedor idfornecedorNew = entrada.getIdfornecedor();
            Produto produtoOld = persistentEntrada.getProduto();
            Produto produtoNew = entrada.getProduto();
            if (idfornecedorNew != null) {
                idfornecedorNew = em.getReference(idfornecedorNew.getClass(), idfornecedorNew.getIdfornecedor());
                entrada.setIdfornecedor(idfornecedorNew);
            }
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                entrada.setProduto(produtoNew);
            }
            entrada = em.merge(entrada);
            if (idfornecedorOld != null && !idfornecedorOld.equals(idfornecedorNew)) {
                idfornecedorOld.getEntradaList().remove(entrada);
                idfornecedorOld = em.merge(idfornecedorOld);
            }
            if (idfornecedorNew != null && !idfornecedorNew.equals(idfornecedorOld)) {
                idfornecedorNew.getEntradaList().add(entrada);
                idfornecedorNew = em.merge(idfornecedorNew);
            }
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getEntradaList().remove(entrada);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getEntradaList().add(entrada);
                produtoNew = em.merge(produtoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EntradaPK id = entrada.getEntradaPK();
                if (findEntrada(id) == null) {
                    throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Entrada persistentEntrada,Entrada entrada, EntityManager em) throws NonexistentEntityException, Exception {
        entrada.getEntradaPK().setIdproduto(entrada.getProduto().getIdproduto());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Entrada persistentEntrada = em.find(Entrada.class, entrada.getEntradaPK());
            
            Fornecedor idfornecedorOld = persistentEntrada.getIdfornecedor();
            Fornecedor idfornecedorNew = entrada.getIdfornecedor();
            Produto produtoOld = persistentEntrada.getProduto();
            Produto produtoNew = entrada.getProduto();
            if (idfornecedorNew != null) {
                idfornecedorNew = em.getReference(idfornecedorNew.getClass(), idfornecedorNew.getIdfornecedor());
                entrada.setIdfornecedor(idfornecedorNew);
            }
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                entrada.setProduto(produtoNew);
            }
            entrada = em.merge(entrada);
            if (idfornecedorOld != null && !idfornecedorOld.equals(idfornecedorNew)) {
                idfornecedorOld.getEntradaList().remove(entrada);
                idfornecedorOld = em.merge(idfornecedorOld);
            }
            if (idfornecedorNew != null && !idfornecedorNew.equals(idfornecedorOld)) {
                idfornecedorNew.getEntradaList().add(entrada);
                idfornecedorNew = em.merge(idfornecedorNew);
            }
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getEntradaList().remove(entrada);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getEntradaList().add(entrada);
                produtoNew = em.merge(produtoNew);
            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                EntradaPK id = entrada.getEntradaPK();
//                if (findEntrada(id) == null) {
//                    throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public int editVenda(Entrada entrada, EntityManager em) throws NonexistentEntityException, Exception {
//        entrada.getEntradaPK().setIdproduto(entrada.getProduto().getIdproduto());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
        int qv = entrada.getQv();
        Entrada persistentEntrada = em.find(Entrada.class, entrada.getEntradaPK());
        em.refresh(persistentEntrada,LockModeType.PESSIMISTIC_WRITE);
        int qvpersist = persistentEntrada.getQv();
        System.out.println("edit qv: "+qv+" persistQv: "+qvpersist);
        if (qvpersist < qv) {
            return qv - qvpersist;
        }else{
           persistentEntrada.setQv(qvpersist-qv);
        }
////        Fornecedor idfornecedorOld = persistentEntrada.getIdfornecedor();
////        Fornecedor idfornecedorNew = entrada.getIdfornecedor();
////        Produto produtoOld = persistentEntrada.getProduto();
////        Produto produtoNew = entrada.getProduto();
////        if (idfornecedorNew != null) {
////            idfornecedorNew = em.getReference(idfornecedorNew.getClass(), idfornecedorNew.getIdfornecedor());
////            entrada.setIdfornecedor(idfornecedorNew);
////        }
////        if (produtoNew != null) {
////            produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
////            entrada.setProduto(produtoNew);
////        }
        em.merge(persistentEntrada);
//        if (idfornecedorOld != null && !idfornecedorOld.equals(idfornecedorNew)) {
//            idfornecedorOld.getEntradaList().remove(entrada);
//            idfornecedorOld = em.merge(idfornecedorOld);
//        }
//        if (idfornecedorNew != null && !idfornecedorNew.equals(idfornecedorOld)) {
//            idfornecedorNew.getEntradaList().add(entrada);
//            idfornecedorNew = em.merge(idfornecedorNew);
//        }
//        if (produtoOld != null && !produtoOld.equals(produtoNew)) {
//            produtoOld.getEntradaList().remove(entrada);
//            produtoOld = em.merge(produtoOld);
//        }
//        if (produtoNew != null && !produtoNew.equals(produtoOld)) {
//            produtoNew.getEntradaList().add(entrada);
//            produtoNew = em.merge(produtoNew);
//        }
//         if(true){
//              do{
//                  System.out.println("ainda");
//              }while(true);
//            }
//        em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                EntradaPK id = entrada.getEntradaPK();
//                if (findEntrada(id) == null) {
//                    throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
        System.out.println("2-edit qv: "+qv+" persistQv: "+qvpersist);
        return qv - qvpersist;
    }

    public void refresh(Entrada e) {
        EntityManager em = null;
        em = getEntityManager();
        em.persist(e);
        em.close();
    }

    public void destroy(EntradaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrada entrada;
            try {
                entrada = em.getReference(Entrada.class, id);
                entrada.getEntradaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.", enfe);
            }
            Fornecedor idfornecedor = entrada.getIdfornecedor();
            if (idfornecedor != null) {
                idfornecedor.getEntradaList().remove(entrada);
                idfornecedor = em.merge(idfornecedor);
            }
            Produto produto = entrada.getProduto();
            if (produto != null) {
                produto.getEntradaList().remove(entrada);
                produto = em.merge(produto);
            }
            em.remove(entrada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void destroy(EntradaPK id,EntityManager em ) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Entrada entrada;
            try {
                entrada = em.getReference(Entrada.class, id);
                entrada.getEntradaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.", enfe);
            }
            Fornecedor idfornecedor = entrada.getIdfornecedor();
            if (idfornecedor != null) {
                idfornecedor.getEntradaList().remove(entrada);
                idfornecedor = em.merge(idfornecedor);
            }
            Produto produto = entrada.getProduto();
            if (produto != null) {
                produto.getEntradaList().remove(entrada);
                produto = em.merge(produto);
            }
            em.remove(entrada);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Entrada> findEntradaEntities() {
        return findEntradaEntities(true, -1, -1);
    }

    public List<Entrada> findEntradaEntities(int maxResults, int firstResult) {
        return findEntradaEntities(false, maxResults, firstResult);
    }

    private List<Entrada> findEntradaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entrada.class));
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

    public Entrada findEntrada(EntradaPK id) {
        EntityManager em = getEntityManager();
        try {
            Entrada e = em.find(Entrada.class, id);
            em.refresh(e);
            return e;
        } finally {
            em.close();
        }
    }
    
    public Entrada findEntradaS(EntradaPK id,EntityManager em) {
//        EntityManager em = getEntityManager();
//        try {
            Entrada e = em.find(Entrada.class, id);
            em.refresh(e,LockModeType.PESSIMISTIC_WRITE);
            return e;
//        } finally {
//            em.close();
//        }
    }

    public int getEntradaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entrada> rt = cq.from(Entrada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Entrada> getEntradaPeriodo(Date i, Date f, Object p, Object fo) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            if (fo.equals("Todos Fornec.")) {
                if (p == null) {
                    if (f == null) {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i order by v.entradaPK.data desc");
                        i.setHours(1);
                        q.setParameter("i", i);
                    } else {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.entradaPK.data <= :f order by v.entradaPK.data desc");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                    }
                } else {
                    if (f == null) {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.entradaPK.idproduto = :p order by v.entradaPK.data desc");
                        i.setHours(1);
                        q.setParameter("i", i);
                        q.setParameter("p", ((Produto) p).getIdproduto());
                    } else {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.entradaPK.data <= :f and v.entradaPK.idproduto = :p order by v.entradaPK.data desc");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                        q.setParameter("p", ((Produto) p).getIdproduto());
                    }
                }
            } else {
                if (p == null) {
                    if (f == null) {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.idfornecedor = :fo order by v.entradaPK.data desc");
                        i.setHours(1);
                        q.setParameter("i", i);
                        q.setParameter("fo", (Fornecedor) fo);
                    } else {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.entradaPK.data <= :f and v.idfornecedor = :fo order by v.entradaPK.data desc");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                        q.setParameter("fo", (Fornecedor) fo);
                    }
                } else {
                    if (f == null) {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.entradaPK.idproduto = :p and v.idfornecedor = :fo order by v.entradaPK.data desc");
                        i.setHours(1);
                        q.setParameter("i", i);
                        q.setParameter("p", ((Produto) p).getIdproduto());
                        q.setParameter("fo", (Fornecedor) fo);
                    } else {
                        q = em.createQuery("from Entrada v where v.dataref is null and v.entradaPK.data >= :i and v.entradaPK.data <= :f and v.entradaPK.idproduto = :p order by v.entradaPK.data desc"
                                + " and v.idfornecedor = :fo");
                        i.setHours(1);
                        f.setHours(23);
                        q.setParameter("i", i);
                        q.setParameter("f", f);
                        q.setParameter("p", ((Produto) p).getIdproduto());
                        q.setParameter("fo", (Fornecedor) fo);
                    }
                }
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boolean getExistF(Fornecedor p) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.idfornecedor = :p");
            q.setParameter("p", p);
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }

    public List<Entrada> getEntFull(Produto p) {  //Entradas que ainda possuem valores no stock ou no balcao
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.produto = :p and "
                    + "(v.qtd>v.qs or v.qv>0) order by v.entradaPK.data desc");
            q.setParameter("p", p);
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Entrada> getEntStock(Produto p) {  //Entradas que ainda possuem valores no stock
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.produto = :p and "
                    + "v.qtd>v.qs order by v.entradaPK.data asc");
            q.setParameter("p", p);
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

//    public List<Entrada> getEntrav(Produto p) {  //Entradas que ainda possuem valores para venda
//        EntityManager em = getEntityManager();
//        try {
//            Query q = em.createQuery("from Entrada v where v.produto = :p and "
//                    + "v.qv>0 and v.dataexpiracao > :d order by v.dataexpiracao asc");
//            Date d = new Date();
//            q.setParameter("p", p);
//            q.setParameter("d", d);
//            q.setHint("eclipselink.refresh", true);
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }

    public List<Entrada> getEntrada(Produto p) {  //Entradas que ainda possuem valores para venda
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.produto = :p and "
                    + "v.qv>0 order by v.dataexpiracao asc");
            q.setParameter("p", p);
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Long getEntrFora() {  //Entradas que ainda possuem valores no stock ou no balcao, fora do prazo
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(v.produto) from Entrada v where "
                    + "(v.qtd-v.qs>0 or v.qv>0) and v.dataexpiracao is not null and v.dataexpiracao < :d");
            Date d = new Date();
            q.setParameter("d", d);
            q.setHint("eclipselink.refresh", true);
            return (Long) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Entrada> getEntExp() {  //Entradas que ainda possuem valores
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where "
                    + "v.qv>0 and v.dataexpiracao < :d");
            Date d = new Date();
            q.setParameter("d", d);
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Entrada getEntData(Date d) {  //Entradas que ainda possuem valores
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.entradaPK.data = :d");
            q.setParameter("d", d);
            q.setHint("eclipselink.refresh", true);
            return (Entrada) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Entrada> getEntDataref(Date d) {  //Entradas que referenciam movimentos das outras entradas
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.dataref is not null and v.dataref = :d order by v.entradaPK.data");
            q.setParameter("d", d);
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Entrada getEntrada(Date d) {  //Entradas que ainda possuem valores
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.entradaPK.data = :d");
            q.setParameter("d", d);
            q.setHint("eclipselink.refresh", true);
            return (Entrada) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Entrada getEntradaCbarra(String cbarra) {  //Entradas que ainda possuem valores
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.cbarra = :c and v.qv > 0 order by v.entradaPK.data desc");
            q.setParameter("c", cbarra);
            q.setHint("eclipselink.refresh", true);
            List<Entrada> le = q.getResultList();
            if (!le.isEmpty()) {
                return (Entrada) le.get(0);
            }
            return null;
        } finally {
            em.close();
        }
    }

    public Entrada getEntradaPCbarra(String cbarra) {  //Entradas que ainda possuem valores
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entrada v where v.cbarra = :c");
            q.setParameter("c", cbarra);
            q.setFirstResult(0);
            q.setHint("eclipselink.refresh", true);
            List<Entrada> le = q.getResultList();
            if (!le.isEmpty()) {
                return (Entrada) le.get(0);
            }
            return null;
        } finally {
            em.close();
        }
    }
}
