package com.saborperuano.sistema.DAO;

import com.saborperuano.sistema.Interfaces.IVenta;
import com.saborperuano.sistema.Models.*;
import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Models.DTO.VentaDTO;
import com.saborperuano.sistema.Models.Enums.TipoComprobante;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import org.joda.time.DateTime;
import utils.ListaEnlazadaGenerica;

import java.sql.*;

public class VentaDAO implements IVenta {

    @Override
    public ListaEnlazadaGenerica<VentaDTO> listar() {
            String consulta = "{call sp_listarVentas}";
        Connection conexion = ConexionBD.getConnection();
        ListaEnlazadaGenerica<VentaDTO> ventas = null;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            ResultSet filas = sentencia.executeQuery();

            ventas = new ListaEnlazadaGenerica<>();
            while (filas.next()) {

                Venta nuevoRegistro = new Venta(
                        filas.getInt("idVenta"),
                        filas.getInt("idEmpresa"),
                        filas.getInt("idEmpleado"),
                        filas.getInt("idTipo_Comprobante"),
                        new DateTime(filas.getTimestamp("fecha_Venta").getTime()),
                        filas.getInt("cantidad_total"),
                        filas.getInt("descuentoTotal"),
                        filas.getDouble("monto_total"),
                        filas.getString("estado")
                );


                EmpleadoDTO empleado = FrmPrincipal.listaPersonal.obtenerObjetoPorCampo(FrmPrincipal.listaPersonal,
                        "codigo",nuevoRegistro.getIdEmpleado());

                String nombreEmpleado = String.format("%s %s %s",empleado.getNombre(),
                        empleado.getApellidoPaterno(),empleado.getApellidoMaterno());
                TipoComprobante[] tipoComprobantes = TipoComprobante.values();

                String tipoComprobante = tipoComprobantes[nuevoRegistro.getIdTipo_Comprobante()-1].toString();


                VentaDTO nuevaVentaDTO = null;


                if (empleado != null){
                     nuevaVentaDTO = new VentaDTO(
                            nuevoRegistro.getIdVenta(),
                            nombreEmpleado,
                            TipoComprobante.valueOf(tipoComprobante),
                            nuevoRegistro.getFecha_Venta(),
                            nuevoRegistro.getCantidad_total(),
                            nuevoRegistro.getDescuento_total(),
                            nuevoRegistro.getMonto_total(),
                            nuevoRegistro.getEstado());
                }



                if (ventas.longitud == 0){
                    ventas.agregarInicio(nuevaVentaDTO);
                }else {
                    ventas.agregarAlFinal(nuevaVentaDTO);
                }

            }
            filas.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ventas;
    }

    @Override
    public Venta obtener(int id) {
        return null;
    }

    @Override
    public Venta agregar(VentaDTO registro) throws Exception {

        String consulta = "{call sp_agregarVenta(?,?,?,?,?,?,?,?)}";
        Connection conexion = ConexionBD.getConnection();
        Venta nuevoRegistro = null;

        try {


            nuevoRegistro = new Venta(
                    registro.getNroVenta(),
                    1,
                    FrmPrincipal.empleadoActual.getCodigo(),
                    registro.getTipoComprobante().ordinal()+1,
                    registro.getFecha_venta(),
                    registro.getCantidadProductos(),
                    registro.getDescuento_total(),
                    registro.getMontoTotal(),
                    registro.getEstado());

            //Insertandos a la sentencia y ejecutando proc almc en bd
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1,nuevoRegistro.getIdEmpresa());
            sentencia.setInt(2,nuevoRegistro.getIdEmpleado());
            sentencia.setInt(3,nuevoRegistro.getIdTipo_Comprobante());
            sentencia.setTimestamp(4, new Timestamp(nuevoRegistro.getFecha_Venta().getMillis()));
            sentencia.setInt(5,nuevoRegistro.getCantidad_total());
            sentencia.setDouble(6,nuevoRegistro.getDescuento_total());
            sentencia.setDouble(7,nuevoRegistro.getMonto_total());
            sentencia.setString(8,nuevoRegistro.getEstado());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Nuevo registro en Venta Agregada Correctamente");
            } else {
                throw new Exception("Error. No se pudo agregar el registro correctamente.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return nuevoRegistro;
    }

    @Override
    public Venta modificar(VentaDTO producto) throws Exception {
        return null;
    }

    @Override
    public boolean eliminar(int idVenta) throws Exception {
        String consulta = "{call sp_eliminarVenta(?)}";
        Connection conexion = ConexionBD.getConnection();
        boolean resultado = true;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, idVenta);

            int numeroFilasAfectadas = sentencia.executeUpdate();


            if (numeroFilasAfectadas == 0) {
                resultado = false;
                throw new Exception("Error. No se pudo eliminar el registro de venta con el id proporcionado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;

    }
}
