package com.saborperuano.sistema.Models;

public class DetalleVenta {

    int codigoDetalleVenta, codigoVenta, codigoMenu,codigoCarta, cantidad;
    double precioUnitario,descuento, precioTotal, importePagar;


    public DetalleVenta(int codigoDetalleVenta, int codigoVenta,
                        int codigoMenu, int codigoCarta,
                        int cantidad, double precioUnitario,
                         double precioTotal,double descuento,
                        double importePagar) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.codigoVenta = codigoVenta;
        this.codigoMenu = codigoMenu;
        this.codigoCarta = codigoCarta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.precioTotal = precioTotal;
        this.importePagar = importePagar;
    }

    public DetalleVenta(int codigoVenta, int codigoMenu,
                        int codigoCarta,
                        int cantidad, double precioUnitario,
                        double precioTotal,double descuento,
                        double importePagar) {
        this.codigoVenta = codigoVenta;
        this.codigoMenu = codigoMenu;
        this.codigoCarta = codigoCarta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.precioTotal = precioTotal;
        this.importePagar = importePagar;
    }

    public int getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(int codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public int getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(int codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public int getCodigoMenu() {
        return codigoMenu;
    }

    public void setCodigoMenu(int codigoMenu) {
        this.codigoMenu = codigoMenu;
    }

    public int getCodigoCarta() {
        return codigoCarta;
    }

    public void setCodigoCarta(int codigoCarta) {
        this.codigoCarta = codigoCarta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public double getImportePagar() {
        return importePagar;
    }

    public void setImportePagar(double importePagar) {
        this.importePagar = importePagar;
    }


    @Override
    public String toString() {
        return "DetalleVenta{" +
                "codigoDetalleVenta=" + codigoDetalleVenta +
                ", codigoVenta=" + codigoVenta +
                ", codigoMenu=" + codigoMenu +
                ", codigoCarta=" + codigoCarta +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", descuento=" + descuento +
                ", precioTotal=" + precioTotal +
                ", importePagar=" + importePagar +
                '}';
    }
}
