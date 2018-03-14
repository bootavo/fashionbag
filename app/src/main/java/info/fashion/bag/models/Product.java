package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/12/18.
 */

public class Product {

    @Expose
    @SerializedName("product_type")
    private ProductType product_type;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("only_price")
    private float only_price;

    @Expose
    @SerializedName("business_unit")
    private String business_unit;

    @Expose
    @SerializedName("sale_price")
    private float sale_price;

    @Expose
    @SerializedName("suggested_price")
    private float suggested_price;

    @Expose
    @SerializedName("brand")
    private Brand brand;

    public ProductType getProduct_type() {
        return product_type;
    }

    public void setProduct_type(ProductType product_type) {
        this.product_type = product_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOnly_price() {
        return only_price;
    }

    public void setOnly_price(float only_price) {
        this.only_price = only_price;
    }

    public String getBusiness_unit() {
        return business_unit;
    }

    public void setBusiness_unit(String business_unit) {
        this.business_unit = business_unit;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public float getSuggested_price() {
        return suggested_price;
    }

    public void setSuggested_price(float suggested_price) {
        this.suggested_price = suggested_price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_type=" + product_type +
                ", name='" + name + '\'' +
                ", only_price=" + only_price +
                ", business_unit='" + business_unit + '\'' +
                ", sale_price=" + sale_price +
                ", suggested_price=" + suggested_price +
                ", brand=" + brand +
                '}';
    }
}
