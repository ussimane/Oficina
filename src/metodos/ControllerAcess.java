/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author acer
 */
public class ControllerAcess {

    @PersistenceUnit(unitName = "FarmaciaPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() throws java.lang.IllegalStateException {
        return (getEntityManagerFactory().createEntityManager());
    }

    public EntityManagerFactory getEntityManagerFactory() {
        try {
            emf = Persistence.createEntityManagerFactory("FarmaciaPU");
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        return (emf);
    }

//    public Connection conetion() throws Exception {
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//        return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/vendas", "root", "");
//    
//    }
}
