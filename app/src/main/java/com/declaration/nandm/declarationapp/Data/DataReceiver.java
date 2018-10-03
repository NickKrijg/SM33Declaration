package com.declaration.nandm.declarationapp.Data;

import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.Layout.AllDeclarationsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataReceiver {

    FirebaseDatabase mRoot = FirebaseDatabase.getInstance();
    AllDeclarationsAdapter adapter;

    public User getUser() {
        return user;
    }

    User user;

    public DataReceiver(String email, AllDeclarationsAdapter adapter){
        this.adapter = adapter;
        user = new User();
        getUser(email);
        getDeclaration(email);
    }


    private void getUser(String email){
        DatabaseReference userRef = mRoot.getReference("Users/");
        Query query = userRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    user = snap.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDeclaration(String email){
        DatabaseReference decRef = mRoot.getReference("Declarations");
        Query query = decRef.orderByChild("userId").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && user != null){
                    List<Declaration> currentList = new ArrayList<>();
                    for(DataSnapshot declaration: dataSnapshot.getChildren()){
                        currentList.add(declaration.getValue(Declaration.class));
                    }
                    user.getDeclaration().clear();
                    user.setDeclaration(currentList);
                    adapter.setList(user.getDeclaration());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
