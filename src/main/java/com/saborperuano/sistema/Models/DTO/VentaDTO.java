package com.saborperuano.sistema.Models.DTO;

import com.saborperuano.sistema.Models.Enums.TipoComprobante;
import org.joda.time.DateTime;

public class VentaDTO {

    //region [Atributos]
    private int nroVenta;
    private String empleado;
    private TipoComprobante tipoComprobante;
    private DateTime fecha_venta;
    private int cantidadProductos;
    private double descuento_total;
    private double montoTotal;
    private String  estado;
    //endregion

    //region [Métodos y Constructor]


    public VentaDTO(int nroVenta, String empleado,
                    TipoComprobante tipoComprobante, DateTime fecha_venta,
                    int cantidadProductos, double descuento_total,
                    double montoTotal, String estado) {
        this.nroVenta = nroVenta;
        this.empleado = empleado;
        this.tipoComprobante = tipoComprobante;
        this.fecha_venta = fecha_venta;
        this.cantidadProductos = cantidadProductos;
        this.descuento_total = descuento_total;
        this.montoTotal = montoTotal;
        this.estado = estado;
    }

    public int getNroVenta() {
        return nroVenta;
    }

    public void setNroVenta(int nroVenta) {
        this.nroVenta = nroVenta;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public DateTime getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(DateTime fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
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
        return "VentaDTO{" +
                "nroVenta=" + nroVenta +
                ", empleado='" + empleado + '\'' +
                ", tipoComprobante=" + tipoComprobante +
                ", fecha_venta=" + fecha_venta +
                ", cantidadProductos=" + cantidadProductos +
                ", descuento_total=" + descuento_total +
                ", montoTotal=" + montoTotal +
                ", estado='" + estado + '\'' +
                '}';
    }

    //endregion


}
