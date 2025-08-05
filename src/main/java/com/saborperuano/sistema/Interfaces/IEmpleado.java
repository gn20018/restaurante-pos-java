package com.saborperuano.sistema.Interfaces;

import com.saborperuano.sistema.Models.DTO.EmpleadoDTO;
import com.saborperuano.sistema.Models.Empleado;
import utils.ListaEnlazadaGenerica;

public interface IEmpleado {

    ListaEnlazadaGenerica<EmpleadoDTO> listar();
    Empleado agregar(EmpleadoDTO producto) throws Exception;
    Empleado modificar(EmpleadoDTO producto) throws Exception;
    boolean eliminar(int id) throws Exception;

}
