
package Controlador;

import Modelo.CConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Controlador.CDatosCompras;

public class CDatosCompras {

public void MostrarUsuarios(JTable TablaCompras) {
    CConexion objetoconexion = new CConexion();
    DefaultTableModel modelo = new DefaultTableModel();
    String sql = "";
    
    modelo.addColumn("Codigo_Compra");
    modelo.addColumn("Codigo_Cliente");
    modelo.addColumn("Codigo_Producto");
    modelo.addColumn("Descripcion_Producto"); // Nueva columna para la descripción
    modelo.addColumn("Cantidad");
    modelo.addColumn("Fecha");
    
    TablaCompras.setModel(modelo);
    
    // Consulta con JOIN para obtener la descripción del producto
    sql = "SELECT Compras.Codigo_Compra, Compras.Codigo_Cliente, Compras.Codigo_Producto, "
        + "Productos.Descripcion AS Descripcion_Producto, Compras.Cantidad, Compras.Fecha "
        + "FROM Compras "
        + "JOIN Productos ON Compras.Codigo_Producto = Productos.Codigo_Producto";
    
    String[] datos = new String[6]; // Cambia a 6 columnas
    Statement st; 
    
    try {
        st = objetoconexion.establecerConexion().createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
            datos[0] = rs.getString("Codigo_Compra");
            datos[1] = rs.getString("Codigo_Cliente");
            datos[2] = rs.getString("Codigo_Producto");
            datos[3] = rs.getString("Descripcion_Producto"); // Obtener la descripción del producto
            datos[4] = rs.getString("Cantidad");
            datos[5] = rs.getString("Fecha");
            modelo.addRow(datos);
        }
        
        TablaCompras.setModel(modelo);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar: " + e.toString());  
    } finally {
        objetoconexion.cerrarConexion();
    }
}


    public void InsertarUsuario(JTextField Codigo_Compra, JComboBox<String> Codigo_Cliente, JComboBox<String> Codigo_Producto, JTextField Cantidad, JTextField Fecha) {
        CConexion objetoCConexion = new CConexion();
        String consulta = "INSERT INTO Compras(Codigo_Compra, Codigo_Cliente, Codigo_Producto, Cantidad, Fecha) VALUES (?, ?, ?, ?, ?);";
    
        try {
            Connection con = objetoCConexion.establecerConexion();
            Statement stmt = con.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
            PreparedStatement ps = con.prepareStatement(consulta);
            
            ps.setString(1, Codigo_Compra.getText());
            ps.setString(2, Codigo_Cliente.getSelectedItem().toString());  // Usar selectedItem del JComboBox
            ps.setString(3, Codigo_Producto.getSelectedItem().toString()); // Usar selectedItem del JComboBox
            ps.setString(4, Cantidad.getText());
            ps.setString(5, Fecha.getText());
            
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "No se guardó correctamente");
        } finally {
            objetoCConexion.cerrarConexion();
        }
    }

    public void LimpiarCampos(JTextField Codigo_Compra, JComboBox<String> Codigo_Cliente, JComboBox<String> Codigo_Producto, JTextField Cantidad, JTextField Fecha) {
        Codigo_Compra.setText("");
        Codigo_Cliente.setSelectedIndex(0); // Selecciona el primer elemento
        Codigo_Producto.setSelectedIndex(0); // Selecciona el primer elemento
        Cantidad.setText("");
        Fecha.setText("");
    }

    public void ConsultarUsuario(JTable TablaClientes, JTextField Codigo_Compra, JComboBox<String> Codigo_Cliente, JComboBox<String> Codigo_Producto, JTextField Cantidad, JTextField Fecha) {
        CConexion objetoConexion = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo_Compra");
        modelo.addColumn("Codigo_Cliente");
        modelo.addColumn("Codigo_Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Fecha");

        TablaClientes.setModel(modelo);
        StringBuilder sql = new StringBuilder("SELECT * FROM Compras WHERE 1=1");

        if (!Codigo_Compra.getText().trim().isEmpty()) {
            sql.append(" AND Codigo_Compra = ?");
        }
        if (Codigo_Cliente.getSelectedIndex() > 0) { // Solo si se seleccionó un cliente
            sql.append(" AND Codigo_Cliente = ?");
        }
        if (Codigo_Producto.getSelectedIndex() > 0) { // Solo si se seleccionó un producto
            sql.append(" AND Codigo_Producto = ?");
        }
        if (!Cantidad.getText().trim().isEmpty()) {
            sql.append(" AND Cantidad LIKE ?");
        }
        if (!Fecha.getText().trim().isEmpty()) {
            sql.append(" AND Fecha LIKE ?");
        }

        try {
            Connection con = objetoConexion.establecerConexion();
            PreparedStatement ps = con.prepareStatement(sql.toString());
            int index = 1;

            if (!Codigo_Compra.getText().trim().isEmpty()) {
                ps.setString(index++, Codigo_Compra.getText().trim());
            }
            if (Codigo_Cliente.getSelectedIndex() > 0) {
                ps.setString(index++, Codigo_Cliente.getSelectedItem().toString());
            }
            if (Codigo_Producto.getSelectedIndex() > 0) {
                ps.setString(index++, Codigo_Producto.getSelectedItem().toString());
            }
            if (!Cantidad.getText().trim().isEmpty()) {
                ps.setString(index++, "%" + Cantidad.getText().trim() + "%");
            }
            if (!Fecha.getText().trim().isEmpty()) {
                ps.setString(index++, "%" + Fecha.getText().trim() + "%");
            }

            ResultSet rs = ps.executeQuery();
            String[] datos = new String[5];
            boolean found = false;

            while (rs.next()) {
                datos[0] = rs.getString("Codigo_Compra");
                datos[1] = rs.getString("Codigo_Cliente");
                datos[2] = rs.getString("Codigo_Producto");
                datos[3] = rs.getString("Cantidad");
                datos[4] = rs.getString("Fecha");
                modelo.addRow(datos);
                found = true;
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "No se encontraron resultados");
            }

            TablaClientes.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    // Método para llenar combo box de clientes
    public void llenarComboBoxClientes(JComboBox<String> cmbClientes) {
        CConexion objconexion = new CConexion();
        String sql = "SELECT Codigo_Cliente FROM Clientes";
        
        try (Statement st = objconexion.establecerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            cmbClientes.removeAllItems();  // Limpiar combo box
            
            while (rs.next()) {
                cmbClientes.addItem(rs.getString("Codigo_Cliente"));
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar el ComboBox de clientes: " + e.getMessage());
        } finally {
            objconexion.cerrarConexion();
        }
    }

    // Método para llenar combo box de productos
    public void llenarComboBoxProductos(JComboBox<String> cmbProductos) {
        CConexion objconexion = new CConexion();
        String sql = "SELECT Codigo_Producto FROM Productos";
        
        try (Statement st = objconexion.establecerConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            cmbProductos.removeAllItems();  // Limpiar combo box
            
            while (rs.next()) {
                cmbProductos.addItem(rs.getString("Codigo_Producto"));
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar el ComboBox de productos: " + e.getMessage());
        } finally {
            objconexion.cerrarConexion();
        }
    }
}