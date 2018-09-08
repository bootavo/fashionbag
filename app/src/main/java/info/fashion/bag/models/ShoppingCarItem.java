package info.fashion.bag.models;

import java.util.List;

public class ShoppingCarItem {

    private Integer id_carrito_compra_producto;
    private Integer id_carrito_compra;
    private Integer id_promocion;
    private Integer id_producto;
    private Integer cantidad;
    private float precio_total;
    private int fichas_total;
    private String tipo_compra;
    private List<Product> itemProduct;
    private List<Promotion> itemPromotion;

    public Integer getId_carrito_compra_producto() {
        return id_carrito_compra_producto;
    }

    public void setId_carrito_compra_producto(Integer id_carrito_compra_producto) {
        this.id_carrito_compra_producto = id_carrito_compra_producto;
    }

    public Integer getId_carrito_compra() {
        return id_carrito_compra;
    }

    public void setId_carrito_compra(Integer id_carrito_compra) {
        this.id_carrito_compra = id_carrito_compra;
    }

    public Integer getId_promocion() {
        return id_promocion;
    }

    public void setId_promocion(Integer id_promocion) {
        this.id_promocion = id_promocion;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public List<Product> getItemProduct() {
        return itemProduct;
    }

    public void setItemProduct(List<Product> itemProduct) {
        this.itemProduct = itemProduct;
    }

    public List<Promotion> getItemPromotion() {
        return itemPromotion;
    }

    public void setItemPromotion(List<Promotion> itemPromotion) {
        this.itemPromotion = itemPromotion;
    }

    public String getTipo_compra() {
        return tipo_compra;
    }

    public void setTipo_compra(String tipo_compra) {
        this.tipo_compra = tipo_compra;
    }

    public int getFichas_total() {
        return fichas_total;
    }

    public void setFichas_total(int fichas_total) {
        this.fichas_total = fichas_total;
    }

    @Override
    public String toString() {
        return "ShoppingCarItem{" +
                "id_carrito_compra_producto=" + id_carrito_compra_producto +
                ", id_carrito_compra=" + id_carrito_compra +
                ", id_promocion=" + id_promocion +
                ", id_producto=" + id_producto +
                ", cantidad=" + cantidad +
                ", precio_total=" + precio_total +
                ", fichas_total=" + fichas_total +
                ", tipo_compra='" + tipo_compra + '\'' +
                ", itemProduct=" + itemProduct +
                ", itemPromotion=" + itemPromotion +
                '}';
    }
}
