package com.declaration.nandm.declarationapp.Domain;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class Declaration implements Serializable{

    private double price;
    private String title;
    private String receiptPhoto;
    private String description;
    private Authority authority;
    private State state;
    private Date date;
    private String userId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReceiptPhoto(String receiptPhoto) {
        this.receiptPhoto = receiptPhoto;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getReceiptPhoto() {
        return receiptPhoto;
    }

    public State getState() {
        return state;
    }

    public Date getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthority() {
        return authority.getName();
    }

    public void setAuthority(String name) {
        this.authority = new Authority(name);
    }

    public Declaration() {

    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Declaration(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
