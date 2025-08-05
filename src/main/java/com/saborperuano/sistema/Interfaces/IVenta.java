package com.saborperuano.sistema.Interfaces;

import com.saborperuano.sistema.Models.Venta;
import com.saborperuano.sistema.Models.DTO.VentaDTO;
import utils.ListaEnlazadaGenerica;

public interface IVenta {
    ListaEnlazadaGenerica<VentaDTO> listar();
    Venta obtener(int id);
    Venta agregar(VentaDTO registro) throws Exception;
    Venta modificar(VentaDTO registro) throws Exception;
    boolean eliminar(int id) throws Exception;
}
