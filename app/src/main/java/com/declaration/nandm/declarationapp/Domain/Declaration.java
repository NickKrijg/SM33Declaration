package com.declaration.nandm.declarationapp.Domain;

import android.net.Uri;

import java.util.Date;

public class Declaration {

    private int price;
    private String title;
    private Uri receiptPhoto;
    private int amaout;
    private String description;
    private Authority authority;
    private State state;
    private Date date;
    private String userId;

    public String getDescription() {
        return description;
    }

    public String getTitle() {
    }

    public Declaration(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
