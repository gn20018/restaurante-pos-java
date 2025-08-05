package com.saborperuano.sistema.Models;

public class VentasEspera {
    private int nroVenta,cantidadProductos;
    private String nombreVenta;
    private float descuento,montoTotal;

    public VentasEspera(int nroVenta, int cantidadProductos, String nombreVenta, float descuento, float montoTotal) {
        this.nroVenta = nroVenta;
        this.cantidadProductos = cantidadProductos;
        this.nombreVenta = nombreVenta;
        this.descuento = descuento;
        this.montoTotal = montoTotal;
    }

    public int getNroVenta() {
        return nroVenta;
    }

    public void setNroVenta(int nroVenta) {
        this.nroVenta = nroVenta;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public String getNombreVenta() {
        return nombreVenta;
    }

    public void setNombreVenta(String nombreVenta) {
        this.nombreVenta = nombreVenta;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String toString() {
        return "VentasEspera{" +
                "nroVenta=" + nroVenta +
                ", cantidadProductos=" + cantidadProductos +
                ", nombreVenta='" + nombreVenta + '\'' +
                ", descuento=" + descuento +
                ", montoTotal=" + montoTotal +
                '}';
    }
}
