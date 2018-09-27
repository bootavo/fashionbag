package com.tarjetic.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShoppingCar {

    @Expose
    @SerializedName("id_carrito_compra")
    private Integer id_carrito_compra;

    @Expose
    @SerializedName("id_usuario")
    private Integer id_usuario;

    @Expose
    @SerializedName("estado")
    private String estado;

    public Integer getId_carrito_compra() {
        return id_carrito_compra;
    }

    public void setId_carrito_compra(Integer id_carrito_compra) {
        this.id_carrito_compra = id_carrito_compra;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "id_carrito_compra=" + id_carrito_compra +
                ", id_usuario=" + id_usuario +
                ", estado='" + estado + '\'' +
                '}';
    }
}
