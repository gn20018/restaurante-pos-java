package com.saborperuano.sistema.DAO;

import com.saborperuano.sistema.Interfaces.IDetalleVenta;
import com.saborperuano.sistema.Models.DTO.CartaDTO;
import com.saborperuano.sistema.Models.DetalleVenta;
import com.saborperuano.sistema.Models.DTO.DetalleVentaDTO;
import com.saborperuano.sistema.Models.DTO.MenuDTO;
import com.saborperuano.sistema.Views.Principal.FrmPrincipal;
import utils.ListaEnlazadaGenerica;

import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.listaCarta;
import static com.saborperuano.sistema.Views.Principal.FrmPrincipal.ventaActual;

public class DetalleVentaDAO implements IDetalleVenta {

    @Override
    public ListaEnlazadaGenerica<DetalleVentaDTO> listar(int idVenta) {
        String consulta = "{call sp_listarDetallesVenta(?)}";
        Connection conexion = ConexionBD.getConnection();
        ListaEnlazadaGenerica<DetalleVentaDTO> detallesVenta = null;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1,idVenta);
            ResultSet filas = sentencia.executeQuery();

            detallesVenta = new ListaEnlazadaGenerica<>();
            while (filas.next()) {

                int idMenu=-1,idCarta=-1;
                if (filas.getObject("idMenu")== null){
                    idMenu = -1;
                }else {
                    idMenu = filas.getInt("idMenu");
                }

                if (filas.getObject("idCarta")== null){
                    idCarta = -1;
                }else {
                    idCarta= filas.getInt("idCarta");
                }


                DetalleVenta nuevoRegistro = new DetalleVenta(
                        filas.getInt("idDetalle_Venta"),
                        filas.getInt("idVenta"),
                        idMenu,
                        idCarta,
                        filas.getInt("cantidad"),
                        filas.getDouble("precioUnitario"),
                        filas.getDouble("descuento"),
                        filas.getDouble("precioTotal"),
                        filas.getDouble("importePagar"));


                DetalleVentaDTO nuevoDetalleVentaDTO = null;


                if (nuevoRegistro.getCodigoCarta()!=-1 && nuevoRegistro.getCodigoMenu()==-1){
                    listaCarta.mostrarLista();
                    CartaDTO cartaDTO = FrmPrincipal.listaCarta.obtenerObjetoPorCampo(FrmPrincipal.listaCarta,"Codigo",
                            nuevoRegistro.getCodigoCarta());


                    if (cartaDTO != null){
                        nuevoDetalleVentaDTO = new DetalleVentaDTO(
                                detallesVenta.longitud+1,
                                cartaDTO.getCodigo(),
                                cartaDTO.getNombre(),
                                cartaDTO.getCategoria().toString(),
                                nuevoRegistro.getCantidad(),
                                nuevoRegistro.getPrecioUnitario(),
                                nuevoRegistro.getPrecioTotal(),
                                nuevoRegistro.getDescuento(),
                                nuevoRegistro.getImportePagar()
                                );

                    }

                }

                if (nuevoRegistro.getCodigoCarta()==-1 && nuevoRegistro.getCodigoMenu() != -1){
                    MenuDTO menuDTO = FrmPrincipal.listaMenu.obtenerObjetoPorCampo(FrmPrincipal.listaMenu,"codigo",
                            nuevoRegistro.getCodigoMenu());

                    if (menuDTO != null){
                        nuevoDetalleVentaDTO = new DetalleVentaDTO(
                                detallesVenta.longitud+1,
                                menuDTO.getCodigo(),
                                menuDTO.getNombre(),
                                menuDTO.getCategoria().toString(),
                                nuevoRegistro.getCantidad(),
                                nuevoRegistro.getPrecioUnitario(),
                                nuevoRegistro.getPrecioTotal(),
                                nuevoRegistro.getDescuento(),
                                nuevoRegistro.getImportePagar()
                        );
                    }
                }


                if (detallesVenta.longitud == 0){
                    detallesVenta.agregarInicio(nuevoDetalleVentaDTO);
                }else {
                    detallesVenta.agregarAlFinal(nuevoDetalleVentaDTO);
                }

            }
            filas.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return detallesVenta;
    }

    @Override
    public DetalleVenta obtener(int id) {
        return null;
    }

    @Override
    public DetalleVenta agregar(DetalleVentaDTO registro) throws Exception {

        String consulta = "{call sp_agregarDetalleVenta(?,?,?,?,?,?,?,?)}";
        Connection conexion = ConexionBD.getConnection();
        DetalleVenta nuevoRegistro = null;

        try {

            MenuDTO menuDTO = FrmPrincipal.listaMenu.obtenerObjetoPorCampo(FrmPrincipal.listaMenu,
                    "Nombre",registro.getNombreProducto());

            System.out.println("ventaActual.getNroVenta() DVDAO = " + ventaActual.getNroVenta());

            if (menuDTO != null){
                nuevoRegistro = new DetalleVenta(
                        ventaActual.getNroVenta(),
                        menuDTO.getCodigo(),
                        -1,
                        registro.getCantidad(),
                        registro.getPrecioUnitario(),
                        registro.getPrecioTotal(),
                        registro.getDescuento(),
                        registro.getImporteAPagar());

            }else {
                CartaDTO cartaDTO = FrmPrincipal.listaCarta.obtenerObjetoPorCampo(FrmPrincipal.listaCarta,
                        "Nombre",registro.getNombreProducto());
                if (cartaDTO != null){
                    nuevoRegistro = new DetalleVenta(
                            ventaActual.getNroVenta(),
                            -1,
                            cartaDTO.getCodigo(),
                            registro.getCantidad(),
                            registro.getPrecioUnitario(),
                            registro.getPrecioTotal(),
                            registro.getDescuento(),
                            registro.getImporteAPagar());
                }else {
                    JOptionPane.showMessageDialog(null,"Ocurrió un error al agregar el detalle de venta a la bd.");
                }
            }

            System.out.println("nuevoRegistro = " + nuevoRegistro);

            //Insertandos a la sentencia y ejecutando proc almc en bd
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, Objects.requireNonNull(nuevoRegistro).getCodigoVenta());
            sentencia.setInt(2,nuevoRegistro.getCodigoMenu());
            sentencia.setInt(3,nuevoRegistro.getCodigoCarta());
            sentencia.setInt(4,nuevoRegistro.getCantidad());
            sentencia.setDouble(5,nuevoRegistro.getPrecioUnitario());
            sentencia.setDouble(6,nuevoRegistro.getPrecioTotal());
            sentencia.setDouble(7,nuevoRegistro.getDescuento());
            sentencia.setDouble(8,nuevoRegistro.getImportePagar());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Nuevo registro en DetalleVenta Agregado Correctamente");
            } else {
                throw new Exception("Error. No se pudo agregar el registro correctamente.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return nuevoRegistro;
    }

    @Override
    public DetalleVenta modificar(DetalleVentaDTO producto) throws Exception {
        return null;
    }

    @Override
    public boolean eliminar(int idDetalleVenta) throws Exception {
        String consulta = "{call sp_eliminarDetalleVenta(?)}";
        Connection conexion = ConexionBD.getConnection();
        boolean resultado = true;

        try {
            CallableStatement sentencia = conexion.prepareCall(consulta);
            sentencia.setInt(1, idDetalleVenta);

            int numeroFilasAfectadas = sentencia.executeUpdate();

            if (numeroFilasAfectadas == 0) {
                resultado = false;
                throw new Exception("Error. No se pudo eliminar el registro del detalle de venta con el id proporcionado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}
