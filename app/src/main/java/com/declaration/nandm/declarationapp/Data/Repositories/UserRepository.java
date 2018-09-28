package com.declaration.nandm.declarationapp.Data.Repositories;

import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.Data.Interfaces.IUserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRepository implements IUserRepository {

    private FirebaseDatabase mRoot;
    private DatabaseReference mRef;

    public UserRepository() {
        mRoot = FirebaseDatabase.getInstance();
        mRef = mRoot.getReference("Users");
    }

    @Override
    public User getUser(String email) {
        mRef.child("nick").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean editUser(User user) {
        return false;
    }
}
