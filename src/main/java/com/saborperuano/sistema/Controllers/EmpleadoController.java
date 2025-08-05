package com.saborperuano.sistema.Controllers;

import com.saborperuano.sistema.DAO.EmpleadoDAO;
import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Models.Empleado;
import utils.ListaEnlazadaGenerica;

public class EmpleadoController {
    public ListaEnlazadaGenerica<EmpleadoDTO> listar() {

        return new EmpleadoDAO().listar();
    }
    public Empleado agregar(EmpleadoDTO registro) throws Exception {
        return new EmpleadoDAO().agregar(registro);
    }

    public Empleado modificar(EmpleadoDTO registro) throws Exception {
        return new EmpleadoDAO().modificar(registro);
    }

    public boolean eliminar(int id) throws Exception {
        return new EmpleadoDAO().eliminar(id);
    }
}
