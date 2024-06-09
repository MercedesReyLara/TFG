package modeloDao;

import java.util.Iterator;
import javax.swing.JTextArea;
import modelosClases.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CategoryDAO {
    
    
    public static void cargaCategorias(Session session, JTextArea ta) throws Exception {
    
        Category c;

        String consulta = "FROM Category c";
        Query q=session.createQuery(consulta);
        ta.append("Categorias\n");

        Iterator it=q.list().iterator();
        while (it.hasNext()){
            c=(Category) it.next();  //En este caso convierto cada elemento de la lista a departamento
            ta.append("Id:"+c.getId()+"\nNombre: "+c.getNombre() + "\nDescripcion: " + c.getDescripcion() 
                    + "\n---------------------\n");
        }
    
    }
    
    public static Category getCategoria(Session session,int num)throws Exception{
        Category c=null;
        Query q = session.createQuery("FROM Category c where c.id=:num");

        q.setParameter("num", num);
        c=(Category) q.uniqueResult();
        return c;
    }
    public static void insertarCategoria(Session session,int num,String nombre,String descripcion){
        Category c=new Category(num,nombre,descripcion);
        session.save(c);
    }
    
    public static void modificarCategoria(Session session,Category c){
        session.update(c);
    }
    
    public static void borrarCategoria(Session session,Category c){
      session.delete(c);
    }
}
