package com.declaration.nandm.declarationapp.Domain;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseCalls {

    static public void addDeclaration(Declaration declaration){
        FirebaseDatabase mRoot = FirebaseDatabase.getInstance();
        DatabaseReference ref = mRoot.getReference("Declarations");

        ref.setValue(declaration);
    }

}
