package controlador;

import controladorHibernate.Hibernate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modeloDao.CategoryDAO;
import modeloDao.ProductDAO;
import modeloDao.UsuarioDAO;
import modelosClases.Category;
import modelosClases.Producto;
import org.hibernate.Session;
import vistas.*;

public class controladorCategorias {

  
    public static Categorias Vcategorias=new Categorias();
    public static CategoryDAO cDAO;
    public static UsuarioDAO uDAO;
    public static ProductDAO pDAO;
    public static Session session;
    
    public static void iniciar() {
       Vcategorias.setVisible(true);
       Vcategorias.setLocationRelativeTo(null);
    }
    public static void iniciaSession() {
        session=Hibernate.getSessionFactory().openSession();
        cDAO=Hibernate.getCategoryDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }
    public static void listarCategorias(){
        Vcategorias.getListaC().setText("");
       try{
          session.beginTransaction();
          CategoryDAO.cargaCategorias(session, Vcategorias.getListaC());
          session.getTransaction().commit();
       } catch (Exception ex) {
           Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void insertarCategoria(){
        if(Vcategorias.getIdC().getText().isEmpty()||Vcategorias.getNombreC().getText().isEmpty()
                ||Vcategorias.getDescripcionC().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
            return;
        }else if(!(isInt(Vcategorias.getIdC().getText()))){
           JOptionPane.showMessageDialog(null, "El id tiene que ser un número entero");
            return;  
        }
        int id=Integer.parseInt(Vcategorias.getIdC().getText());
        String nombre=Vcategorias.getNombreC().getText();
        String descripcion=Vcategorias.getDescripcionC().getText();
       try{
          session.beginTransaction();
          CategoryDAO.insertarCategoria(session, id, nombre, descripcion);
          session.getTransaction().commit();
          limpiarCampos();
          JOptionPane.showMessageDialog(null, "Categoria insertada con exito");
       } catch (Exception ex) {
          Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static void modificarCategoria(){
     if(Vcategorias.getIdC().getText().isEmpty()||Vcategorias.getNombreC().getText().isEmpty()
                ||Vcategorias.getDescripcionC().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
            return;
        }else if(!isInt(Vcategorias.getIdC().getText())){
           JOptionPane.showMessageDialog(null, "El id tiene que ser un número entero");
            return;  
        }
        int id=Integer.parseInt(Vcategorias.getIdC().getText());
        String nombre=Vcategorias.getNombreC().getText();
        String descripcion=Vcategorias.getDescripcionC().getText();
       try{
          session.beginTransaction();
          Category c=CategoryDAO.getCategoria(session, id);
          if(c==null){
            JOptionPane.showMessageDialog(null, "Esta categoría no existe");
            return;   
          }
          c.setNombre(nombre);
          c.setDescripcion(descripcion);
          CategoryDAO.modificarCategoria(session, c);
          session.getTransaction().commit();
          limpiarCampos();
          JOptionPane.showMessageDialog(null, "Categoria modificada con éxito");
       } catch (Exception ex) {
           Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    public static boolean isInt(String num){
        try{
          Integer.valueOf(num);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public static void borrarCategoria(){
        if(Vcategorias.getIdC().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
            return;
        }else if(!isInt(Vcategorias.getIdC().getText())){
           JOptionPane.showMessageDialog(null, "El id tiene que ser un número entero");
            return;  
        }
        
        int id=Integer.parseInt(Vcategorias.getIdC().getText());
        try{
           session.beginTransaction();
           Category c=CategoryDAO.getCategoria(session, id);
           if(c!=null){
               if(c.getProducts().size()!=0){
              for(Producto p:c.getProducts()){
                  if(p.getUsers().size()!=0){
                  UsuarioDAO.quitarUserProduct(session, p.getUsers(), p);
                  }
                  ProductDAO.quitarProductUser(session, c.getProducts()); 
                 ProductDAO.borrarProductos(session, c.getProducts());
              }   
               }
        }else{ 
           JOptionPane.showMessageDialog(null, "Esa categoria no existe");
            Hibernate.rollbackTx(session); 
            return;   
           }
           CategoryDAO.borrarCategoria(session, c);
           Vcategorias.getIdC().setText("");
            session.getTransaction().commit();
            limpiarCampos();
            JOptionPane.showMessageDialog(null, "Categoria eliminada con éxito");
        }catch(Exception ex){
          Hibernate.rollbackTx(session);  
           Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void limpiarCampos(){
        Vcategorias.getIdC().setText("");
        Vcategorias.getNombreC().setText("");
        Vcategorias.getDescripcionC().setText("");
    }
}
