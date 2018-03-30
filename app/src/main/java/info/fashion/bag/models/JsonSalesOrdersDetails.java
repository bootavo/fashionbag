package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 26/03/18.
 */

public class JsonSalesOrdersDetails {

    @Expose
    @SerializedName("count")
    private int count;

    @Expose
    @SerializedName("next")
    private String next;

    @Expose
    @SerializedName("previous")
    private String previous;

    @Expose
    @SerializedName("results")
    private List<SalesOrdersDatails> results;

    @Expose
    @SerializedName("detail")
    private String detail;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<SalesOrdersDatails> getResults() {
        return results;
    }

    public void setResults(List<SalesOrdersDatails> results) {
        this.results = results;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "JsonSalesOrders{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                ", detail='" + detail + '\'' +
                '}';
    }
}
