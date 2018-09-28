package com.declaration.nandm.declarationapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.declaration.nandm.declarationapp.Data.Interfaces.IUserRepository;
import com.declaration.nandm.declarationapp.Data.Repositories.UserRepository;
import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mRoot;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoot = FirebaseDatabase.getInstance();
        
/*
        //Mock data for declarations
        DatabaseReference reference = mRoot.getReference("Declarations");


        for (int i =0; i<10;i++){
            Declaration dec = new Declaration();
            dec.setUserId("notNick");
            dec.setDescription(Integer.toString(i));
            dec.setPrice(i);
            reference.push().setValue(dec);
        }
*/

        getUser("nick");
        getDeclaration("nick");
        System.out.println(user);
    }

    private void getUser(String email){
        DatabaseReference userRef = mRoot.getReference("Users/" + email);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                System.out.println(user);
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
                    List<Declaration> currentList = user.getDeclaration();
                    for(DataSnapshot declaration: dataSnapshot.getChildren()){
                        currentList.add(declaration.getValue(Declaration.class));
                    }
                    user.setDeclaration(currentList);
                    System.out.println(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
