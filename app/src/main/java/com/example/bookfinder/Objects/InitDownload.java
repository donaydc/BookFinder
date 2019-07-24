package com.example.bookfinder.Objects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitDownload {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("books")
    @Expose
    private List<BookObject> books = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BookObject> getBooks() {
        return books;
    }

    public void setBooks(List<BookObject> books) {
        this.books = books;
    }

}
