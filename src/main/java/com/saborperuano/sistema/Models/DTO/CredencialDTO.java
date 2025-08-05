package com.saborperuano.sistema.Models.DTO;

public class CredencialDTO {
    private int idEmpleado;
    private String usuario;
    private String contrasena;

    public CredencialDTO(int idEmpleado, String usuario, String contrasena) {
        this.idEmpleado = idEmpleado;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    @Override
    public String toString() {
        return "CredencialesDTO{" +
                "idEmpleado=" + idEmpleado +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
