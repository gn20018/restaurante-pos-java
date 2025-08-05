package com.saborperuano.sistema.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConexionBD {

    private String db="bd_saborperuano";
    private String url="jdbc:mysql://localhost:3306/"+db;
    private String user="root";
    private String password="";
    private boolean conectionSuccess;
    private static Connection conexion=null;

    public ConexionBD(){
        conectionSuccess = false;
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/properties.properties")) {
            prop.load(fis);

            db = prop.getProperty("db.name");
            user = prop.getProperty("db.username");
            password= prop.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url,user,password);
            if (conexion != null){
                System.out.println("Se conecto a la base de datos -"+url);
                conectionSuccess = true;
            }
        }catch (ClassNotFoundException e){
            System.out.println("driver no escontrado");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("error de conexion a la baseDatos");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error global");
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conexion;
}

    public boolean isConectionSuccess() {
        return conectionSuccess;
    }
}