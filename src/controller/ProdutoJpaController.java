/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Familia;
import modelo.Fornecedor;
import modelo.Imposto;
import modelo.Modelo;
import modelo.Produto;

/**
 *
 * @author USSIMANE
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familia idfamilia = produto.getIdfamilia();
            if (idfamilia != null) {
                idfamilia = em.getReference(idfamilia.getClass(), idfamilia.getIdfamilia());
                produto.setIdfamilia(idfamilia);
            }
            Imposto idimposto = produto.getIdimposto();
            if (idimposto != null) {
                idimposto = em.getReference(idimposto.getClass(), idimposto.getIdimposto());
                produto.setIdimposto(idimposto);
            }
            em.persist(produto);
            if (idfamilia != null) {
                idfamilia.getProdutoList().add(produto);
                idfamilia = em.merge(idfamilia);
            }
            if (idimposto != null) {
                idimposto.getProdutoList().add(produto);
                idimposto = em.merge(idimposto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getIdproduto());
            Familia idfamiliaOld = persistentProduto.getIdfamilia();
            Familia idfamiliaNew = produto.getIdfamilia();
            Imposto idimpostoOld = persistentProduto.getIdimposto();
            Imposto idimpostoNew = produto.getIdimposto();
            if (idfamiliaNew != null) {
                idfamiliaNew = em.getReference(idfamiliaNew.getClass(), idfamiliaNew.getIdfamilia());
                produto.setIdfamilia(idfamiliaNew);
            }
            if (idimpostoNew != null) {
                idimpostoNew = em.getReference(idimpostoNew.getClass(), idimpostoNew.getIdimposto());
                produto.setIdimposto(idimpostoNew);
            }
            produto = em.merge(produto);
            if (idfamiliaOld != null && !idfamiliaOld.equals(idfamiliaNew)) {
                idfamiliaOld.getProdutoList().remove(produto);
                idfamiliaOld = em.merge(idfamiliaOld);
            }
            if (idfamiliaNew != null && !idfamiliaNew.equals(idfamiliaOld)) {
                idfamiliaNew.getProdutoList().add(produto);
                idfamiliaNew = em.merge(idfamiliaNew);
            }
            if (idimpostoOld != null && !idimpostoOld.equals(idimpostoNew)) {
                idimpostoOld.getProdutoList().remove(produto);
                idimpostoOld = em.merge(idimpostoOld);
            }
            if (idimpostoNew != null && !idimpostoNew.equals(idimpostoOld)) {
                idimpostoNew.getProdutoList().add(produto);
                idimpostoNew = em.merge(idimpostoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getIdproduto();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto persistentProduto,Produto produto, EntityManager em) throws NonexistentEntityException, Exception {
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//        Produto persistentProduto = em.find(Produto.class, produto.getIdproduto());
//        em.refresh(persistentProduto, LockModeType.PESSIMISTIC_WRITE);
        Familia idfamiliaOld = persistentProduto.getIdfamilia();
        Familia idfamiliaNew = produto.getIdfamilia();
        Imposto idimpostoOld = persistentProduto.getIdimposto();
        Imposto idimpostoNew = produto.getIdimposto();
        if (idfamiliaNew != null) {
            idfamiliaNew = em.getReference(idfamiliaNew.getClass(), idfamiliaNew.getIdfamilia());
            produto.setIdfamilia(idfamiliaNew);
        }
        if (idimpostoNew != null) {
            idimpostoNew = em.getReference(idimpostoNew.getClass(), idimpostoNew.getIdimposto());
            produto.setIdimposto(idimpostoNew);
        }
        produto = em.merge(produto);
        if (idfamiliaOld != null && !idfamiliaOld.equals(idfamiliaNew)) {
            idfamiliaOld.getProdutoList().remove(produto);
            idfamiliaOld = em.merge(idfamiliaOld);
        }
        if (idfamiliaNew != null && !idfamiliaNew.equals(idfamiliaOld)) {
            idfamiliaNew.getProdutoList().add(produto);
            idfamiliaNew = em.merge(idfamiliaNew);
        }
        if (idimpostoOld != null && !idimpostoOld.equals(idimpostoNew)) {
            idimpostoOld.getProdutoList().remove(produto);
            idimpostoOld = em.merge(idimpostoOld);
        }
        if (idimpostoNew != null && !idimpostoNew.equals(idimpostoOld)) {
            idimpostoNew.getProdutoList().add(produto);
            idimpostoNew = em.merge(idimpostoNew);
        }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = produto.getIdproduto();
//                if (findProduto(id) == null) {
//                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }
    
    public void editVenda(Produto produto, EntityManager em) throws NonexistentEntityException, Exception {
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
        int qt = produto.getQtdvenda();
        Produto persistentProduto = em.find(Produto.class, produto.getIdproduto());
        em.refresh(persistentProduto, LockModeType.PESSIMISTIC_WRITE);
        persistentProduto.setQtdvenda(persistentProduto.getQtdvenda()-qt);
//        Familia idfamiliaOld = persistentProduto.getIdfamilia();
//        Familia idfamiliaNew = produto.getIdfamilia();
//        Imposto idimpostoOld = persistentProduto.getIdimposto();
//        Imposto idimpostoNew = produto.getIdimposto();
//        if (idfamiliaNew != null) {
//            idfamiliaNew = em.getReference(idfamiliaNew.getClass(), idfamiliaNew.getIdfamilia());
//            produto.setIdfamilia(idfamiliaNew);
//        }
//        if (idimpostoNew != null) {
//            idimpostoNew = em.getReference(idimpostoNew.getClass(), idimpostoNew.getIdimposto());
//            produto.setIdimposto(idimpostoNew);
//        }
        produto = em.merge(persistentProduto);
//        if (idfamiliaOld != null && !idfamiliaOld.equals(idfamiliaNew)) {
//            idfamiliaOld.getProdutoList().remove(produto);
//            idfamiliaOld = em.merge(idfamiliaOld);
//        }
//        if (idfamiliaNew != null && !idfamiliaNew.equals(idfamiliaOld)) {
//            idfamiliaNew.getProdutoList().add(produto);
//            idfamiliaNew = em.merge(idfamiliaNew);
//        }
//        if (idimpostoOld != null && !idimpostoOld.equals(idimpostoNew)) {
//            idimpostoOld.getProdutoList().remove(produto);
//            idimpostoOld = em.merge(idimpostoOld);
//        }
//        if (idimpostoNew != null && !idimpostoNew.equals(idimpostoOld)) {
//            idimpostoNew.getProdutoList().add(produto);
//            idimpostoNew = em.merge(idimpostoNew);
//        }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = produto.getIdproduto();
//                if (findProduto(id) == null) {
//                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getIdproduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            Familia idfamilia = produto.getIdfamilia();
            if (idfamilia != null) {
                idfamilia.getProdutoList().remove(produto);
                idfamilia = em.merge(idfamilia);
            }
            Imposto idimposto = produto.getIdimposto();
            if (idimposto != null) {
                idimposto.getProdutoList().remove(produto);
                idimposto = em.merge(idimposto);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
           // Produto =
            // return em.getReference(Produto.class, id);
            Produto p = em.find(Produto.class, id);
            //em.clear();
            em.refresh(p);
//            Query q = em.createQuery("from Produto p where p.idproduto = :i");
//            q.setParameter("i", id);
            return p;
        } finally {
            em.close();
        }
    }

    public Produto findProdutoS(Integer id, EntityManager em) {
//        EntityManager em = getEntityManager();
//        try {
        // Produto =
        // return em.getReference(Produto.class, id);
        Produto p = em.find(Produto.class, id);
        //em.clear();
        em.refresh(p, LockModeType.PESSIMISTIC_WRITE);
//            Query q = em.createQuery("from Produto p where p.idproduto = :i");
//            q.setParameter("i", id);
        return p;
//        } finally {
//            em.close();
//        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Produto> getProdNomeLike(String n) {
        String nn = "%" + n.toLowerCase() + "%";
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Produto p where p.codigo = :i or lower(p.nome) like :n "
                    + "order by p.nome asc");
            q.setParameter("n", nn);
            q.setParameter("i", n);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Produto getProdCod(String n) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Produto p where p.codigo = :i");
            q.setParameter("i", n);
            return (Produto) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int existeCodigo(String c) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Produto p where p.codigo = :i");
            q.setParameter("i", c);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int existeImposto(Imposto i) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Produto p where p.idimposto = :i");
            q.setParameter("i", i);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int existeFamilia(Familia i) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Produto p where p.idfamilia = :i");
            q.setParameter("i", i);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public int existeModelo(Modelo i) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Produto p where p.idmodelo = :i");
            q.setParameter("i", i);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Produto> getProdAsc() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Produto p order by p.nome asc");
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
