/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import modelo.Venda;

/**
 *
 * @author acer
 */
public class Main {

    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        
//        Date currentDate = new Date(calendar.getTime().getTime());
//        EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
//        //new EmprestimoJpaController(emf).create(new Emprestimo(currentDate, currentDate, 2, 234, new Cliente(1), new Produto(5)));
//       // new VendaJpaController(emf).getFacturaPeiodo(null, null);
//         //new VendedorJpaController(emf).create(new Vendedor("Cantonio","ray", "vais", new Contacto(new Consultas().getlastIndex(4))));
//  Date d = new Date();
//        Calendar cal = new GregorianCalendar();
//        cal.setTime(d);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        Date n = cal.getTime();
// 

    }

}
