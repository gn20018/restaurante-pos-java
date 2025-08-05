package com.saborperuano.sistema.Models.DTO;

import com.saborperuano.sistema.Models.Enums.Categoria;
import com.saborperuano.sistema.Models.Enums.EstadoRegistro;

;

public class CartaDTO {

        //region [Atributos]
    private int Codigo;
    private String Nombre;
    private String Descripcion;
    private String TipoPlatillo;
    private Categoria Categoria;
    private float PrecioUnitario;
    private EstadoRegistro estado;


    //endregion

    //region [Métodos]


    public CartaDTO(int codigo,
                    String nombre,
                    String descripcion,
                    String tipoPlatillo,
                    com.saborperuano.sistema.Models.Enums.Categoria categoria,
                    float precioUnitario,
                    EstadoRegistro estado) {
        Codigo = codigo;
        Nombre = nombre;
        Descripcion = descripcion;
        TipoPlatillo = tipoPlatillo;
        Categoria = categoria;
        PrecioUnitario = precioUnitario;
        this.estado = estado;
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

    public String getTipoPlatillo() {
        return TipoPlatillo;
    }

    public void setTipoPlatillo(String tipoPlatillo) {
        TipoPlatillo = tipoPlatillo;
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

    public EstadoRegistro getEstado() {
        return estado;
    }

    public void setEstado(EstadoRegistro estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CartaDTO{" +
                "Codigo=" + Codigo +
                ", Nombre='" + Nombre + '\'' +
                ", estado=" + estado.toString() +
                '}';
    }

    //endregion
}
