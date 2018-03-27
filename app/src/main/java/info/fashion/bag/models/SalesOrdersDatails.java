package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/26/18.
 */

public class SalesOrdersDatails {

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
    @SerializedName("is_promotion")
    private boolean is_promotion;

    @Expose
    @SerializedName("weight")
    private float weight;

    @Expose
    @SerializedName("position")
    private int position;

    @Expose
    @SerializedName("units")
    private int units;

    @Expose
    @SerializedName("real_price")
    private float real_price;

    @Expose
    @SerializedName("g_send")
    private String g_send;

    @Expose
    @SerializedName("fbp_price")
    private float fbp_price;

    @Expose
    @SerializedName("sale_price")
    private float sale_price;

    @Expose
    @SerializedName("total_price")
    private float total_price;

    @Expose
    @SerializedName("sales_order")
    private int sales_order;

    @Expose
    @SerializedName("variant")
    private int variant;

    @Expose
    @SerializedName("detail")
    private String detail;

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

    public boolean isIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(boolean is_promotion) {
        this.is_promotion = is_promotion;
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

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public float getReal_price() {
        return real_price;
    }

    public void setReal_price(float real_price) {
        this.real_price = real_price;
    }

    public String getG_send() {
        return g_send;
    }

    public void setG_send(String g_send) {
        this.g_send = g_send;
    }

    public float getFbp_price() {
        return fbp_price;
    }

    public void setFbp_price(float fbp_price) {
        this.fbp_price = fbp_price;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getSales_order() {
        return sales_order;
    }

    public void setSales_order(int sales_order) {
        this.sales_order = sales_order;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "SalesOrdersDatails{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", is_promotion=" + is_promotion +
                ", weight=" + weight +
                ", position=" + position +
                ", units=" + units +
                ", real_price=" + real_price +
                ", g_send='" + g_send + '\'' +
                ", fbp_price=" + fbp_price +
                ", sale_price=" + sale_price +
                ", total_price=" + total_price +
                ", sales_order=" + sales_order +
                ", variant=" + variant +
                ", detail='" + detail + '\'' +
                '}';
    }
}
