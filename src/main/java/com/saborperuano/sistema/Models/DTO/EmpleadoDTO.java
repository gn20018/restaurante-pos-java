package com.saborperuano.sistema.Models.DTO;

import com.saborperuano.sistema.Models.Enums.Cargo;

public class EmpleadoDTO {
    private String usuario, contrasena, nombre, apellidoPaterno, apellidoMaterno, direccion, correo;
    private int codigo, dni, telefono;
    private double salario;

    private Cargo cargo;
    private String estado;

    public EmpleadoDTO(int codigo,
                       String usuario,
                       String contrasena,
                       String nombre,
                       String apellidoPaterno,
                       String apellidoMaterno,
                       String direccion,
                       String correo,
                       int dni,
                       int telefono,
                       double salario,
                       Cargo cargo) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.direccion = direccion;
        this.correo = correo;
        this.codigo = codigo;
        this.dni = dni;
        this.telefono = telefono;
        this.salario = salario;
        this.cargo = cargo;
    }

    public EmpleadoDTO(int codigo,
                       String usuario,
                       String contrasena,
                       String nombre,
                       String apellidoPaterno,
                       String apellidoMaterno,
                       String direccion,
                       String correo,
                       int dni,
                       int telefono,
                       double salario,
                       Cargo cargo, String estado) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.direccion = direccion;
        this.correo = correo;
        this.codigo = codigo;
        this.dni = dni;
        this.telefono = telefono;
        this.salario = salario;
        this.cargo = cargo;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" +
                "usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correo='" + correo + '\'' +
                ", codigo=" + codigo +
                ", dni=" + dni +
                ", telefono=" + telefono +
                ", salario=" + salario +
                ", cargo=" + cargo +
                ", estado='" + estado + '\'' +
                '}';
    }
}
