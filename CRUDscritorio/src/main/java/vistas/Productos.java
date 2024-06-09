package vistas;

import controlador.controladorProductos;
import java.time.LocalDate;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Productos extends javax.swing.JFrame {

    public Productos() {
        initComponents();
        controladorProductos.iniciaSession();
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(214, 234, 248));
        this.setResizable(false); 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        goBack = new javax.swing.JButton();
        insertar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        listar = new javax.swing.JButton();
        insertarUser = new javax.swing.JButton();
        nombre = new javax.swing.JLabel();
        nombreV = new javax.swing.JTextField();
        precio = new javax.swing.JLabel();
        precioV = new javax.swing.JTextField();
        categoria = new javax.swing.JLabel();
        categoriaV = new javax.swing.JTextField();
        descripcion = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        idV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        idU = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listarP = new javax.swing.JTextArea();
        listU = new javax.swing.JButton();
        listCategorias = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        descripcionV = new javax.swing.JTextArea();
        p_UList = new javax.swing.JButton();
        informeUsuarios = new javax.swing.JButton();
        informeResenas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        jLabel1.setText("GESTION DE PRODUCTOS");

        goBack.setText("VOLVER");
        goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackActionPerformed(evt);
            }
        });

        insertar.setText("INSERTAR");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        eliminar.setText("ELIMINAR");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        modificar.setText("MODIFICAR");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        listar.setText("LISTAR");
        listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarActionPerformed(evt);
            }
        });

        insertarUser.setText("INSERTAR PRODUCTO A USUARIO");
        insertarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarUserActionPerformed(evt);
            }
        });

        nombre.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        nombre.setText("Nombre");

        precio.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        precio.setText("Precio");

        categoria.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        categoria.setText("Categoria");

        descripcion.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        descripcion.setText("Descripcion");

        id.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        id.setText("Introduce el ID del producto a eliminar/modificar");

        idV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idVActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        jLabel2.setText("Introduce ID de usuario");

        listarP.setColumns(20);
        listarP.setRows(5);
        jScrollPane1.setViewportView(listarP);

        listU.setText("LISTAR USUARIOS");
        listU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listUActionPerformed(evt);
            }
        });

        listCategorias.setText("LISTAR CATEGORIAS");
        listCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listCategoriasActionPerformed(evt);
            }
        });

        descripcionV.setColumns(20);
        descripcionV.setRows(5);
        jScrollPane2.setViewportView(descripcionV);

        p_UList.setText("LISTAR PRODUCTOS DE CADA USER");
        p_UList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_UListActionPerformed(evt);
            }
        });

        informeUsuarios.setText("INFORME USUARIOS");
        informeUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeUsuariosActionPerformed(evt);
            }
        });

        informeResenas.setText("INFORME RESEÑAS CATEGORIA CASA");
        informeResenas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeResenasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(insertarUser)
                                    .addComponent(eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(informeResenas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(informeUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(goBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(idV)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreV)
                    .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(precioV)
                    .addComponent(categoriaV)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listU))
                    .addComponent(jScrollPane2)
                    .addComponent(idU)
                    .addComponent(p_UList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertar)
                    .addComponent(jLabel1)
                    .addComponent(informeUsuarios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombre)
                        .addGap(0, 0, 0)
                        .addComponent(nombreV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precio)
                        .addGap(0, 0, 0)
                        .addComponent(precioV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(listCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoria))
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eliminar)
                            .addComponent(informeResenas))
                        .addGap(18, 18, 18)
                        .addComponent(modificar)
                        .addGap(18, 18, 18)
                        .addComponent(listar)
                        .addGap(18, 18, 18)
                        .addComponent(insertarUser)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(categoriaV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(descripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(listU))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goBack)
                    .addComponent(p_UList))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idVActionPerformed
        
    }//GEN-LAST:event_idVActionPerformed

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarActionPerformed
      controladorProductos.insertarProducto();
    }//GEN-LAST:event_insertarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
      controladorProductos.modificarProducto();
    }//GEN-LAST:event_modificarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
       controladorProductos.eliminarProducto();
    }//GEN-LAST:event_eliminarActionPerformed

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
      controladorProductos.listarProductos();
    }//GEN-LAST:event_listarActionPerformed

    private void insertarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarUserActionPerformed
      controladorProductos.insertarProductoAUser();
    }//GEN-LAST:event_insertarUserActionPerformed

    private void goBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackActionPerformed
        this.setVisible(false);
        Inicio inicio=new Inicio();
        inicio.setVisible(true);
    }//GEN-LAST:event_goBackActionPerformed

    private void listUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listUActionPerformed
        controladorProductos.listarUsuarios();
    }//GEN-LAST:event_listUActionPerformed

    private void listCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCategoriasActionPerformed
        controladorProductos.listarCategorias();
    }//GEN-LAST:event_listCategoriasActionPerformed

    private void p_UListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_UListActionPerformed
        controladorProductos.listarProductos_User();
    }//GEN-LAST:event_p_UListActionPerformed

    private void informeUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeUsuariosActionPerformed
        controladorProductos.generarPDF("C:\\Users\\manca\\Desktop\\CRUDscritorio\\informes\\Usuarios.jasper",
                "C:\\Users\\manca\\Desktop\\CRUDscritorio\\informes\\Usuarios "+LocalDate.now()+".pdf");
    }//GEN-LAST:event_informeUsuariosActionPerformed

    private void informeResenasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeResenasActionPerformed
        controladorProductos.generarPDF("C:\\Users\\manca\\Desktop\\CRUDscritorio\\informes\\ResenasCategoria.jasper",
                "C:\\Users\\manca\\Desktop\\CRUDscritorio\\informes\\ResenasCategoria "+LocalDate.now()+".pdf");
    }//GEN-LAST:event_informeResenasActionPerformed

    public JTextField getCategoriaV() {
        return categoriaV;
    }

    public void setCategoriaV(JTextField categoriaV) {
        this.categoriaV = categoriaV;
    }

    public JTextArea getDescripcionV() {
        return descripcionV;
    }

    public void setDescripcionV(JTextArea descripcionV) {
        this.descripcionV = descripcionV;
    }

    public JTextField getIdU() {
        return idU;
    }

    public void setIdU(JTextField idU) {
        this.idU = idU;
    }

    public JTextField getIdV() {
        return idV;
    }

    public void setIdV(JTextField idV) {
        this.idV = idV;
    }

    public JTextArea getListarP() {
        return listarP;
    }

    public void setListarP(JTextArea listarP) {
        this.listarP = listarP;
    }

    public JTextField getNombreV() {
        return nombreV;
    }

    public void setNombreV(JTextField nombreV) {
        this.nombreV = nombreV;
    }

    public JTextField getPrecioV() {
        return precioV;
    }

    public void setPrecioV(JTextField precioV) {
        this.precioV = precioV;
    }

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel categoria;
    private javax.swing.JTextField categoriaV;
    private javax.swing.JLabel descripcion;
    private javax.swing.JTextArea descripcionV;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton goBack;
    private javax.swing.JLabel id;
    private javax.swing.JTextField idU;
    private javax.swing.JTextField idV;
    private javax.swing.JButton informeResenas;
    private javax.swing.JButton informeUsuarios;
    private javax.swing.JButton insertar;
    private javax.swing.JButton insertarUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listCategorias;
    private javax.swing.JButton listU;
    private javax.swing.JButton listar;
    private javax.swing.JTextArea listarP;
    private javax.swing.JButton modificar;
    private javax.swing.JLabel nombre;
    private javax.swing.JTextField nombreV;
    private javax.swing.JButton p_UList;
    private javax.swing.JLabel precio;
    private javax.swing.JTextField precioV;
    // End of variables declaration//GEN-END:variables
}
