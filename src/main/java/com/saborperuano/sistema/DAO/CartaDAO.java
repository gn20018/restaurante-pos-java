package com.saborperuano.sistema.DAO;

import com.saborperuano.sistema.Interfaces.ICarta;
import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.Enums.Categoria;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;
import utils.ListaEnlazadaGenerica;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaDAO implements ICarta {

    @Override
    public ListaEnlazadaGenerica<CartaDTO> listar() {
            String consulta = "{call sp_listarCarta}";
        Connection conexion = ConexionBD.getConnection();
        ListaEnlazadaGenerica<CartaDTO> carta = null;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            ResultSet filas = sentencia.executeQuery();

            carta = new ListaEnlazadaGenerica<>();
            while (filas.next()) {
                CartaDTO nuevoRegistro = new CartaDTO(
                        filas.getInt("codigo"),
                        filas.getString("nombre"),
                        filas.getString("descripcion"),
                        filas.getString("tipo_platillo"),
                        Categoria.valueOf(filas.getString("categoria")),
                        filas.getFloat("precio_unitario"),
                        EstadoRegistro.valueOf(switch (filas.getInt("estado")) {
                            case 1-> "Habilitado";
                            default -> "Deshabilitado";
                        }));


                if (carta.longitud == 0){
                    carta.agregarInicio(nuevoRegistro);
                }else {
                    carta.agregarAlFinal(nuevoRegistro);
                }

            }
            filas.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carta;
    }

    @Override
    public CartaDTO obtener(int id) {
        String consulta = "{call sp_obtenerCarta(?)}";
        Connection conexion = ConexionBD.getConnection();
        CartaDTO nuevoRegistro = null;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, id);

            ResultSet filas = sentencia.executeQuery();

            while (filas.next()) {
                nuevoRegistro = new CartaDTO(
                        filas.getInt("idCarta"),
                        filas.getString("nombre"),
                        filas.getString("descripcion"),
                        filas.getString("tipo_platillo"),
                        Categoria.valueOf(filas.getString("categoria")),
                        filas.getFloat("precio_unitario"),
                        EstadoRegistro.valueOf(switch (filas.getInt("estado")) {
                            case 1-> "Habilitado";
                            default -> "Deshabilitado";
                        }));
            }
            filas.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nuevoRegistro;
    }

    @Override
    public CartaDTO agregar(CartaDTO registro) throws Exception {
        String consulta = "{call sp_agregarCarta(?,?,?,?,?,?)}";
        Connection conexion = ConexionBD.getConnection();

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, (registro.getCategoria().ordinal()+1));
            sentencia.setString(2, registro.getTipoPlatillo());
            sentencia.setString(3, registro.getNombre());
            sentencia.setString(4, registro.getDescripcion());
            sentencia.setFloat(5, registro.getPrecioUnitario());
            sentencia.setInt(6,registro.getEstado().ordinal());
            System.out.println("registro.getEstado().ordinal() = " + registro.getEstado().ordinal());


            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Nuevo registro en carta Agregado Correctamente");
            } else {
                throw new Exception("Error. No se pudo agregar el registro correctamente.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registro;
    }

    @Override
    public CartaDTO modificar(CartaDTO registro) throws Exception {
        String consulta = "{call sp_modificarCarta(?,?,?,?,?,?,?)}";
        Connection conexion = ConexionBD.getConnection();

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, registro.getCodigo());
            sentencia.setInt(2, (registro.getCategoria().ordinal()+1));
            sentencia.setString(3, registro.getTipoPlatillo());
            sentencia.setString(4, registro.getNombre());
            sentencia.setString(5, registro.getDescripcion());
            sentencia.setFloat(6, registro.getPrecioUnitario());
            sentencia.setInt(7,registro.getEstado().ordinal());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas == 0) {
                throw new Exception("Error. No se pudo modificar los datos de la carta.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registro;
    }

    @Override
    public boolean eliminar(int idCarta) throws Exception {
        String consulta = "{call sp_eliminarCarta(?)}";
        Connection conexion = ConexionBD.getConnection();
        boolean resultado = true;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, idCarta);

            int numeroFilasAfectadas = sentencia.executeUpdate();

            if (numeroFilasAfectadas == 0) {
                resultado = false;
                throw new Exception("Error. No se pudo eliminar el registro de la carta con el id proporcionado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

}
