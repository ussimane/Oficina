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
import modelo.Cliente;
import modelo.Modelo;
import modelo.Manutencao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Entradaviatura;
import modelo.Pecamanutencao;
import modelo.Mecanicomanutencao;

/**
 *
 * @author Ussimane
 */
public class EntradaviaturaJpaController implements Serializable {

    public EntradaviaturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entradaviatura entradaviatura) {
        if (entradaviatura.getManutencaoList() == null) {
            entradaviatura.setManutencaoList(new ArrayList<Manutencao>());
        }
        if (entradaviatura.getPecamanutencaoList() == null) {
            entradaviatura.setPecamanutencaoList(new ArrayList<Pecamanutencao>());
        }
        if (entradaviatura.getMecanicomanutencaoList() == null) {
            entradaviatura.setMecanicomanutencaoList(new ArrayList<Mecanicomanutencao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = entradaviatura.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdcliente());
                entradaviatura.setCliente(cliente);
            }
            Modelo modelo = entradaviatura.getModelo();
            if (modelo != null) {
                modelo = em.getReference(modelo.getClass(), modelo.getIdmodelo());
                entradaviatura.setModelo(modelo);
            }
            List<Manutencao> attachedManutencaoList = new ArrayList<Manutencao>();
            for (Manutencao manutencaoListManutencaoToAttach : entradaviatura.getManutencaoList()) {
                manutencaoListManutencaoToAttach = em.getReference(manutencaoListManutencaoToAttach.getClass(), manutencaoListManutencaoToAttach.getManutencaoPK());
                attachedManutencaoList.add(manutencaoListManutencaoToAttach);
            }
            entradaviatura.setManutencaoList(attachedManutencaoList);
            List<Pecamanutencao> attachedPecamanutencaoList = new ArrayList<Pecamanutencao>();
            for (Pecamanutencao pecamanutencaoListPecamanutencaoToAttach : entradaviatura.getPecamanutencaoList()) {
                pecamanutencaoListPecamanutencaoToAttach = em.getReference(pecamanutencaoListPecamanutencaoToAttach.getClass(), pecamanutencaoListPecamanutencaoToAttach.getPecamanutencaoPK());
                attachedPecamanutencaoList.add(pecamanutencaoListPecamanutencaoToAttach);
            }
            entradaviatura.setPecamanutencaoList(attachedPecamanutencaoList);
            List<Mecanicomanutencao> attachedMecanicomanutencaoList = new ArrayList<Mecanicomanutencao>();
            for (Mecanicomanutencao mecanicomanutencaoListMecanicomanutencaoToAttach : entradaviatura.getMecanicomanutencaoList()) {
                mecanicomanutencaoListMecanicomanutencaoToAttach = em.getReference(mecanicomanutencaoListMecanicomanutencaoToAttach.getClass(), mecanicomanutencaoListMecanicomanutencaoToAttach.getMecanicomanutencaoPK());
                attachedMecanicomanutencaoList.add(mecanicomanutencaoListMecanicomanutencaoToAttach);
            }
            entradaviatura.setMecanicomanutencaoList(attachedMecanicomanutencaoList);
            em.persist(entradaviatura);
            if (cliente != null) {
                cliente.getEntradaviaturaList().add(entradaviatura);
                cliente = em.merge(cliente);
            }
            if (modelo != null) {
                modelo.getEntradaviaturaList().add(entradaviatura);
                modelo = em.merge(modelo);
            }
            for (Manutencao manutencaoListManutencao : entradaviatura.getManutencaoList()) {
                Entradaviatura oldEntradaviaturaOfManutencaoListManutencao = manutencaoListManutencao.getEntradaviatura();
                manutencaoListManutencao.setEntradaviatura(entradaviatura);
                manutencaoListManutencao = em.merge(manutencaoListManutencao);
                if (oldEntradaviaturaOfManutencaoListManutencao != null) {
                    oldEntradaviaturaOfManutencaoListManutencao.getManutencaoList().remove(manutencaoListManutencao);
                    oldEntradaviaturaOfManutencaoListManutencao = em.merge(oldEntradaviaturaOfManutencaoListManutencao);
                }
            }
            for (Pecamanutencao pecamanutencaoListPecamanutencao : entradaviatura.getPecamanutencaoList()) {
                Entradaviatura oldEntradaviaturaOfPecamanutencaoListPecamanutencao = pecamanutencaoListPecamanutencao.getEntradaviatura();
                pecamanutencaoListPecamanutencao.setEntradaviatura(entradaviatura);
                pecamanutencaoListPecamanutencao = em.merge(pecamanutencaoListPecamanutencao);
                if (oldEntradaviaturaOfPecamanutencaoListPecamanutencao != null) {
                    oldEntradaviaturaOfPecamanutencaoListPecamanutencao.getPecamanutencaoList().remove(pecamanutencaoListPecamanutencao);
                    oldEntradaviaturaOfPecamanutencaoListPecamanutencao = em.merge(oldEntradaviaturaOfPecamanutencaoListPecamanutencao);
                }
            }
            for (Mecanicomanutencao mecanicomanutencaoListMecanicomanutencao : entradaviatura.getMecanicomanutencaoList()) {
                Entradaviatura oldEntradaviaturaOfMecanicomanutencaoListMecanicomanutencao = mecanicomanutencaoListMecanicomanutencao.getEntradaviatura();
                mecanicomanutencaoListMecanicomanutencao.setEntradaviatura(entradaviatura);
                mecanicomanutencaoListMecanicomanutencao = em.merge(mecanicomanutencaoListMecanicomanutencao);
                if (oldEntradaviaturaOfMecanicomanutencaoListMecanicomanutencao != null) {
                    oldEntradaviaturaOfMecanicomanutencaoListMecanicomanutencao.getMecanicomanutencaoList().remove(mecanicomanutencaoListMecanicomanutencao);
                    oldEntradaviaturaOfMecanicomanutencaoListMecanicomanutencao = em.merge(oldEntradaviaturaOfMecanicomanutencaoListMecanicomanutencao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entradaviatura entradaviatura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradaviatura persistentEntradaviatura = em.find(Entradaviatura.class, entradaviatura.getIdentrada());
            Cliente clienteOld = persistentEntradaviatura.getCliente();
            Cliente clienteNew = entradaviatura.getCliente();
            Modelo modeloOld = persistentEntradaviatura.getModelo();
            Modelo modeloNew = entradaviatura.getModelo();
            List<Manutencao> manutencaoListOld = persistentEntradaviatura.getManutencaoList();
            List<Manutencao> manutencaoListNew = entradaviatura.getManutencaoList();
            List<Pecamanutencao> pecamanutencaoListOld = persistentEntradaviatura.getPecamanutencaoList();
            List<Pecamanutencao> pecamanutencaoListNew = entradaviatura.getPecamanutencaoList();
            List<Mecanicomanutencao> mecanicomanutencaoListOld = persistentEntradaviatura.getMecanicomanutencaoList();
            List<Mecanicomanutencao> mecanicomanutencaoListNew = entradaviatura.getMecanicomanutencaoList();
            List<String> illegalOrphanMessages = null;
            for (Manutencao manutencaoListOldManutencao : manutencaoListOld) {
                if (!manutencaoListNew.contains(manutencaoListOldManutencao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Manutencao " + manutencaoListOldManutencao + " since its entradaviatura field is not nullable.");
                }
            }
            for (Pecamanutencao pecamanutencaoListOldPecamanutencao : pecamanutencaoListOld) {
                if (!pecamanutencaoListNew.contains(pecamanutencaoListOldPecamanutencao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pecamanutencao " + pecamanutencaoListOldPecamanutencao + " since its entradaviatura field is not nullable.");
                }
            }
            for (Mecanicomanutencao mecanicomanutencaoListOldMecanicomanutencao : mecanicomanutencaoListOld) {
                if (!mecanicomanutencaoListNew.contains(mecanicomanutencaoListOldMecanicomanutencao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mecanicomanutencao " + mecanicomanutencaoListOldMecanicomanutencao + " since its entradaviatura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
                entradaviatura.setCliente(clienteNew);
            }
            if (modeloNew != null) {
                modeloNew = em.getReference(modeloNew.getClass(), modeloNew.getIdmodelo());
                entradaviatura.setModelo(modeloNew);
            }
            List<Manutencao> attachedManutencaoListNew = new ArrayList<Manutencao>();
            for (Manutencao manutencaoListNewManutencaoToAttach : manutencaoListNew) {
                manutencaoListNewManutencaoToAttach = em.getReference(manutencaoListNewManutencaoToAttach.getClass(), manutencaoListNewManutencaoToAttach.getManutencaoPK());
                attachedManutencaoListNew.add(manutencaoListNewManutencaoToAttach);
            }
            manutencaoListNew = attachedManutencaoListNew;
            entradaviatura.setManutencaoList(manutencaoListNew);
            List<Pecamanutencao> attachedPecamanutencaoListNew = new ArrayList<Pecamanutencao>();
            for (Pecamanutencao pecamanutencaoListNewPecamanutencaoToAttach : pecamanutencaoListNew) {
                pecamanutencaoListNewPecamanutencaoToAttach = em.getReference(pecamanutencaoListNewPecamanutencaoToAttach.getClass(), pecamanutencaoListNewPecamanutencaoToAttach.getPecamanutencaoPK());
                attachedPecamanutencaoListNew.add(pecamanutencaoListNewPecamanutencaoToAttach);
            }
            pecamanutencaoListNew = attachedPecamanutencaoListNew;
            entradaviatura.setPecamanutencaoList(pecamanutencaoListNew);
            List<Mecanicomanutencao> attachedMecanicomanutencaoListNew = new ArrayList<Mecanicomanutencao>();
            for (Mecanicomanutencao mecanicomanutencaoListNewMecanicomanutencaoToAttach : mecanicomanutencaoListNew) {
                mecanicomanutencaoListNewMecanicomanutencaoToAttach = em.getReference(mecanicomanutencaoListNewMecanicomanutencaoToAttach.getClass(), mecanicomanutencaoListNewMecanicomanutencaoToAttach.getMecanicomanutencaoPK());
                attachedMecanicomanutencaoListNew.add(mecanicomanutencaoListNewMecanicomanutencaoToAttach);
            }
            mecanicomanutencaoListNew = attachedMecanicomanutencaoListNew;
            entradaviatura.setMecanicomanutencaoList(mecanicomanutencaoListNew);
            entradaviatura = em.merge(entradaviatura);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getEntradaviaturaList().remove(entradaviatura);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getEntradaviaturaList().add(entradaviatura);
                clienteNew = em.merge(clienteNew);
            }
            if (modeloOld != null && !modeloOld.equals(modeloNew)) {
                modeloOld.getEntradaviaturaList().remove(entradaviatura);
                modeloOld = em.merge(modeloOld);
            }
            if (modeloNew != null && !modeloNew.equals(modeloOld)) {
                modeloNew.getEntradaviaturaList().add(entradaviatura);
                modeloNew = em.merge(modeloNew);
            }
            for (Manutencao manutencaoListNewManutencao : manutencaoListNew) {
                if (!manutencaoListOld.contains(manutencaoListNewManutencao)) {
                    Entradaviatura oldEntradaviaturaOfManutencaoListNewManutencao = manutencaoListNewManutencao.getEntradaviatura();
                    manutencaoListNewManutencao.setEntradaviatura(entradaviatura);
                    manutencaoListNewManutencao = em.merge(manutencaoListNewManutencao);
                    if (oldEntradaviaturaOfManutencaoListNewManutencao != null && !oldEntradaviaturaOfManutencaoListNewManutencao.equals(entradaviatura)) {
                        oldEntradaviaturaOfManutencaoListNewManutencao.getManutencaoList().remove(manutencaoListNewManutencao);
                        oldEntradaviaturaOfManutencaoListNewManutencao = em.merge(oldEntradaviaturaOfManutencaoListNewManutencao);
                    }
                }
            }
            for (Pecamanutencao pecamanutencaoListNewPecamanutencao : pecamanutencaoListNew) {
                if (!pecamanutencaoListOld.contains(pecamanutencaoListNewPecamanutencao)) {
                    Entradaviatura oldEntradaviaturaOfPecamanutencaoListNewPecamanutencao = pecamanutencaoListNewPecamanutencao.getEntradaviatura();
                    pecamanutencaoListNewPecamanutencao.setEntradaviatura(entradaviatura);
                    pecamanutencaoListNewPecamanutencao = em.merge(pecamanutencaoListNewPecamanutencao);
                    if (oldEntradaviaturaOfPecamanutencaoListNewPecamanutencao != null && !oldEntradaviaturaOfPecamanutencaoListNewPecamanutencao.equals(entradaviatura)) {
                        oldEntradaviaturaOfPecamanutencaoListNewPecamanutencao.getPecamanutencaoList().remove(pecamanutencaoListNewPecamanutencao);
                        oldEntradaviaturaOfPecamanutencaoListNewPecamanutencao = em.merge(oldEntradaviaturaOfPecamanutencaoListNewPecamanutencao);
                    }
                }
            }
            for (Mecanicomanutencao mecanicomanutencaoListNewMecanicomanutencao : mecanicomanutencaoListNew) {
                if (!mecanicomanutencaoListOld.contains(mecanicomanutencaoListNewMecanicomanutencao)) {
                    Entradaviatura oldEntradaviaturaOfMecanicomanutencaoListNewMecanicomanutencao = mecanicomanutencaoListNewMecanicomanutencao.getEntradaviatura();
                    mecanicomanutencaoListNewMecanicomanutencao.setEntradaviatura(entradaviatura);
                    mecanicomanutencaoListNewMecanicomanutencao = em.merge(mecanicomanutencaoListNewMecanicomanutencao);
                    if (oldEntradaviaturaOfMecanicomanutencaoListNewMecanicomanutencao != null && !oldEntradaviaturaOfMecanicomanutencaoListNewMecanicomanutencao.equals(entradaviatura)) {
                        oldEntradaviaturaOfMecanicomanutencaoListNewMecanicomanutencao.getMecanicomanutencaoList().remove(mecanicomanutencaoListNewMecanicomanutencao);
                        oldEntradaviaturaOfMecanicomanutencaoListNewMecanicomanutencao = em.merge(oldEntradaviaturaOfMecanicomanutencaoListNewMecanicomanutencao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = entradaviatura.getIdentrada();
                if (findEntradaviatura(id) == null) {
                    throw new NonexistentEntityException("The entradaviatura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entradaviatura entradaviatura;
            try {
                entradaviatura = em.getReference(Entradaviatura.class, id);
                entradaviatura.getIdentrada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entradaviatura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Manutencao> manutencaoListOrphanCheck = entradaviatura.getManutencaoList();
            for (Manutencao manutencaoListOrphanCheckManutencao : manutencaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entradaviatura (" + entradaviatura + ") cannot be destroyed since the Manutencao " + manutencaoListOrphanCheckManutencao + " in its manutencaoList field has a non-nullable entradaviatura field.");
            }
            List<Pecamanutencao> pecamanutencaoListOrphanCheck = entradaviatura.getPecamanutencaoList();
            for (Pecamanutencao pecamanutencaoListOrphanCheckPecamanutencao : pecamanutencaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entradaviatura (" + entradaviatura + ") cannot be destroyed since the Pecamanutencao " + pecamanutencaoListOrphanCheckPecamanutencao + " in its pecamanutencaoList field has a non-nullable entradaviatura field.");
            }
            List<Mecanicomanutencao> mecanicomanutencaoListOrphanCheck = entradaviatura.getMecanicomanutencaoList();
            for (Mecanicomanutencao mecanicomanutencaoListOrphanCheckMecanicomanutencao : mecanicomanutencaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entradaviatura (" + entradaviatura + ") cannot be destroyed since the Mecanicomanutencao " + mecanicomanutencaoListOrphanCheckMecanicomanutencao + " in its mecanicomanutencaoList field has a non-nullable entradaviatura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente cliente = entradaviatura.getCliente();
            if (cliente != null) {
                cliente.getEntradaviaturaList().remove(entradaviatura);
                cliente = em.merge(cliente);
            }
            Modelo modelo = entradaviatura.getModelo();
            if (modelo != null) {
                modelo.getEntradaviaturaList().remove(entradaviatura);
                modelo = em.merge(modelo);
            }
            em.remove(entradaviatura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entradaviatura> findEntradaviaturaEntities() {
        return findEntradaviaturaEntities(true, -1, -1);
    }

    public List<Entradaviatura> findEntradaviaturaEntities(int maxResults, int firstResult) {
        return findEntradaviaturaEntities(false, maxResults, firstResult);
    }

    private List<Entradaviatura> findEntradaviaturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entradaviatura.class));
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

    public Entradaviatura findEntradaviatura(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entradaviatura.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntradaviaturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entradaviatura> rt = cq.from(Entradaviatura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Entradaviatura> getMatriculaLike(String n){
       String nn = "%" + n.toLowerCase() + "%";
       EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Entradaviatura p where lower(p.matricula) like :n or lower(p.cliente.nome) like :i");
            q.setParameter("n", nn);
            q.setParameter("i", nn);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
     
        public Boolean getExistManutencao(Entradaviatura p) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Pecamanutencao v where v.pecamanutencaoPK.identrada = :p");
            q.setParameter("p", p.getIdentrada());
            return q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }
    
}
