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
public class CDatosProveedores {
        public void MostrarUsuarios(JTable TablaProveedor){
     
        CConexion objetoconexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql = "";
        
        modelo.addColumn("Codigo_Proveedor");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Direccion");
        modelo.addColumn("provincia");
        modelo.addColumn("Telefono");
        
        
        
        TablaProveedor.setModel(modelo);
        
        sql = "SELECT * FROM Proveedores";
        
        String[] datos = new String[6];
        Statement st; 
        
        try{
           st= objetoconexion.establecerConexion().createStatement();
           
           ResultSet rs = st.executeQuery(sql);
           
           while (rs.next()) {
               
               datos[0]= rs.getString(1);
               datos[1]= rs.getString(2);
               datos[2]= rs.getString(3);
               datos[3]= rs.getString(4);
               datos[4]= rs.getString(5);
               datos[5]= rs.getString(6);
               
               
               
               modelo.addRow(datos);
               
           }
           TablaProveedor.setModel(modelo);
        
           
        }catch (Exception e){
            
            JOptionPane.showMessageDialog(null,"Error al mostrar"+ e.toString());  
        }
         finally {
            objetoconexion.cerrarConexion();
        }
    }
        
         public void InsertarUsuario (JTextField Codigo_Proveedor, JTextField Nombre, JTextField Apellidos, JTextField Direccion, JTextField Provincia, JTextField Telefono ){
            
            CConexion objetoCConexion = new CConexion();
        
            String consulta = "insert into Proveedores(Codigo_Proveedor, Nombre, Apellidos, Direccion, Provincia, Telefono) values (?,?,?,?,?,?);";
    
          try{
              
              PreparedStatement ps = objetoCConexion.establecerConexion().prepareStatement(consulta);
             
          
              ps.setString(1, Codigo_Proveedor.getText());
              ps.setString(2, Nombre.getText());
              ps.setString(3, Apellidos.getText());
              ps.setString(4, Direccion.getText());
              ps.setString(5, Provincia.getText());
              ps.setString(6, Telefono.getText());              
              
              ps.execute();
           
              JOptionPane.showMessageDialog(null, "se guardo correctamente");
              
                
          } catch (Exception e){
              
              JOptionPane.showMessageDialog(null, "no se guardo correctamente"+ e.toString());
          }
          
          finally{
              objetoCConexion.cerrarConexion();
          }
          
    
          }
public void SelecionarUsuario(JTable TablaProvedor, JTextField Codigo_Proveedor, JTextField Nombre, JTextField Apellidos, JTextField Direccion, JTextField Provincia, JTextField Telefono ){
     
     try {
         int fila = TablaProvedor.getSelectedRow();
         
         if(fila >=0){
             
             Codigo_Proveedor.setText(TablaProvedor.getValueAt(fila, 0).toString());
             Nombre.setText(TablaProvedor.getValueAt(fila, 1).toString());
             Apellidos.setText(TablaProvedor.getValueAt(fila, 2).toString());
             Direccion.setText(TablaProvedor.getValueAt(fila, 3).toString());
             Provincia.setText(TablaProvedor.getValueAt(fila, 3).toString());
             Telefono.setText(TablaProvedor.getValueAt(fila, 4).toString());
         }
         else{
             JOptionPane.showMessageDialog(null, "no se pudo selecionar");
             
         
         }
         
     } catch (Exception e){
             JOptionPane.showMessageDialog(null, "Error al selecionar"+e.toString());
         
     }
     
 }
 
  public void ModificarUsuario (JTextField Codigo_Proveedor, JTextField Nombre, JTextField Apellidos, JTextField Direccion, JTextField Provincia, JTextField Telefono){
            
            CConexion objetoCConexion = new CConexion();
        
            String consulta = "UPDATE Proveedores SET Nombre=?, Apellidos=?, Direccion=?, Provincia=?, Telefono=? WHERE Codigo_Proveedor=?;";
    
          try{
              
              PreparedStatement ps = objetoCConexion.establecerConexion().prepareStatement(consulta);
             
          
              
              ps.setString(1, Nombre.getText());
              ps.setString(2, Apellidos.getText());
              ps.setString(3, Direccion.getText());
              ps.setString(4, Provincia.getText());
              ps.setString(5, Telefono.getText());
              ps.setString(6, Codigo_Proveedor.getText());              
              
              ps.execute();
           
              JOptionPane.showMessageDialog(null, "se modifico correctamente");
              
                
          } catch (Exception e){
              
              JOptionPane.showMessageDialog(null, "no se modifico correctamente"+ e.toString());
          }
          
          finally{
              objetoCConexion.cerrarConexion();
          }
          
        }
         
