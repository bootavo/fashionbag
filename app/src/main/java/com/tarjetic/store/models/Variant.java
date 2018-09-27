package com.tarjetic.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import butterknife.OnItemSelected;

/**
 * Created by gtufinof on 3/21/18.
 */

public class Variant {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("image_url")
    private String image_url;

    @Expose
    @SerializedName("thumbnail_url")
    private String thumbnail_url;

    @Expose
    @SerializedName("is_offer")
    private boolean is_offer;

    @Expose
    @SerializedName("created")
    private String created;

    @Expose
    @SerializedName("modified")
    private String modified;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("is_active")
    private boolean is_active;

    @Expose
    @SerializedName("stock_on_hand")
    private String stock_on_hand;

    @Expose
    @SerializedName("commited_stock")
    private String commited_stock;

    @Expose
    @SerializedName("incomming_stock")
    private String incomming_stock;

    @Expose
    @SerializedName("exhibition_stock")
    private String exhibition_stock;

    @Expose
    @SerializedName("failed_stock")
    private String failed_stock;

    @Expose
    @SerializedName("sku")
    private String sku;

    @Expose
    @SerializedName("weight")
    private float weight;

    @Expose
    @SerializedName("position")
    private int position;

    @Expose
    @SerializedName("fbp_price")
    private String fbp_price;

    @Expose
    @SerializedName("initial_stock_level")
    private String initial_stock_level;

    @Expose
    @SerializedName("product")
    private int product;

    @Expose
    @SerializedName("color")
    private int color;

    @Expose
    @SerializedName("image")
    private int image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public boolean isIs_offer() {
        return is_offer;
    }

    public void setIs_offer(boolean is_offer) {
        this.is_offer = is_offer;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getStock_on_hand() {
        return stock_on_hand;
    }

    public void setStock_on_hand(String stock_on_hand) {
        this.stock_on_hand = stock_on_hand;
    }

    public String getCommited_stock() {
        return commited_stock;
    }

    public void setCommited_stock(String commited_stock) {
        this.commited_stock = commited_stock;
    }

    public String getIncomming_stock() {
        return incomming_stock;
    }

    public void setIncomming_stock(String incomming_stock) {
        this.incomming_stock = incomming_stock;
    }

    public String getExhibition_stock() {
        return exhibition_stock;
    }

    public void setExhibition_stock(String exhibition_stock) {
        this.exhibition_stock = exhibition_stock;
    }

    public String getFailed_stock() {
        return failed_stock;
    }

    public void setFailed_stock(String failed_stock) {
        this.failed_stock = failed_stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFbp_price() {
        return fbp_price;
    }

    public void setFbp_price(String fbp_price) {
        this.fbp_price = fbp_price;
    }

    public String getInitial_stock_level() {
        return initial_stock_level;
    }

    public void setInitial_stock_level(String initial_stock_level) {
        this.initial_stock_level = initial_stock_level;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", image_url='" + image_url + '\'' +
                ", thumbnail_url='" + thumbnail_url + '\'' +
                ", is_offer=" + is_offer +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", name='" + name + '\'' +
                ", is_active=" + is_active +
                ", stock_on_hand='" + stock_on_hand + '\'' +
                ", commited_stock='" + commited_stock + '\'' +
                ", incomming_stock='" + incomming_stock + '\'' +
                ", exhibition_stock='" + exhibition_stock + '\'' +
                ", failed_stock='" + failed_stock + '\'' +
                ", sku='" + sku + '\'' +
                ", weight=" + weight +
                ", position=" + position +
                ", fbp_price='" + fbp_price + '\'' +
                ", initial_stock_level='" + initial_stock_level + '\'' +
                ", product=" + product +
                ", color=" + color +
                ", image=" + image +
                '}';
    }
}
