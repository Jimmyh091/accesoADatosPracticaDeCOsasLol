package ejercicio2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AccesoDatos {
    
    private String base_datos,usuario,password,driver,host;
    private Connection c;
    
    public AccesoDatos(String host,String base_datos,String usuario,String password,String driver){
        this.driver=driver;
        this.host=host;
        this.base_datos=base_datos;
        this.usuario=usuario;
        this.password=password;
    }
    
    public Connection conexion(){
        Connection c = null;
        try {
            Class.forName(this.driver);
            c = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.base_datos, this.usuario, this.password);
            return c;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public ArrayList<String[]> verPensiones()throws ClassNotFoundException, SQLException{
        c = conexion();
        
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select * from cuentas where tipo = 'pension'");
        
        ArrayList<String[]> res = new ArrayList<>();
        res.add(this.atributos_consulta(rs.getMetaData()));
                
       
        while(rs.next()){
            System.out.println("ID "+rs.getInt(1));
            
            String[] lista = new String[rs.getMetaData().getColumnCount()];
            
            lista[0] = "" + rs.getInt(1);
            lista[1] = rs.getString(2);
            lista[2] = rs.getString(3);
            lista[3] = "" + rs.getInt(4);
            lista[4] = rs.getString(5);
            lista[5] = "" + rs.getFloat(6);
            lista[6] = "" + rs.getInt(7);
            
            res.add(lista);
        }
        
        return res;
    }
    
    public ArrayList<String[]> cuentasSucursal(String sucursal)throws ClassNotFoundException, SQLException{
        
        return new ArrayList<>();
    }
    
    public void nuevaSucursal(String denominacion,String director,String poblacion,String telefono)throws ClassNotFoundException, SQLException{
        
    }
    
    public void aumentarCuentas(double subida)throws ClassNotFoundException, SQLException{
        
    }
   
    public String mejorDirector()throws ClassNotFoundException, SQLException{
        return "Nada";
    }
    
    
    
    
    public String[] atributos_consulta(ResultSetMetaData metadatos) throws SQLException{
        String[] nombres_atributos=new String[metadatos.getColumnCount()];
       
        for(int i=0;i<metadatos.getColumnCount();i++){
            nombres_atributos[i]=metadatos.getColumnName(i+1);
        }
        
        return nombres_atributos;
        
    }
    
    
    public String[] valores_fila(ResultSet fila) throws SQLException{
        
        String[] valores=new String[fila.getMetaData().getColumnCount()];
            for(int i=0;i<fila.getMetaData().getColumnCount();i++){
                valores[i]=fila.getString(i+1);
            }
        return valores;
        
    }
    
    
    
    
    public void metodoPlantillaSQL() {
        String nombre="",localizacion="",ocupacion="",fecha="";
      int filas;
      try{  
        
        Class.forName(driver);
        Connection conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/"+base_datos,
                                                  usuario, //usuario de la BD
                                                 password); //contraseña
        
        
        //INSERT UPDATE DELETE
        
        //SIN PREPARAR
        String sql_insert = "INSERT INTO departamentos VALUES ('"+nombre+"', '"+localizacion+"')";
        System.out.println(sql_insert);
        Statement sentencia_insert = conexion.createStatement();
        filas = sentencia_insert.executeUpdate(sql_insert);
        System.out.println("Filas afectadas: " + filas);
        sentencia_insert.close(); // Cerrar Statement
        conexion.close(); // Cerrar conexion
        
        //PREPARADA
        String sql_prepa_insert= "INSERT INTO departamentos VALUES (?, ?)"; 
        PreparedStatement sentencia_prepa_insert = conexion.prepareStatement(sql_prepa_insert);
        filas = sentencia_prepa_insert.executeUpdate();
        System.out.println("Filas afectadas: " + filas);
        sentencia_prepa_insert.close(); // Cerrar Statement
        conexion.close(); // Cerrar conexion
        
        //SELECT
        
        //SIN PREPARAR
        String sql_select = "SELECT * FROM empleados "
                               + "WHERE ocupacion ='"+ocupacion+"'AND fecha_alta='"+fecha+"'"; 
        Statement sentencia_select = conexion.createStatement();
        
        ResultSet resul = sentencia_select.executeQuery(sql_select);
        while (resul.next())
        {
                
                
        }
        resul.close(); //Cerrar ResultSet
        sentencia_select.close(); // Cerrar Statement
        conexion.close(); // Cerrar conexión    
        
        //PREPARADA
        String sql_prepa_select = "SELECT * FROM empleados WHERE ocupacion =? AND fecha_alta=?"; 
        PreparedStatement sentencia_prepa_select = conexion.prepareStatement(sql_prepa_select);
        sentencia_prepa_select.setString(1, "ocupacion");
        sentencia_prepa_select.setString(2, "fecha de alta");
        ResultSet resul_prepa = sentencia_prepa_select.executeQuery();
        while (resul.next())
        {
                
                
        }
        resul.close(); //Cerrar ResultSet
        sentencia_prepa_select.close(); // Cerrar Statement
        conexion.close(); // Cerrar conexión    
      }catch(ClassNotFoundException cfe){
        JOptionPane.showMessageDialog(null, cfe.getMessage());  
      }catch(SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());  
      }   
    }
}
