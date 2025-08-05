package com.saborperuano.sistema.Controllers;

import com.saborperuano.sistema.DAO.DetalleVentaDAO;
import com.saborperuano.sistema.Models.DetalleVenta;
import com.saborperuano.sistema.Models.DTO.DetalleVentaDTO;
import utils.ListaEnlazadaGenerica;

public class DetalleVentaController {
    public ListaEnlazadaGenerica<DetalleVentaDTO> listar(int idVenta) {
        return new DetalleVentaDAO().listar(idVenta);
    }

    public DetalleVenta obtener(int id) {
        return new DetalleVentaDAO().obtener(id);
    }

    public DetalleVenta agregar(DetalleVentaDTO registro) throws Exception {
        return new DetalleVentaDAO().agregar(registro);
    }

    public DetalleVenta modificar(DetalleVentaDTO registro) throws Exception {
        return new DetalleVentaDAO().modificar(registro);
    }

    public boolean eliminar(int id) throws Exception {
        return new DetalleVentaDAO().eliminar(id);
    }
}
