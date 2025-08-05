package com.saborperuano.sistema.Models;

import java.sql.Date;

public class Empleado {
    private int idEmpleado;
    private int idCargo;
    private int idEmpresa;
    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private int dni;
    private String direccion;
    private String correo_electronico;
    private int telefono;
    private Date fecha_inicio;
    private double salario;
    private int estado;

    public Empleado(int idEmpleado,
                    int idCargo,
                    int idEmpresa,
                    String nombres,
                    String apellido_paterno,
                    String apellido_materno,
                    int dni, String direccion,
                    String correo_electronico,
                    int telefono,
                    Date fecha_inicio,
                    double salario,
                    int estado) {
        this.idEmpleado = idEmpleado;
        this.idCargo = idCargo;
        this.idEmpresa = idEmpresa;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.dni = dni;
        this.direccion = direccion;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
        this.fecha_inicio = fecha_inicio;
        this.salario = salario;
        this.estado = estado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + idEmpleado +
                ", idCargo=" + idCargo +
                ", nombres='" + nombres + '\'' +
                ", apellido_paterno='" + apellido_paterno + '\'' +
                ", apellido_materno='" + apellido_materno + '\'' +
                ", fecha_inicio=" + fecha_inicio +
                ", estado=" + estado +
                '}';
    }
}
