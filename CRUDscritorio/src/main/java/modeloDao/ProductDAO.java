package modeloDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;
import modelosClases.Category;
import modelosClases.Producto;
import modelosClases.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ProductDAO {
    
    
    public static void cargaProductos(Session session, JTextArea ta) throws Exception {
    
        Producto p;

        String consulta="FROM Producto p";
        Query q=session.createQuery(consulta);
        ta.append("Productos\n");  
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            p=(Producto) it.next();  //En este caso convierto cada elemento de la lista a departamento
            ta.append("Id:"+p.getId()+"\nNombre: "+p.getNombreP() + "\nPrecio: " + p.getPrecio()+ "\nCategoria: " 
                    +p.getCategory().getNombre()+"\nDescripcion: "+p.getDescripcionP()+"\n---------------------\n");
        }
    
    }
    
    public static List<Producto> getProductos(Session session) throws Exception {
        Producto p;
        String consulta="FROM Producto p";
        Query q=session.createQuery(consulta);
        return q.list();
    }
    public static Producto getProducto(Session session,int id)throws Exception{
        Producto p=null;
        Query q = session.createQuery("FROM Producto p where p.id=:id");

        q.setParameter("id", id);
        p=(Producto) q.uniqueResult();
        return p;
    }
    public static void insertarProducto(Session session,Producto p){
        session.save(p);
    }
    
    public static void modificarProducto(Session session,Producto p){
        session.update(p);
    }
    
    public static void borrarProducto(Session session,Producto p){
      session.delete(p);
    }
    
    public static void borrarProductos(Session session,List<Producto> productos){
       for(Producto p:productos){
           session.delete(p);
       } 
    }
    
    public static void insertarPaU(Session session,Producto p,User u){
       p.getUsers().add(u);
       u.getProductsU().add(p);
       
       session.update(u);
       session.update(p);
    }
    
    public static void quitarProductUser(Session session,List<Producto> productos){
        for(Producto p:productos){
            p.getUsers().clear();
        }
    }
    
    public static void quitarProductUser(Session session,Producto p){
            p.getUsers().clear();
    }
}
