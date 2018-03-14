package info.fashion.bag.apis;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bootavo on 26/08/2017.
 */

public class ApiRetrofitError {

    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("endpoint")
    private String endpoint;
    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private String details;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ApiRetrofitError{" +
                "statusCode=" + statusCode +
                ", endpoint='" + endpoint + '\'' +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
