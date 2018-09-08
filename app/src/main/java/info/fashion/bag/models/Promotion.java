package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gtufinof on 3/30/18.
 */

public class Promotion implements Serializable{

    @Expose
    @SerializedName("id_promocion")
    private int id_promocion;

    @Expose
    @SerializedName("nombre")
    private String nombre;

    @Expose
    @SerializedName("descripcion")
    private String descripcion;

    @Expose
    @SerializedName("estado")
    private String estado;

    @Expose
    @SerializedName("precio")
    private float precio;

    @Expose
    @SerializedName("precio_fichas")
    private int precio_fichas;

    @Expose
    @SerializedName("producto")
    private List<Product> producto;

    @Expose
    @SerializedName("imagen")
    private String imagen;

    public int getId_promocion() {
        return id_promocion;
    }

    public void setId_promocion(int id_promocion) {
        this.id_promocion = id_promocion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public List<Product> getProducto() {
        return producto;
    }

    public void setProducto(List<Product> producto) {
        this.producto = producto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id_promocion=" + id_promocion +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", precio=" + precio +
                ", precio_fichas=" + precio_fichas +
                ", producto=" + producto +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}