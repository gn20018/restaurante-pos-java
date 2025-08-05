package com.saborperuano.sistema.Interfaces;

import com.saborperuano.sistema.Models.DTO.CartaDTO;
import utils.ListaEnlazadaGenerica;

public interface ICarta {

    ListaEnlazadaGenerica<CartaDTO> listar();
    CartaDTO obtener(int id);
    CartaDTO agregar(CartaDTO producto) throws Exception;
    CartaDTO modificar(CartaDTO producto) throws Exception;
    boolean eliminar(int id) throws Exception;

}
