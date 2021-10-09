/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misiontic.logica;

import com.misiontic.persistencia.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author o
 */
public class Producto {
    
    private int codigo;
    private String nombre;
    private int cantidad;
    private String categoria;
    private double precio;

    
    public Producto(){}
    
   
    public Producto getProducto(int codigo)throws SQLException{
        this.codigo = codigo;
        return this.getProducto();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
    
    
    
    
    public void llenarProducto(int codigo, String nombre, int cantidad, String categoria, double precio){
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.precio = precio;        
    }
    
    public boolean guardarProducto() {
        System.out.println("bien");
        ConexionBD conexion = new ConexionBD();
        String sentencia = "INSERT INTO producto(codigo,nombre,categoria,cantidad,precio)"
                +"VALUES('"+this.codigo+"','"+this.nombre+"','"+this.categoria+"',"+this.cantidad+","+this.precio+");";
        System.out.println(sentencia);
        if (conexion.setAutoCommit(false)) {
            if (conexion.insertDB(sentencia)) {
                conexion.CommitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
        
   
    }
    
    public boolean borrarProducto(int codigo){
        ConexionBD conexion = new ConexionBD();
        String sentencia = "DELETE FROM producto WHERE codigo="+ codigo +";";
        if (conexion.setAutoCommit(false)) {
            if (conexion.actualizartDB(sentencia)) {
                conexion.CommitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }
    
    public boolean actualizarProducto() {
        ConexionBD conexion = new ConexionBD();
        String sentencia = "UPDATE producto SET nombre='"
                +this.nombre+"',categoria='"+this.categoria
                +"',cantidad="+this.cantidad+",precio="
                +this.precio+" WHERE codigo="+this.codigo+";";
        if (conexion.setAutoCommit(false)) {
            if (conexion.actualizartDB(sentencia)) {
                conexion.CommitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }
    
    public List<Producto> listarProductos() throws SQLException{
        List<Producto> listaProductos = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        String sentencia = "select * from producto order by codigo asc";
        System.out.println("Antes de inicializar el rs");
            ResultSet rs = conexion.consultarBD(sentencia);
            System.out.println(rs);
            Producto p;
            while (rs.next()) {
                p=new Producto();
                p.setCodigo(rs.getInt("codigo"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                listaProductos.add(p);
            }
        
            conexion.cerrarConexion();
        
        return listaProductos;
    }
    
    public Producto getProducto() throws SQLException {
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from producto where codigo='" + this.codigo + "'";
        ResultSet rs = conexion.consultarBD(sql);
        if (rs.next()) {
            this.codigo = rs.getInt("codigo");
            this.nombre = rs.getString("nombre");
            this.categoria = rs.getString("categoria");
            this.precio = rs.getDouble("precio");
            this.cantidad = rs.getInt("cantidad");
            return this;
            
        } else{
            conexion.cerrarConexion();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", nombre=" + nombre + ", cantidad=" + cantidad + ", categoria=" + categoria + ", precio=" + precio + '}';
    }
    
    

}
