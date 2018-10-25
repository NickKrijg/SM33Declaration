package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
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
    private SearchView searchView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_authority);

        searchView = (SearchView)findViewById(R.id.searchAddAuth);
        spinner =(Spinner)findViewById(R.id.spinnerAddAuth);
        btnAddAuth = (Button)findViewById(R.id.btAddAuth);


        user = (User) getIntent().getSerializableExtra("user");

        //Load data into spinner
        List<Authority> authorities = new ArrayList<>();
        authorities = (List<Authority>)getIntent().getSerializableExtra("authorities");
        ArrayList<String> authorityStrings = new ArrayList<>();

        for(Authority auth:authorities){
            boolean temp = true;
            for (Authority auth2:user.getAuthority()){
                if (auth2.getName().equals(auth.getName())){
                    temp = false;
                }
            }
            if (temp){
                authorityStrings.add(auth.getName());
            }
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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/" + user.getKey());
                ref.setValue(user);

                Intent i = new Intent();
                i.putExtra("user", user);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        //Search updates
        final ArrayList<String> temp = authorityStrings;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> result = new ArrayList<>();
                for (String str : temp){
                    if (str.contains(query)){
                        result.add(str);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        AddAuthorityActivity.this, android.R.layout.simple_spinner_item, result);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> result = new ArrayList<>();
                for (String str : temp){
                    if (str.toLowerCase().contains(newText.toLowerCase())){
                        result.add(str);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        AddAuthorityActivity.this, android.R.layout.simple_spinner_item, result);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);


                return false;
            }
        });
    }
}
