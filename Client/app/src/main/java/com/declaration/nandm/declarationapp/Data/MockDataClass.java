package com.declaration.nandm.declarationapp.Data;

import java.util.ArrayList;

public class MockDataClass {

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    ArrayList<String> authorities;

    public MockDataClass(){
        authorities = new ArrayList<>();
        fillAuthorities();
    }

    private void fillAuthorities() {
        authorities.add("Belangenvereniging");
        authorities.add("Beroepsvereniging");
        authorities.add("Branchevereniging");
        authorities.add("Carnavalsvereniging");
        authorities.add("Consumentenvereniging");
        authorities.add("Foto- Film- en Diavereniging");
        authorities.add("Geboortestreekvereniging");
        authorities.add("Groepsvereniging");
        authorities.add("Hobbyvereniging");
        authorities.add("Humanistische vereniging");
        authorities.add("Vrijmetselaarsloge");
        authorities.add("Middenstandsvereniging");
        authorities.add("Muziekvereniging");
        authorities.add("Natuurvereniging");
        authorities.add("Ondernemersvereniging");
        authorities.add("Patiëntenvereniging");
        authorities.add("Personeelsvereniging");
        authorities.add("Pluimvee en konijnenvereniging");
        authorities.add("Politieke partij");
        authorities.add("Sportvereniging");
        authorities.add("Studentenvereniging");
        authorities.add("Studievereniging");
        authorities.add("Vakbond");
        authorities.add("Vogelvereniging");    }
}
