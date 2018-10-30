package com.declaration.nandm.admin.Data;

import com.declaration.nandm.admin.Domain.Authority;
import com.declaration.nandm.admin.Domain.Declaration;
import com.declaration.nandm.admin.Domain.User;
import com.declaration.nandm.admin.Layout.AllDeclarationsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataReceiver {

    FirebaseDatabase mRoot = FirebaseDatabase.getInstance();
    AllDeclarationsAdapter adapter;

    ArrayList<Authority> authorities;
    User user;
    ArrayList<Declaration> declarations;

    public User getUser() {
        return user;
    }

    public ArrayList<Authority> getAuthorities() {
        return authorities;
    }

    public ArrayList<Declaration> getDeclarations() {
        return declarations;
    }

    public DataReceiver(String email, AllDeclarationsAdapter adapter){
        this.adapter = adapter;
        user = new User();
        getDeclarationAuthority(email);
    }

    private void getUser(String email){
        DatabaseReference userRef = mRoot.getReference("Users/");
        Query query = userRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    user = snap.getValue(User.class);
                    user.setKey(snap.getKey());
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

    private void getAllAuthorities(){
        DatabaseReference ref = mRoot.getReference("Authorities");
        authorities = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    authorities.add(new Authority(snap.getValue(String.class)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDeclarationAuthority(final String authority){
        DatabaseReference ref = mRoot.getReference("Declarations");
        declarations = new ArrayList<>();
        Query query = ref.orderByChild("authority").equalTo(authority);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Declaration> currentList = new ArrayList<>();
                for(DataSnapshot declaration: dataSnapshot.getChildren()){
                    Declaration dec = declaration.getValue(Declaration.class);
                    dec.setKey(declaration.getKey());
                    currentList.add(dec);
                }
                declarations.clear();

                Collections.sort(currentList, new sortByStatus());

                declarations.addAll(currentList);

                adapter.setList(currentList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

 class sortByStatus implements Comparator<Declaration> {
     @Override
     public int compare(Declaration o1, Declaration o2) {
         return o1.getState().compareTo(o2.getState());
     }
 }
