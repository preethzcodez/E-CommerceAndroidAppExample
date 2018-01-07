
package com.preethzcodez.ecommerceexample.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("variants")
    @Expose
    private List<Variant> variants = null;
    @SerializedName("tax")
    @Expose
    private Tax tax;
    @SerializedName("price_range")
    @Expose
    private String price_range;

    private Boolean isShortlisted = false;

    public Boolean getShortlisted() {
        return isShortlisted;
    }

    public void setShortlisted(Boolean shortlisted) {
        isShortlisted = shortlisted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }
}
