
package com.misiontic.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionBD {
  
    
    private String DB_driver = "";
    private String url = "";
    private String db = "tienda_virtual";
    private String host = "";
    private String username = "";
    private String password = "";
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private boolean local;
    
    
    public ConexionBD(){
       DB_driver = "com.mysql.cj.jdbc.Driver" ;
       host = "localhost:3306";
       url = "jdbc:mysql://" + host + "/" + db;
       username = "root";
       password = "admin";
       
        try {
            Class.forName(DB_driver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión Exitosa...");
        } catch (Exception ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
    
    //Metodo que retorna la conexion
    public Connection getConnection(){
        return con;
    }
    
    //Metodo para cerrar la conexión
    public void closeConnection (Connection con){
        if(con != null){
            try {
                con.close();
            } catch (Exception ex) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Metodo que permite guardar lo obtenido de una consulta ResultSet 
    public ResultSet consultarBD(String sentencia){
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sentencia);
        }  catch (SQLException sqlex){            
        } catch (RuntimeException rex){
        } catch (Exception ex){
        }
    
        return rs;
    }
    
    //Metodo que realiza un INSERT y devuelve TRUE si la operacion es exitosa
    public boolean insertDB(String sentencia){
       System.out.println("Registro Exitoso...");
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);            
        } catch (Exception sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }
    
    //Metodo que permite ELIMINAR un registro.
    public boolean borrartDB(String sentencia){
       System.out.println("Registro Eliminado Exitosamente...");
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);            
        } catch (Exception sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }
    
    //Metodo que permite ACTUALIZAR un registro.
    public boolean actualizartDB(String sentencia){
       System.out.println("Registro Modificado Exitosamente...");
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);            
        } catch (Exception sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }
    
    //Metodo que permite ELIMINAR un registro.
    public boolean setAutoCommit(boolean parametro){
       
        try {
           con.setAutoCommit(parametro);        
        } catch (SQLException  sqlex) {
            System.out.println("ERROR al configurar el AutoCommit: " + sqlex);
            return false;
        }
        return true;
    }
    
    public void cerrarConexion(){
        closeConnection(con);
    }
    
    //Metodo para realizar el commit
    public boolean CommitBD(){
       
        try {
           con.commit();
           return true;
        } catch (SQLException  sqlex) {
            System.out.println("ERROR al realizar Commit: " + sqlex);
            return false;
        }
    }
    
    //Metodo para realizar el rollback 
    public boolean rollbackBD(){
       
        try {
           con.rollback();
           return true;
        } catch (SQLException  sqlex) {
            System.out.println("ERROR al realizar rollback: " + sqlex);
            return false;
        }
    }
    
    //Metodo Mian para ejecutar y cerrar conexion
    public static void main(String[] args) {
        ConexionBD b = new ConexionBD();
        b.cerrarConexion();
    }
    
}
