package info.fashion.bag.models;

import info.fashion.bag.models.Products;

import java.util.List;

/**
 * Created by gtufinof on 3/12/18.
 */

public class JsonProducts {

    private int count;
    private String next;
    private String previous;
    private List<Products> results;

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

    public List<Products> getResults() {
        return results;
    }

    public void setResults(List<Products> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "JsonProducts{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}