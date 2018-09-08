package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @Expose
    @SerializedName("code")
    private int code;

    @Expose
    @SerializedName("detail")
    private String detail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", detail='" + detail + '\'' +
                '}';
    }
}
