package com.tarjetic.store.models;

public class Order {

    private Integer id_pedido;
    private Integer cantidad;
    private String tipo_pago;
    private float pago_efectivo;
    private Integer pago_fichas;
    private String direccion;
    private String telefono_contacto;
    private String fecha;
    private String estado;
    private float monto_total;
    private Integer id_usuario;
    private Integer id_carrito_compra;

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public float getPago_efectivo() {
        return pago_efectivo;
    }

    public void setPago_efectivo(float pago_efectivo) {
        this.pago_efectivo = pago_efectivo;
    }

    public Integer getPago_fichas() {
        return pago_fichas;
    }

    public void setPago_fichas(Integer pago_fichas) {
        this.pago_fichas = pago_fichas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(float monto_total) {
        this.monto_total = monto_total;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_carrito_compra() {
        return id_carrito_compra;
    }

    public void setId_carrito_compra(Integer id_carrito_compra) {
        this.id_carrito_compra = id_carrito_compra;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id_pedido=" + id_pedido +
                ", cantidad=" + cantidad +
                ", tipo_pago='" + tipo_pago + '\'' +
                ", pago_efectivo=" + pago_efectivo +
                ", pago_fichas=" + pago_fichas +
                ", direccion='" + direccion + '\'' +
                ", telefono_contacto='" + telefono_contacto + '\'' +
                ", fecha='" + fecha + '\'' +
                ", estado='" + estado + '\'' +
                ", monto_total=" + monto_total +
                ", id_usuario=" + id_usuario +
                ", id_carrito_compra=" + id_carrito_compra +
                '}';
    }
}
