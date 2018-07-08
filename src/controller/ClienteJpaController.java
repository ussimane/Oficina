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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Empresa;

/**
 *
 * @author USSIMANE
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa idempresa = cliente.getIdempresa();
            if (idempresa != null) {
                idempresa = em.getReference(idempresa.getClass(), idempresa.getIdempresa());
                cliente.setIdempresa(idempresa);
            }
            em.persist(cliente);
            if (idempresa != null) {
                idempresa.getClienteList().add(cliente);
                idempresa = em.merge(idempresa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            Empresa idempresaOld = persistentCliente.getIdempresa();
            Empresa idempresaNew = cliente.getIdempresa();
            if (idempresaNew != null) {
                idempresaNew = em.getReference(idempresaNew.getClass(), idempresaNew.getIdempresa());
                cliente.setIdempresa(idempresaNew);
            }
            cliente = em.merge(cliente);
            if (idempresaOld != null && !idempresaOld.equals(idempresaNew)) {
                idempresaOld.getClienteList().remove(cliente);
                idempresaOld = em.merge(idempresaOld);
            }
            if (idempresaNew != null && !idempresaNew.equals(idempresaOld)) {
                idempresaNew.getClienteList().add(cliente);
                idempresaNew = em.merge(idempresaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Empresa idempresa = cliente.getIdempresa();
            if (idempresa != null) {
                idempresa.getClienteList().remove(cliente);
                idempresa = em.merge(idempresa);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Cliente> getClienteLike(String n){
       String nn = "%" + n.toLowerCase() + "%";
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente p where p.codigo = :i or lower(p.nome) like :n");
            q.setParameter("n", nn);
            q.setParameter("i", n);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Cliente getClienteLikeN(String n){
       String nn =  n.toLowerCase() + "%";
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente p where lower(p.nome) like :n or p.codigo = :i");
            q.setParameter("n", nn);
            q.setParameter("i", n);
            return (Cliente) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public int existeCodigo(String c, Empresa e){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(p) from Cliente p where p.codigo = :i "
                    + "and p.idempresa = :e");
            q.setParameter("i", c);
            q.setParameter("e", e);
            return ((Long)q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Boolean getExistE(Empresa p) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente v where v.idempresa = :p");
            q.setParameter("p", p);
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }
    
    public Cliente getClienteCod(String e,String n){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente p where p.codigo = :n and p.idempresa.codigo = :e");
            q.setParameter("e", e);
            q.setParameter("n", n);
            return (Cliente) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Cliente getCliCod(String n){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente p where p.codigo = :n"); 
            q.setParameter("n", n);
            return (Cliente) q.getResultList().get(0);
        } finally {
            em.close();
        }
    }
    
    public List<Cliente> getClienteEmp(String e){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente p where p.idempresa.codigo = :e and p.idempresa.estado = true and p.estado = true"
                    + " order by p.nome asc");
            q.setParameter("e", e);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Cliente> getClientAsc(){
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente p where p.idempresa.estado = true and  p.estado = true order by p.nome asc");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
   
}
