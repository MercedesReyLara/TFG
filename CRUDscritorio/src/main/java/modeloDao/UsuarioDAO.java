
package modeloDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;
import modelosClases.Producto;
import modelosClases.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsuarioDAO {
    
    
    public static void listarUsers(Session session,JTextArea a){
        User u;

        String consulta = "FROM User u";
        Query q=session.createQuery(consulta);
         a.append("Usuarios\n");
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            u=(User) it.next();  //En este caso convierto cada elemento de la lista a departamento
            a.append("DNI: "+u.getDNI()+"\n");
        }
    }
     public static User getUser(Session session,String DNI)throws Exception{
        User u=null;
        Query q = session.createQuery("FROM User u where u.DNI=:DNI");

        q.setParameter("DNI", DNI);
        u=(User) q.uniqueResult();
        return u;
    }
     
     public static void quitarUserProduct(Session session,List<User> users,Producto p){
         for(User u:users){
             u.getProductsU().remove(p);
         }
     }
}
