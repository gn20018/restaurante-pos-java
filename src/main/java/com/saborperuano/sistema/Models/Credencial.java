package com.saborperuano.sistema.Models;

public class Credencial {
    private int idEmpleado;
    private String usuario;
    private String contrasena;
    private int estado;
    public Credencial() {
    }
    public Credencial(int idEmpleado,
                      String usuario,
                      String contrasena,
                      int estado) {
        this.idEmpleado = idEmpleado;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.estado = estado;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Credenciales{" +
                ", idEmpleado=" + idEmpleado +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", estado=" + estado +
                '}';
    }
}
