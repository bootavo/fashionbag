package com.tarjetic.store.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gtufinof on 3/12/18.
 */

public class JsonRequest {

    @Expose
    @SerializedName("status")
    private Status status;

    @Expose
    @SerializedName("results")
    private Results results;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "JsonRequest{" +
                "status=" + status +
                ", results=" + results +
                '}';
    }
}
