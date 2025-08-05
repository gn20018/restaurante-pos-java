package com.saborperuano.sistema.Controllers;

import com.saborperuano.sistema.DAO.VentaDAO;
import com.saborperuano.sistema.Models.Venta;
import com.saborperuano.sistema.Models.DTO.VentaDTO;
import utils.ListaEnlazadaGenerica;

public class VentaController {
    public ListaEnlazadaGenerica<VentaDTO> listar() {
        return new VentaDAO().listar();
    }

    public Venta obtener(int id) {
        return new VentaDAO().obtener(id);
    }

    public Venta agregar(VentaDTO registro) throws Exception {
        return new VentaDAO().agregar(registro);
    }

    public Venta modificar(VentaDTO registro) throws Exception {
        return new VentaDAO().modificar(registro);
    }

    public boolean eliminar(int id) throws Exception {
        return new VentaDAO().eliminar(id);
    }
}
