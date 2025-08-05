package com.saborperuano.sistema.DAO;

import com.saborperuano.sistema.Interfaces.ICredenciales;
import com.saborperuano.sistema.Models.Credencial;
import com.saborperuano.sistema.Models.DTO.CredencialDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredencialDAO implements ICredenciales {
    @Override
    public Credencial obtener(int id) {
        Credencial credencial = null;
        Connection conexion = ConexionBD.getConnection();
        try {

            CallableStatement statement = conexion.prepareCall("{call sp_obtenerCredencialPorIdEmpleado(?)}");


            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                credencial = new Credencial();
                credencial.setIdEmpleado(resultSet.getInt("idEmpleado"));
                credencial.setUsuario(resultSet.getString("usuario"));
                credencial.setContrasena(resultSet.getString("contrasena"));
                credencial.setEstado(resultSet.getInt("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return credencial;
    }

    @Override
    public Credencial agregar(CredencialDTO credenciales) throws Exception {
        Credencial credencial = null;
        Connection conexion = ConexionBD.getConnection();
        try {
             CallableStatement sentencia = conexion.prepareCall("{call sp_agregarCredenciales(?,?,?)}");
            sentencia.setInt(1, credenciales.getIdEmpleado());
            sentencia.setString(2, credenciales.getUsuario());
            sentencia.setString(3, credenciales.getContrasena());

            int filasAfectadas = sentencia.executeUpdate();


            credencial = new Credencial(credenciales.getIdEmpleado(), credenciales.getUsuario(),
                    credenciales.getContrasena(),1);

            if (filasAfectadas == 0) {
                throw new Exception("Error. No se pudo agregar las credenciales.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credencial;
    }

    @Override
    public Credencial modificar(CredencialDTO credenciales) throws Exception {
        Credencial registroModificado = null;
        Connection conexion = ConexionBD.getConnection();
        try {
             CallableStatement sentencia = conexion.prepareCall("{call sp_modificarCredenciales(?,?,?,?)}");

            sentencia.setInt(1, credenciales.getIdEmpleado());
            sentencia.setString(2, credenciales.getUsuario());
            sentencia.setString(3, credenciales.getContrasena());
            sentencia.setInt(4,1);

            int filasAfectadas = sentencia.executeUpdate();


            registroModificado = new Credencial(credenciales.getIdEmpleado(), credenciales.getUsuario(),
                    credenciales.getContrasena(),1);

            if (filasAfectadas == 0) {
                throw new Exception("Error. No se pudo modificar los datos de la carta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return registroModificado;
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        boolean resultado = true;
        Connection conexion = ConexionBD.getConnection();
        try {
             CallableStatement sentencia = conexion.prepareCall("{call sp_eliminarCredencial(?)}");

            sentencia.setInt(1, id);

            int numeroFilasAfectadas = sentencia.executeUpdate();


            if (numeroFilasAfectadas == 0) {
                resultado = false;
                throw new Exception("Error. No se pudo eliminar el registro de venta con el id proporcionado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return resultado;
    }
}
