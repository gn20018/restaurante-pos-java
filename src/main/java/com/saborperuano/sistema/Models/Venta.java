package com.saborperuano.sistema.Models;

import org.joda.time.DateTime;

public class Venta {

    private int idVenta;
    private int idEmpresa;
    private int idEmpleado;
    private int idTipo_Comprobante;
    private DateTime fecha_Venta;
    private int cantidad_total;
    private double descuento_total;
    private double monto_total;
    private String estado;

    public Venta(int idVenta, int idEmpresa,
                 int idEmpleado,
                 int idTipo_Comprobante,
                 DateTime fecha_Venta,
                 int cantidad_total,
                 double descuento_total,
                 double monto_total, String estado) {
        this.idVenta = idVenta;
        this.idEmpresa = idEmpresa;
        this.idEmpleado = idEmpleado;
        this.idTipo_Comprobante = idTipo_Comprobante;
        this.fecha_Venta = fecha_Venta;
        this.cantidad_total = cantidad_total;
        this.descuento_total = descuento_total;
        this.monto_total = monto_total;
        this.estado = estado;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdTipo_Comprobante() {
        return idTipo_Comprobante;
    }

    public void setIdTipo_Comprobante(int idTipo_Comprobante) {
        this.idTipo_Comprobante = idTipo_Comprobante;
    }

    public DateTime getFecha_Venta() {
        return fecha_Venta;
    }

    public void setFecha_Venta(DateTime fecha_Venta) {
        this.fecha_Venta = fecha_Venta;
    }

    public int getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(int cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    public double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getDescuento_total() {
        return descuento_total;
    }

    public void setDescuento_total(double descuento_total) {
        this.descuento_total = descuento_total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", idEmpresa=" + idEmpresa +
                ", idEmpleado=" + idEmpleado +
                ", idTipo_Comprobante=" + idTipo_Comprobante +
                ", fecha_Venta=" + fecha_Venta +
                ", cantidad_total=" + cantidad_total +
                ", descuento_total=" + descuento_total +
                ", monto_total=" + monto_total +
                ", estado='" + estado + '\'' +
                '}';
    }
}
