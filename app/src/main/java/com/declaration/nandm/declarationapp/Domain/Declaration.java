package com.declaration.nandm.declarationapp.Domain;

import android.net.Uri;

import java.util.Date;

public class Declaration {

    private Uri receiptPhoto;
    private int price;
    private String description;
    private Authority authority;
    private State state;
    private Date date;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Declaration(){}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
