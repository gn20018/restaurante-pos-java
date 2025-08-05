package com.saborperuano.sistema.Controllers;

import com.saborperuano.sistema.DAO.CartaDAO;
import com.saborperuano.sistema.Models.DTO.CartaDTO;
import utils.ListaEnlazadaGenerica;

public class CartaController {
    public ListaEnlazadaGenerica<CartaDTO> listar() {
        return new CartaDAO().listar();
    }

    public CartaDTO obtener(int id) {
        return new CartaDAO().obtener(id);
    }

    public CartaDTO agregar(CartaDTO registro) throws Exception {
        return new CartaDAO().agregar(registro);
    }

    public CartaDTO modificar(CartaDTO registro) throws Exception {
        return new CartaDAO().modificar(registro);
    }

    public boolean eliminar(int id) throws Exception {
        return new CartaDAO().eliminar(id);
    }
}
