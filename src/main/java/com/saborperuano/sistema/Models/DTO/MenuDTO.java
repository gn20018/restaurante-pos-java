package com.saborperuano.sistema.Models.DTO;

import com.saborperuano.sistema.Models.Enums.Categoria;

;

public class MenuDTO {

    //region [Atributos]
    private int Codigo;
    private String Nombre;
    private String Descripcion;
    private Categoria Categoria;
    private float PrecioUnitario;
    private float Descuento;


    //endregion

    //region [Métodos]

    public MenuDTO(int codigo,
                   String nombre,
                   String descripcion,
                   com.saborperuano.sistema.Models.Enums.Categoria categoria,
                   float precioUnitario, float descuento) {
        Codigo = codigo;
        Nombre = nombre;
        Descripcion = descripcion;
        Categoria = categoria;
        PrecioUnitario = precioUnitario;
        Descuento = descuento;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public com.saborperuano.sistema.Models.Enums.Categoria getCategoria() {
        return Categoria;
    }

    public void setCategoria(com.saborperuano.sistema.Models.Enums.Categoria categoria) {
        Categoria = categoria;
    }

    public float getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        PrecioUnitario = precioUnitario;
    }

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float descuento) {
        Descuento = descuento;
    }

    //endregion
}