         public void EliminarUsuario (JTextField Codigo_Proveedor){
            
            CConexion objetoCConexion = new CConexion();
        
            String consulta = "DELETE FROM Proveedores WHERE Codigo_Proveedor=?;";
    
          try{
              
              PreparedStatement ps = objetoCConexion.establecerConexion().prepareStatement(consulta);
             
              ps.setString(1, Codigo_Proveedor.getText());              
              
              ps.execute();
           
              JOptionPane.showMessageDialog(null, "se Elimino correctamente");
              
                
          } catch (Exception e){
              
              JOptionPane.showMessageDialog(null, "no se Elmino correctamente"+ e.toString());
          }
          
          finally{
              objetoCConexion.cerrarConexion();
          }
          
        }
   
   public void LimpiarCampos(JTextField Codigo_Proveedor, JTextField Nombre, JTextField Apellidos, JTextField Direccion, JTextField Provincia, JTextField Telefono){
   
       Codigo_Proveedor.setText(" ");
       Nombre.setText(" ");
       Apellidos.setText(" ");
       Direccion.setText(" ");
       Provincia.setText(" ");
       Telefono.setText(" ");
   }
   public void ConsultarProveedor(JTable TablaProveedores, 
                               JTextField Codigo_Proveedor, 
                               JTextField Nombre, 
                               JTextField Apellidos, 
                               JTextField Direccion, 
                               JTextField Provincia, 
                               JTextField Telefono) {

    CConexion objetoConexion = new CConexion();
    DefaultTableModel modelo = new DefaultTableModel();

    // Nuevas columnas para la tabla
    modelo.addColumn("Codigo_Proveedor");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Direccion");
    modelo.addColumn("Provincia");
    modelo.addColumn("Telefono");

    TablaProveedores.setModel(modelo);

    // Construcción dinámica de la consulta SQL
    StringBuilder sql = new StringBuilder("SELECT * FROM Proveedores WHERE 1=1");
    if (!Codigo_Proveedor.getText().trim().isEmpty()) {
        sql.append(" AND Codigo_Proveedor = ?");
    }
    if (!Nombre.getText().trim().isEmpty()) {
        sql.append(" AND Nombre LIKE ?");
    }
    if (!Apellidos.getText().trim().isEmpty()) {
        sql.append(" AND Apellidos LIKE ?");
    }
    if (!Direccion.getText().trim().isEmpty()) {
        sql.append(" AND Direccion LIKE ?");
    }
    if (!Provincia.getText().trim().isEmpty()) {
        sql.append(" AND Provincia LIKE ?");
    }
    if (!Telefono.getText().trim().isEmpty()) {
        sql.append(" AND Telefono LIKE ?");
    }

    try {
        Connection con = objetoConexion.establecerConexion();
        PreparedStatement ps = con.prepareStatement(sql.toString());

        // Asignación de parámetros dinámicamente
        int index = 1;
        if (!Codigo_Proveedor.getText().trim().isEmpty()) {
            ps.setString(index++, Codigo_Proveedor.getText().trim());
        }
        if (!Nombre.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Nombre.getText().trim() + "%");
        }
        if (!Apellidos.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Apellidos.getText().trim() + "%");
        }
        if (!Direccion.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Direccion.getText().trim() + "%");
        }
        if (!Provincia.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Provincia.getText().trim() + "%");
        }
        if (!Telefono.getText().trim().isEmpty()) {
            ps.setString(index++, "%" + Telefono.getText().trim() + "%");
        }

        ResultSet rs = ps.executeQuery();
        String[] datos = new String[6];

        boolean found = false;
        while (rs.next()) {
            datos[0] = rs.getString("Codigo_Proveedor");
            datos[1] = rs.getString("Nombre");
            datos[2] = rs.getString("Apellidos");
            datos[3] = rs.getString("Direccion");
            datos[4] = rs.getString("Provincia");
            datos[5] = rs.getString("Telefono");
            modelo.addRow(datos);
            found = true;
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados");
        }

        TablaProveedores.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al consultar: " + e.toString());
    } finally {
        objetoConexion.cerrarConexion();
    }
}

   }
