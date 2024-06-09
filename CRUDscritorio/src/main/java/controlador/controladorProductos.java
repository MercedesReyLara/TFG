package controlador;

import controladorHibernate.Hibernate;
import java.io.File;
import java.util.List;
import modeloDao.CategoryDAO;
import modeloDao.ProductDAO;
import modeloDao.UsuarioDAO;
import modelosClases.Category;
import modelosClases.Producto;
import modelosClases.User;
import org.hibernate.Session;
import vistas.Productos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class controladorProductos {
 
    public static Productos Vproductos=new Productos();
    public static CategoryDAO cDAO;
    public static UsuarioDAO uDAO;
    public static ProductDAO pDAO;
    public static Session session;
    
    public static void iniciar() {
       Vproductos.setVisible(true);
       Vproductos.setLocationRelativeTo(null);
    }
    public static void iniciaSession() {
        session=Hibernate.getSessionFactory().openSession();
        pDAO=Hibernate.getProductoDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }
    
    public static void listarProductos(){
        Vproductos.getListarP().setText("");
        try{
          session.beginTransaction();
           ProductDAO.cargaProductos(session, Vproductos.getListarP());
          session.getTransaction().commit();
       } catch (Exception ex) {
           Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void listarProductos_User(){
        Vproductos.getListarP().setText("");
        try{
          session.beginTransaction();
           List<Producto> productos=ProductDAO.getProductos(session);
           if(!(productos.isEmpty())){
           for(Producto p:productos){
             for(User u:p.getUsers()){
                 Vproductos.getListarP().append("Usuario/Producto: "+u.getDNI()+"/"+p.getNombreP()+"\n---------------------\n");
             }
           }
           }
          session.getTransaction().commit();
       } catch (Exception ex) {
           Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public static void listarCategorias(){
        Vproductos.getListarP().setText("");
        try{
          session.beginTransaction();
           CategoryDAO.cargaCategorias(session, Vproductos.getListarP());
          session.getTransaction().commit();
       } catch (Exception ex) {
           Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public static void insertarProducto(){
       if(Vproductos.getNombreV().getText().isEmpty()||
               Vproductos.getCategoriaV().getText().isEmpty()||Vproductos.getPrecioV().getText().isEmpty()
               ||Vproductos.getDescripcionV().getText().isEmpty()) {
           JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
           return;
       }else if(!isInt(Vproductos.getIdV().getText())){
           JOptionPane.showMessageDialog(null, "El id tiene que ser entero");
          return;
       }else if(!isDouble(Vproductos.getPrecioV().getText())){
           JOptionPane.showMessageDialog(null, "El precio tiene que ser un número");
          return;
       }
       int id=Integer.parseInt(Vproductos.getIdV().getText());
       String nombre=Vproductos.getNombreV().getText();
       String descripcion=Vproductos.getDescripcionV().getText();
       int id_categoria=Integer.parseInt(Vproductos.getCategoriaV().getText());
       double precio=Double.parseDouble(Vproductos.getPrecioV().getText());
       try{
           session.beginTransaction();
           Category c=CategoryDAO.getCategoria(session, id_categoria);
           if(c==null){
               JOptionPane.showMessageDialog(null, "La categoría no existe");
                return;
           }
           Producto p=new Producto(id,nombre,descripcion,precio,c);
           ProductDAO.insertarProducto(session,p);
           session.getTransaction().commit();
           JOptionPane.showMessageDialog(null, "Producto insertado con éxito");
           limpiarCampos();
       }catch(Exception e){
           Hibernate.rollbackTx(session);
           JOptionPane.showMessageDialog(null, "Error en la inserción del producto"+e.toString());
       }
    }
    
    
    public static void insertarProductoAUser(){
      if(Vproductos.getIdV().getText().isEmpty()||Vproductos.getIdU().getText().isEmpty()) {
           JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
           return;
       }else if(!isInt(Vproductos.getIdV().getText())){
          JOptionPane.showMessageDialog(null, "El id tiene que ser un número entero");
          return;
       }else if(!isDNI(Vproductos.getIdU().getText())){
           JOptionPane.showMessageDialog(null, "El DNI tiene que ser valido");
          return;
       }
       
       int id=Integer.parseInt(Vproductos.getIdV().getText());
       String DNI=Vproductos.getIdU().getText();
       
       try{
           session.beginTransaction();
           User u=UsuarioDAO.getUser(session, DNI);
           if(u==null){
               JOptionPane.showMessageDialog(null, "El usuario no existe");
                return;
           }
           
           Producto p=ProductDAO.getProducto(session, id);
           if(p==null){
               JOptionPane.showMessageDialog(null, "El usuario no existe");
                return;
           }
           ProductDAO.insertarPaU(session, p, u);
           session.getTransaction().commit();
           JOptionPane.showMessageDialog(null, "Producto insertado al usuario con éxito");
           limpiarCampos();
       }catch(Exception e){
           Hibernate.rollbackTx(session);
           JOptionPane.showMessageDialog(null, "Error en la inserción del producto");
       }  
    }
    
    public static void modificarProducto(){
       if(Vproductos.getIdV().getText().isEmpty()||Vproductos.getNombreV().getText().isEmpty()||
               Vproductos.getCategoriaV().getText().isEmpty()||Vproductos.getPrecioV().getText().isEmpty()
               ||Vproductos.getDescripcionV().getText().isEmpty()) {
           JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
           return;
       }else if(!isInt(Vproductos.getIdV().getText())){
          JOptionPane.showMessageDialog(null, "El id tiene que ser un número entero");
          return;
       }else if(!isDouble(Vproductos.getPrecioV().getText())){
           JOptionPane.showMessageDialog(null, "El precio tiene que ser un número");
          return;
       }
       
       int id=Integer.parseInt(Vproductos.getIdV().getText());
       String nombre=Vproductos.getNombreV().getText();
       String descripcion=Vproductos.getDescripcionV().getText();
       int id_categoria=Integer.parseInt(Vproductos.getCategoriaV().getText());
       double precio=Double.parseDouble(Vproductos.getPrecioV().getText());
       try{
           session.beginTransaction();
           Producto p=ProductDAO.getProducto(session, id);
           if(p==null){
             JOptionPane.showMessageDialog(null, "El producto no existe");
          return;  
           }
           Category c=CategoryDAO.getCategoria(session, id_categoria);
           if(c==null){
               JOptionPane.showMessageDialog(null, "La categoría no existe");
                return;
           }
           p.setNombreP(nombre);
           p.setDescripcionP(descripcion);
           p.setPrecio(precio);
           p.setCategory(c);
           ProductDAO.modificarProducto(session, p);
           session.getTransaction().commit();
           JOptionPane.showMessageDialog(null, "Producto modificado con éxito");
           limpiarCampos();
       }catch(Exception e){
           Hibernate.rollbackTx(session);
           JOptionPane.showMessageDialog(null, "Error en la inserción del producto"+e.getMessage());
       }
    }
     
    public static void eliminarProducto(){
        if(Vproductos.getIdV().getText().isEmpty()){
           JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
           return;
        }else if(!isInt(Vproductos.getIdV().getText())){
           JOptionPane.showMessageDialog(null, "El id tiene que ser entero");
           return;
        }
        int id=Integer.parseInt(Vproductos.getIdV().getText());
        try{
            session.beginTransaction();
            Producto p=ProductDAO.getProducto(session, id);
            if(p!=null){
                if(p.getUsers().size()!=0){
                UsuarioDAO.quitarUserProduct(session, p.getUsers(), p);
                }
                ProductDAO.quitarProductUser(session, p);
            }
            ProductDAO.borrarProducto(session, p);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Producto borrado"); 
            limpiarCampos();
        }catch(Exception e){
          Hibernate.rollbackTx(session);
           JOptionPane.showMessageDialog(null, "Error en la eliminación del producto"+e.getMessage());  
        }
    }
    public static void listarUsuarios(){
        Vproductos.getListarP().setText("");
     try{
          session.beginTransaction();
           UsuarioDAO.listarUsers(session, Vproductos.getListarP());
          session.getTransaction().commit();
       } catch (Exception ex) {
           Hibernate.rollbackTx(session);
            Logger.getLogger(controladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    public static Boolean isInt(String id){
        try{
          Integer.valueOf(id);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public static Boolean isDouble(String precio){
        try{
          Double.valueOf(precio);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public static Boolean isDNI(String dni){
        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        
        // Extrae los dígitos y la letra del DNI
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letra = dni.charAt(8);

        // Verifica si la letra es correcta
        return letras[numero % 23] == letra;
    }
    public static void limpiarCampos(){
        Vproductos.getIdV().setText("");
        Vproductos.getNombreV().setText("");
        Vproductos.getCategoriaV().setText("");
        Vproductos.getDescripcionV().setText("");
        Vproductos.getPrecioV().setText("");
    }
    

    public static void generarPDF(String ruta_reporte, String ruta_destino) {
        String baseDatos = "jdbc:mysql://localhost:3306/restcrud?serverTimezone=UTC";
        String usuario = "root";
        String clave = "1234";
        
        File archivoDestino = new File(ruta_destino);
    if (archivoDestino.exists()) {
        JOptionPane.showMessageDialog(null, "El archivo PDF ya existe. Actualizando...");
        archivoDestino.delete(); 
    }
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, clave);
            Map parametros = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(ruta_reporte, parametros, conexion);
            JasperExportManager.exportReportToPdfFile(print, ruta_destino);
            JOptionPane.showMessageDialog(null, "¡Informe generado!");

        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(controladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(controladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(controladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controladorProductos.class.getName()).log(Level.SEVERE, null, ex.toString());
        } catch (InstantiationException ex) {
            Logger.getLogger(controladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

