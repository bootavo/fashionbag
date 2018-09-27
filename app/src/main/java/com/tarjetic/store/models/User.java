package com.tarjetic.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/12/18.
 */

public class User {

    @Expose
    @SerializedName("id_persona")
    private int id_persona;

    @Expose
    @SerializedName("id_usuario")
    private int id_usuario;

    @Expose
    @SerializedName("nombres")
    private String nombres;

    @Expose
    @SerializedName("apellidos")
    private String apellidos;

    @Expose
    @SerializedName("direccion")
    private String direccion;

    @Expose
    @SerializedName("dni")
    private String dni;

    @Expose
    @SerializedName("correo")
    private String correo;

    @Expose
    @SerializedName("telefono_contacto")
    private String telefono_contacto;

    @Expose
    @SerializedName("usuario")
    private String usuario;

    @Expose
    @SerializedName("clave")
    private String clave;

    @Expose
    @SerializedName("estado")
    private String estado;

    @Expose
    @SerializedName("pedidos_cancelados")
    private int pedidos_cancelados;

    @Expose
    @SerializedName("id_rol")
    private int id_rol;

    @Expose
    @SerializedName("total_fichas")
    private int total_fichas;

    @Expose
    @SerializedName("imagen")
    private String imagen;

    @Expose
    @SerializedName("codigo_app")
    private String codigo_app;

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPedidos_cancelados() {
        return pedidos_cancelados;
    }

    public void setPedidos_cancelados(int pedidos_cancelados) {
        this.pedidos_cancelados = pedidos_cancelados;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public int getTotal_fichas() {
        return total_fichas;
    }

    public void setTotal_fichas(int total_fichas) {
        this.total_fichas = total_fichas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCodigo_app() {
        return codigo_app;
    }

    public void setCodigo_app(String codigo_app) {
        this.codigo_app = codigo_app;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_persona=" + id_persona +
                ", id_usuario=" + id_usuario +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", dni='" + dni + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono_contacto='" + telefono_contacto + '\'' +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", estado='" + estado + '\'' +
                ", pedidos_cancelados=" + pedidos_cancelados +
                ", id_rol=" + id_rol +
                ", total_fichas=" + total_fichas +
                ", imagen='" + imagen + '\'' +
                ", codigo_app='" + codigo_app + '\'' +
                '}';
    }
}
