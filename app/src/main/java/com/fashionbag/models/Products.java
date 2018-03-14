package com.fashionbag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/12/18.
 */

public class Products {

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
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("sku")
    private String sku;

    @Expose
    @SerializedName("product")
    private Product product;

    @Expose
    @SerializedName("color")
    private String color;

    @Expose
    @SerializedName("created")
    private String created;

    @Expose
    @SerializedName("category")
    private Category category;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", image_url='" + image_url + '\'' +
                ", thumbnail_url='" + thumbnail_url + '\'' +
                ", is_offer=" + is_offer +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", product=" + product +
                ", color='" + color + '\'' +
                ", created='" + created + '\'' +
                ", category=" + category +
                '}';
    }
}
