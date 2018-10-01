package com.declaration.nandm.declarationapp.Domain;

import java.io.Serializable;

public class Authority implements Serializable {


    private String Name;

    public Authority(String name) {
        Name = name;
    }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }
}
