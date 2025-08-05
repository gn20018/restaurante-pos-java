
package com.saborperuano.sistema.Models;

public class Insumo {
    private int idInsumo;
    private String idCategoria_insumo;
    private String nombre;
    private float stock_actual;
    private int stock_minimo;
    private String estado;


    public Insumo(int idInsumo,
                  String idCategoria_insumo,
                  String nombre,
                  float stock_actual,
                  int stock_minimo, String estado) {
        this.idInsumo = idInsumo;
        this.idCategoria_insumo = idCategoria_insumo;
        this.nombre = nombre;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.estado = estado;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdCategoria_insumo() {
        return idCategoria_insumo;
    }

    public void setIdCategoria_insumo(String idCategoria_insumo) {
        this.idCategoria_insumo = idCategoria_insumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(float stock_actual) {
        this.stock_actual = stock_actual;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Insumo{" +
                "idInsumo=" + idInsumo +
                ", idCategoria_insumo='" + idCategoria_insumo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", stock_actual=" + stock_actual +
                ", stock_minimo=" + stock_minimo +
                ", estado='" + estado + '\'' +
                '}';
    }
}
