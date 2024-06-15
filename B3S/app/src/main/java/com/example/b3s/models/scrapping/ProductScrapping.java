package com.example.b3s.models.scrapping;


import com.google.gson.annotations.SerializedName;

public class ProductScrapping {
    private String old;
    private String reduction;
    private String boutique;
    @SerializedName("new")
    private String newprice;
    private String image;
    private String name;
    private String link;

    public ProductScrapping() {
    }

    public ProductScrapping(String old, String reduction, String boutique, String newprice, String image, String name, String link) {
        this.old = old;
        this.reduction = reduction;
        this.boutique = boutique;
        this.newprice = newprice;
        this.image = image;
        this.name = name;
        this.link = link;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public String getBoutique() {
        return boutique;
    }

    public void setBoutique(String boutique) {
        this.boutique = boutique;
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
