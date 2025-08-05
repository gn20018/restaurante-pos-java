package com.saborperuano.sistema.Models.DTO;

public class DetalleVentaDTO {
    private int CodigoDetalleVenta;
    private int CodigoProducto;
    private String NombreProducto;
    private String categoria;
    private int Cantidad;
    private double PrecioUnitario;
    private double PrecioTotal;
    private double Descuento;
    private double ImporteAPagar;

    public DetalleVentaDTO(int codigoDetalleVenta,
                           int codigoProducto,
                           String nombreProducto,
                           String categoria,
                           int cantidad,
                           double precioUnitario,
                           double precioTotal,
                           double descuento,
                           double importeAPagar) {
        CodigoDetalleVenta = codigoDetalleVenta;
        CodigoProducto = codigoProducto;
        NombreProducto = nombreProducto;
        this.categoria = categoria;
        Cantidad = cantidad;
        PrecioUnitario = precioUnitario;
        PrecioTotal = precioTotal;
        Descuento = descuento;
        ImporteAPagar = importeAPagar;
    }

    public DetalleVentaDTO(int codigoProducto,
                           String nombreProducto,
                           String categoria,
                           int cantidad,
                           double precioUnitario,
                           double precioTotal,
                           double descuento,
                           double importeAPagar) {
        CodigoProducto = codigoProducto;
        NombreProducto = nombreProducto;
        this.categoria = categoria;
        Cantidad = cantidad;
        PrecioUnitario = precioUnitario;
        PrecioTotal = precioTotal;
        Descuento = descuento;
        ImporteAPagar = importeAPagar;
    }

    public int getCodigoDetalleVenta() {
        return CodigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(int codigoDetalleVenta) {
        CodigoDetalleVenta = codigoDetalleVenta;
    }

    public int getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        CodigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        NombreProducto = nombreProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        PrecioUnitario = precioUnitario;
    }

    public double getPrecioTotal() {
        return PrecioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        PrecioTotal = precioTotal;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double descuento) {
        Descuento = descuento;
    }

    public double getImporteAPagar() {
        return ImporteAPagar;
    }

    public void setImporteAPagar(double importeAPagar) {
        ImporteAPagar = importeAPagar;
    }

    @Override
    public String toString() {
        return "DetalleVentaDTO{" +
                "CodigoDetalleVenta=" + CodigoDetalleVenta +
                ", CodigoProducto=" + CodigoProducto +
                ", NombreProducto='" + NombreProducto + '\'' +
                ", categoria=" + categoria +
                ", Cantidad=" + Cantidad +
                ", PrecioUnitario=" + PrecioUnitario +
                ", PrecioTotal=" + PrecioTotal +
                ", Descuento=" + Descuento +
                ", ImporteAPagar=" + ImporteAPagar +
                '}';
    }
}
