package com.saborperuano.sistema.Interfaces;

import com.saborperuano.sistema.Models.Credencial;
import com.saborperuano.sistema.Models.DTO.CredencialDTO;

public interface ICredenciales {
    Credencial obtener(int id);
    Credencial agregar(CredencialDTO credenciales) throws Exception;
    Credencial modificar(CredencialDTO credenciales) throws Exception;
    boolean eliminar(int id) throws Exception;

}
