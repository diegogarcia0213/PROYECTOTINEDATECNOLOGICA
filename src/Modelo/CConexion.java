/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
/**
 *
 * @author Diego A. Garcia
 */
public class CConexion {
    Connection conectar = null;
    // Cadena de conexión
    String bd = "tienda.db";
    String cadena = "jdbc:sqlite:"+ System.getProperty("user.dir")+"/"+bd;
    
    
    // Método para establecer conexión
    public Connection establecerConexion() {
        
        Connection conectar = null;
        
        try {
            // Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");
            // Abrir conexión
            conectar = DriverManager.getConnection(cadena);
           // JOptionPane.showMessageDialog(null, "Conexión exitosa");
        } catch (Exception e) {
            // Mostrar mensaje de error si la conexión falla
            JOptionPane.showMessageDialog(null, "Conexión NO realizada: " + e.toString());
        }
        return conectar;
    }
    
    public void cerrarConexion(){
        try{
            if(conectar !=null){
                conectar.close();
                JOptionPane.showMessageDialog(null, "se cerro la conexion correctamente");
        }
    }catch (Exception e) {
    JOptionPane.showMessageDialog(null, "no se cerro Conexión correctamente " + e.toString());
    }

    }
}
    
