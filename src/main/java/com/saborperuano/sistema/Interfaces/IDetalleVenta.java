package com.saborperuano.sistema.Interfaces;

import com.saborperuano.sistema.Models.DetalleVenta;
import com.saborperuano.sistema.Models.DTO.DetalleVentaDTO;
import utils.ListaEnlazadaGenerica;

public interface IDetalleVenta {
    ListaEnlazadaGenerica<DetalleVentaDTO> listar(int idVenta);
    DetalleVenta obtener(int id);
    DetalleVenta agregar(DetalleVentaDTO producto) throws Exception;
    DetalleVenta modificar(DetalleVentaDTO producto) throws Exception;
    boolean eliminar(int id) throws Exception;
}
