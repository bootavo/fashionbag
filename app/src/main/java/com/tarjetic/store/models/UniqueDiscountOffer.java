package com.tarjetic.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/16/18.
 */

public class UniqueDiscountOffer {

    @Expose
    @SerializedName("id")
    private int id;

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
    @SerializedName("start_datetime")
    private String start_datetime;

    @Expose
    @SerializedName("end_datetime")
    private String end_datetime;

    @Expose
    @SerializedName("discount_percent")
    private float discount_percent;

    @Expose
    @SerializedName("discount_amount")
    private float discount_amount;

    @Expose
    @SerializedName("active_percent")
    private boolean active_percent;

    @Expose
    @SerializedName("active_amount")
    private boolean active_amount;

    @Expose
    @SerializedName("categories")
    private String categories;

    @Expose
    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public float getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(float discount_percent) {
        this.discount_percent = discount_percent;
    }

    public float getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(float discount_amount) {
        this.discount_amount = discount_amount;
    }

    public boolean isActive_percent() {
        return active_percent;
    }

    public void setActive_percent(boolean active_percent) {
        this.active_percent = active_percent;
    }

    public boolean isActive_amount() {
        return active_amount;
    }

    public void setActive_amount(boolean active_amount) {
        this.active_amount = active_amount;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UniqueDiscountOffer{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", name='" + name + '\'' +
                ", is_active=" + is_active +
                ", start_datetime='" + start_datetime + '\'' +
                ", end_datetime='" + end_datetime + '\'' +
                ", discount_percent=" + discount_percent +
                ", discount_amount=" + discount_amount +
                ", active_percent=" + active_percent +
                ", active_amount=" + active_amount +
                ", categories='" + categories + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
