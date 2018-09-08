package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gtufinof on 3/12/18.
 */

public class Product implements Serializable{

    @Expose
    @SerializedName("id_producto")
    private int id_producto;

    @Expose
    @SerializedName("id_categoria")
    private int id_categoria;

    @Expose
    @SerializedName("nombre")
    private String nombre;

    @Expose
    @SerializedName("descripcion")
    private String descripcion;

    @Expose
    @SerializedName("precio")
    private float precio;

    @Expose
    @SerializedName("precio_fichas")
    private int precio_fichas;

    @Expose
    @SerializedName("estado")
    private String estado;

    @Expose
    @SerializedName("stock")
    private int stock;

    @Expose
    @SerializedName("imagen")
    private String imagen;

    @Expose
    @SerializedName("categoria")
    private String categoria;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getPrecio_fichas() {
        return precio_fichas;
    }

    public void setPrecio_fichas(int precio_fichas) {
        this.precio_fichas = precio_fichas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_producto=" + id_producto +
                ", id_categoria=" + id_categoria +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", precio_fichas=" + precio_fichas +
                ", estado='" + estado + '\'' +
                ", stock=" + stock +
                ", imagen='" + imagen + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
