package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 26/03/18.
 */

public class SalesOrders {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("user")
    private int user;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("director_mount")
    private float director_mount;

    @Expose
    @SerializedName("detail")
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getDirector_mount() {
        return director_mount;
    }

    public void setDirector_mount(float director_mount) {
        this.director_mount = director_mount;
    }

    @Override
    public String toString() {
        return "SalesOrders{" +
                "id=" + id +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", director_mount=" + director_mount +
                ", detail='" + detail + '\'' +
                '}';
    }
}
