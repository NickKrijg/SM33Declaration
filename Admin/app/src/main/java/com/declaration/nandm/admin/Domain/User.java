package com.declaration.nandm.admin.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

    private String key;
    private String name;
    private String email;
    private String iban;
    private List<Authority> authority = new ArrayList<>();
    private List<Declaration> declaration = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, String iban, List<Authority> authority) {
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.authority = authority;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
    }

    public List<Declaration> getDeclaration() {
        return declaration;
    }

    public void setDeclaration(List<Declaration> declaration) {
        this.declaration = declaration;
    }
}
