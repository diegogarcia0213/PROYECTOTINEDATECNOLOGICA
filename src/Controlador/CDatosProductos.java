/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego A. Garcia
 */
public class CDatosProductos {
    public void MostrarUsuarios(JTable TablaProductos){
     
        CConexion objetoconexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql = "";
        
        modelo.addColumn("Codigo_Producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio");
        modelo.addColumn("Existencias");
        
        
        
        TablaProductos.setModel(modelo);
        
        sql = "SELECT * FROM Productos";
        
        String[] datos = new String[4];
        Statement st; 
        
        try{
           st= objetoconexion.establecerConexion().createStatement();
           
           ResultSet rs = st.executeQuery(sql);
           
           while (rs.next()) {
               
               datos[0]= rs.getString(1);
               datos[1]= rs.getString(2);
               datos[2]= rs.getString(3);
               datos[3]= rs.getString(4);
               
               
               
               modelo.addRow(datos);
               
           }
           TablaProductos.setModel(modelo);
        
           
        }catch (Exception e){
            
            JOptionPane.showMessageDialog(null,"Error al mostrar"+ e.toString());  
        }
         finally {
            objetoconexion.cerrarConexion();
        }
    }

public void InsertarProductos (JTextField Codigo_Producto, JTextField Descripcion, JTextField Precio, JTextField Existencias){
            
            CConexion objetoCConexion = new CConexion();
        
            String consulta = "insert into Productos(Codigo_Producto, Descripcion, Precio, Existencias) values (?,?,?,?);";
    
          try{
              
         Connection con = objetoCConexion.establecerConexion();
              Statement stmt = con.createStatement();
            
            // Activar las claves foráneas
            stmt.execute("PRAGMA foreign_keys = ON;");
              PreparedStatement ps = con.prepareStatement(consulta);
             
          
              ps.setString(1, Codigo_Producto.getText());
              ps.setString(2, Descripcion.getText());
              ps.setString(3, Precio.getText());
              ps.setString(4, Existencias.getText());
                           
              
              ps.execute();
           
              JOptionPane.showMessageDialog(null, "se guardo correctamente");
              
                
          } catch (Exception e){
              
              JOptionPane.showMessageDialog(null, "no se guardo correctamente"+ e.toString());
          }
          
          finally{
              objetoCConexion.cerrarConexion();
          }
          
    
          }

public void SelecionarUsuario(JTable TablaProductos, JTextField Codigo_Producto, JTextField Descripcion, JTextField Precio, JTextField Existencias){
     
     try {
         int fila = TablaProductos.getSelectedRow();
         
         if(fila >=0){
             
             Codigo_Producto.setText(TablaProductos.getValueAt(fila, 0).toString());
             Descripcion.setText(TablaProductos.getValueAt(fila, 1).toString());
             Precio.setText(TablaProductos.getValueAt(fila, 2).toString());
             Existencias.setText(TablaProductos.getValueAt(fila, 3).toString());
             
         }
         else{
             JOptionPane.showMessageDialog(null, "no se pudo selecionar");
             
         
         }
         
     } catch (Exception e){
             JOptionPane.showMessageDialog(null, "Error al selecionar"+e.toString());
         
     }
     
 }
 
  public void ModificarUsuario (JTextField Codigo_Producto, JTextField Descripcion, JTextField Precio, JTextField Existencias ){
            
            CConexion objetoCConexion = new CConexion();
        
            String consulta = "UPDATE Productos SET Descripcion=?, Precio=?, Existencias=? WHERE Codigo_Producto=?;";
    
          try{
              
              PreparedStatement ps = objetoCConexion.establecerConexion().prepareStatement(consulta);
             
          
              
              ps.setString(1, Descripcion.getText());
              ps.setString(2, Precio.getText());
              ps.setString(3, Existencias.getText());
              ps.setString(4, Codigo_Producto.getText());              
              
              ps.execute();
           
              JOptionPane.showMessageDialog(null, "se modifico correctamente");
              
                
          } catch (Exception e){
              
              JOptionPane.showMessageDialog(null, "no se modifico correctamente"+ e.toString());
          }
          
          finally{
              objetoCConexion.cerrarConexion();
          }
          
        }
  
   public void EliminarUsuario (JTextField Codigo_Producto){
            
            CConexion objetoCConexion = new CConexion();
        
            String consulta = "DELETE FROM Productos WHERE Codigo_Producto=?;";
    
          try{
              
              PreparedStatement ps = objetoCConexion.establecerConexion().prepareStatement(consulta);
             
              ps.setString(1, Codigo_Producto.getText());              
              
              ps.execute();
           
              JOptionPane.showMessageDialog(null, "se Elimino correctamente");
              
                
          } catch (Exception e){
              
              JOptionPane.showMessageDialog(null, "no se Elmino correctamente"+ e.toString());
          }
          
          finally{
              objetoCConexion.cerrarConexion();
          }
          
        }
   
   public void LimpiarCampos(JTextField Codigo_Producto, JTextField Descripcion, JTextField Precio, JTextField Existencias){
   
       Codigo_Producto.setText(" ");
       Descripcion.setText(" ");
       Precio.setText(" ");
       Existencias.setText(" ");
       
   }
   public void ConsultarProducto(JTable TablaProductos, 
                              JTextField Codigo_Producto, 
                              JTextField Descripcion, 
                              JTextField Precio, 
                              JTextField Existencias) {

    CConexion objetoConexion = new CConexion();
    DefaultTableModel modelo = new DefaultTableModel();

    // Nuevas columnas para la tabla
    modelo.addColumn("Codigo_Producto");
    modelo.addColumn("Descripcion");
    modelo.addColumn("Precio");
    modelo.addColumn("Existencias");

    TablaProductos.setModel(modelo);

    // Construcción dinámica de la consulta SQL
    StringBuilder sql = new StringBuilder("SELECT * FROM Productos WHERE 1=1");
    if (!Codigo_Producto.getText().trim().isEmpty()) {
        sql.append(" AND Codigo_Producto = ?");
    }
    if (!Descripcion.getText().trim().isEmpty()) {
        sql.append(" AND Descripcion LIKE ?");
    }
    if (!Precio.getText().trim().isEmpty()) {
        sql.append(" AND Precio LIKE ?");
    }
    if (!Existencias.getText().trim().isEmpty()) {
        sql.append(" AND Existencias LIKE ?");
    }

    try {
        Connection con = objetoConexion.establecerConexion();
        PreparedStatement ps = con.prepareStatement(sql.toString());

        // Asignación de parámetros dinámicamente
        int index = 1;
        if (!Codigo_Producto.getText().trim().isEmpty()) {
            ps.setString(index++, Codigo_Producto.getText().trim());
        }
        if (!Descripcion.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Descripcion.getText().trim() + "%");
        }
        if (!Precio.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Precio.getText().trim() + "%");
        }
        if (!Existencias.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Existencias.getText().trim() + "%");
        }

        ResultSet rs = ps.executeQuery();
        String[] datos = new String[4];

        boolean found = false;
        while (rs.next()) {
            datos[0] = rs.getString("Codigo_Producto");
            datos[1] = rs.getString("Descripcion");
            datos[2] = rs.getString("Precio");
            datos[3] = rs.getString("Existencias");
            modelo.addRow(datos);
            found = true;
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados");
        }

        TablaProductos.setModel(modelo);

     } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al consultar: " + e.toString());
    } finally {
        objetoConexion.cerrarConexion();
    }
}

   }

