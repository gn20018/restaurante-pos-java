package com.saborperuano.sistema.Controllers;

import com.saborperuano.sistema.DAO.CredencialDAO;
import com.saborperuano.sistema.Models.Credencial;
import com.saborperuano.sistema.Models.DTO.CredencialDTO;

public class CredencialController {
    public Credencial obtener(int id){
        return new CredencialDAO().obtener(id);
    }

    public Credencial agregar(CredencialDTO credenciales) throws Exception {
        return new CredencialDAO().agregar(credenciales);
    }

    public Credencial modificar(CredencialDTO credenciales) throws Exception {
        return new CredencialDAO().modificar(credenciales);
    }

    public boolean eliminar(int id) throws Exception {
        return new CredencialDAO().eliminar(id);
    }

}
