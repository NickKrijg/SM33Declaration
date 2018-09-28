package com.declaration.nandm.declarationapp.Data.Repositories;

import com.declaration.nandm.declarationapp.Data.Interfaces.IAuthorityRepository;
import com.declaration.nandm.declarationapp.Domain.Authority;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AuthorityRepository implements IAuthorityRepository {

    private FirebaseDatabase mRoot;
    private DatabaseReference mRef;

    public AuthorityRepository() {
        mRoot = FirebaseDatabase.getInstance();
        mRef = mRoot.getReference("Authority");
    }

    @Override
    public List<Authority> getAuthorities() {
        return null;
    }
}
