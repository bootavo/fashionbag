package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/16/18.
 */

public class BusinessRules {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("price_shipping_offer")
    private float price_shipping_offer;

    @Expose
    @SerializedName("director_mount")
    private float director_mount;

    @Expose
    @SerializedName("price_shipping_lima_urbana")
    private float price_shipping_lima_urbana;

    @Expose
    @SerializedName("price_shipping_lima_periferia")
    private float price_shipping_lima_periferia;

    @Expose
    @SerializedName("price_shipping_lima_alejada")
    private float price_shipping_lima_alejada;

    @Expose
    @SerializedName("price_courier_national_kilo")
    private float price_courier_national_kilo;

    @Expose
    @SerializedName("price_courier_national_each_kilo")
    private float price_courier_national_each_kilo;

    @Expose
    @SerializedName("price_courier_metropolitana_kilo")
    private float price_courier_metropolitana_kilo;

    @Expose
    @SerializedName("price_courier_metropolitana_each_kilo")
    private float price_courier_metropolitana_each_kilo;

    @Expose
    @SerializedName("price_courier_periferico_kilo")
    private float price_courier_periferico_kilo;

    @Expose
    @SerializedName("price_courier_periferico_each_kilo")
    private float price_courier_periferico_each_kilo;

    @Expose
    @SerializedName("price_shipping_pickup")
    private float price_shipping_pickup;

    @Expose
    @SerializedName("price_shipping_agencia")
    private float price_shipping_agencia;

    @Expose
    @SerializedName("initial_checkout")
    private float initial_checkout;

    @Expose
    @SerializedName("porcentaje_pos")
    private float porcentaje_pos;

    @Expose
    @SerializedName("monto_agencia_bancaria")
    private float monto_agencia_bancaria;

    @Expose
    @SerializedName("can_buy")
    private boolean can_buy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice_shipping_offer() {
        return price_shipping_offer;
    }

    public void setPrice_shipping_offer(float price_shipping_offer) {
        this.price_shipping_offer = price_shipping_offer;
    }

    public float getDirector_mount() {
        return director_mount;
    }

    public void setDirector_mount(float director_mount) {
        this.director_mount = director_mount;
    }

    public float getPrice_shipping_lima_urbana() {
        return price_shipping_lima_urbana;
    }

    public void setPrice_shipping_lima_urbana(float price_shipping_lima_urbana) {
        this.price_shipping_lima_urbana = price_shipping_lima_urbana;
    }

    public float getPrice_shipping_lima_periferia() {
        return price_shipping_lima_periferia;
    }

    public void setPrice_shipping_lima_periferia(float price_shipping_lima_periferia) {
        this.price_shipping_lima_periferia = price_shipping_lima_periferia;
    }

    public float getPrice_shipping_lima_alejada() {
        return price_shipping_lima_alejada;
    }

    public void setPrice_shipping_lima_alejada(float price_shipping_lima_alejada) {
        this.price_shipping_lima_alejada = price_shipping_lima_alejada;
    }

    public float getPrice_courier_national_kilo() {
        return price_courier_national_kilo;
    }

    public void setPrice_courier_national_kilo(float price_courier_national_kilo) {
        this.price_courier_national_kilo = price_courier_national_kilo;
    }

    public float getPrice_courier_national_each_kilo() {
        return price_courier_national_each_kilo;
    }

    public void setPrice_courier_national_each_kilo(float price_courier_national_each_kilo) {
        this.price_courier_national_each_kilo = price_courier_national_each_kilo;
    }

    public float getPrice_courier_metropolitana_kilo() {
        return price_courier_metropolitana_kilo;
    }

    public void setPrice_courier_metropolitana_kilo(float price_courier_metropolitana_kilo) {
        this.price_courier_metropolitana_kilo = price_courier_metropolitana_kilo;
    }

    public float getPrice_courier_metropolitana_each_kilo() {
        return price_courier_metropolitana_each_kilo;
    }

    public void setPrice_courier_metropolitana_each_kilo(float price_courier_metropolitana_each_kilo) {
        this.price_courier_metropolitana_each_kilo = price_courier_metropolitana_each_kilo;
    }

    public float getPrice_courier_periferico_kilo() {
        return price_courier_periferico_kilo;
    }

    public void setPrice_courier_periferico_kilo(float price_courier_periferico_kilo) {
        this.price_courier_periferico_kilo = price_courier_periferico_kilo;
    }

    public float getPrice_courier_periferico_each_kilo() {
        return price_courier_periferico_each_kilo;
    }

    public void setPrice_courier_periferico_each_kilo(float price_courier_periferico_each_kilo) {
        this.price_courier_periferico_each_kilo = price_courier_periferico_each_kilo;
    }

    public float getPrice_shipping_pickup() {
        return price_shipping_pickup;
    }

    public void setPrice_shipping_pickup(float price_shipping_pickup) {
        this.price_shipping_pickup = price_shipping_pickup;
    }

    public float getPrice_shipping_agencia() {
        return price_shipping_agencia;
    }

    public void setPrice_shipping_agencia(float price_shipping_agencia) {
        this.price_shipping_agencia = price_shipping_agencia;
    }

    public float getInitial_checkout() {
        return initial_checkout;
    }

    public void setInitial_checkout(float initial_checkout) {
        this.initial_checkout = initial_checkout;
    }

    public float getPorcentaje_pos() {
        return porcentaje_pos;
    }

    public void setPorcentaje_pos(float porcentaje_pos) {
        this.porcentaje_pos = porcentaje_pos;
    }

    public float getMonto_agencia_bancaria() {
        return monto_agencia_bancaria;
    }

    public void setMonto_agencia_bancaria(float monto_agencia_bancaria) {
        this.monto_agencia_bancaria = monto_agencia_bancaria;
    }

    public boolean isCan_buy() {
        return can_buy;
    }

    public void setCan_buy(boolean can_buy) {
        this.can_buy = can_buy;
    }

    @Override
    public String toString() {
        return "BusinessRules{" +
                "id=" + id +
                ", price_shipping_offer=" + price_shipping_offer +
                ", director_mount=" + director_mount +
                ", price_shipping_lima_urbana=" + price_shipping_lima_urbana +
                ", price_shipping_lima_periferia=" + price_shipping_lima_periferia +
                ", price_shipping_lima_alejada=" + price_shipping_lima_alejada +
                ", price_courier_national_kilo=" + price_courier_national_kilo +
                ", price_courier_national_each_kilo=" + price_courier_national_each_kilo +
                ", price_courier_metropolitana_kilo=" + price_courier_metropolitana_kilo +
                ", price_courier_metropolitana_each_kilo=" + price_courier_metropolitana_each_kilo +
                ", price_courier_periferico_kilo=" + price_courier_periferico_kilo +
                ", price_courier_periferico_each_kilo=" + price_courier_periferico_each_kilo +
                ", price_shipping_pickup=" + price_shipping_pickup +
                ", price_shipping_agencia=" + price_shipping_agencia +
                ", initial_checkout=" + initial_checkout +
                ", porcentaje_pos=" + porcentaje_pos +
                ", monto_agencia_bancaria=" + monto_agencia_bancaria +
                ", can_buy=" + can_buy +
                '}';
    }
}
