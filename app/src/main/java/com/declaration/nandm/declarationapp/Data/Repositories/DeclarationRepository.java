package com.declaration.nandm.declarationapp.Data.Repositories;

import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.State;
import com.declaration.nandm.declarationapp.Data.Interfaces.IDeclarationRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DeclarationRepository implements IDeclarationRepository {

    private FirebaseDatabase mRoot;
    private DatabaseReference mRef;

    public DeclarationRepository() {
        mRoot = FirebaseDatabase.getInstance();
        mRef = mRoot.getReference("Declaration");
    }

    @Override
    public boolean addDeclaration(Declaration declaration) {
        return false;
    }

    @Override
    public List<Declaration> getDeclarationsUser(int id) {
        return null;
    }
}
