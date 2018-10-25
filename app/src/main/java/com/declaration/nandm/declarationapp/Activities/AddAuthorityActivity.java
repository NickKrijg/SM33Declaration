package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddAuthorityActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnAddAuth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_authority);

        spinner =(Spinner)findViewById(R.id.spinnerAddAuth);
        btnAddAuth = (Button)findViewById(R.id.btAddAuth);


        user = (User) getIntent().getSerializableExtra("user");

        //Load data into spinner
        List<Authority> authorities = new ArrayList<>();
        authorities = (List<Authority>)getIntent().getSerializableExtra("authorities");
        ArrayList<String> authorityStrings = new ArrayList<>();

        for(Authority auth:authorities){
            authorityStrings.add(auth.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, authorityStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Add selected item
        btnAddAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authority newItem = new Authority(spinner.getSelectedItem().toString());
                List<Authority> temp = user.getAuthority();
                temp.add(newItem);
                user.setAuthority(temp);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/"+user.getKey());
                ref.setValue(user);

                Intent i = new Intent();
                i.putExtra("user", user);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
