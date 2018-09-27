package com.declaration.nandm.declarationapp.Domain;

import android.net.Uri;

public class Declaration {

    private String title;
    private Uri receiptPhoto;
    private int amaout;
    private String description;
    private Authority authority;
    private State state;

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
}
