package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/16/18.
 */

public class ProductPromotion {

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
    @SerializedName("start_amount")
    private float start_amount;

    @Expose
    @SerializedName("end_amount")
    private float end_amount;

    @Expose
    @SerializedName("is_active")
    private boolean is_active;

    @Expose
    @SerializedName("modified")
    private int variation;

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

    public float getStart_amount() {
        return start_amount;
    }

    public void setStart_amount(float start_amount) {
        this.start_amount = start_amount;
    }

    public float getEnd_amount() {
        return end_amount;
    }

    public void setEnd_amount(float end_amount) {
        this.end_amount = end_amount;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public int getVariation() {
        return variation;
    }

    public void setVariation(int variation) {
        this.variation = variation;
    }

    @Override
    public String toString() {
        return "ProductPromotion{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", start_amount=" + start_amount +
                ", end_amount=" + end_amount +
                ", is_active=" + is_active +
                ", variation=" + variation +
                '}';
    }

}
