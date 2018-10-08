package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.State;
import com.declaration.nandm.declarationapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class CheckDeclarationActivity extends AppCompatActivity {

    Declaration declaration;

    ImageView imageView;
    TextView txtPrice;
    TextView txtDescription;
    TextView txtAuthority;
    Button btnSend;
    Button btnEdit;

    Uri contentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_declaration);

        imageView = (ImageView)findViewById(R.id.imageView);
        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtAuthority = (TextView)findViewById(R.id.txtAuthority);
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        btnSend = (Button)findViewById(R.id.btSend);
        btnEdit = (Button)findViewById(R.id.btEdit);


        declaration = (Declaration) getIntent().getSerializableExtra("declaration");
        if(declaration != null){
            declaration.setState(State.Pending);
            fillFields();
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDeclaration();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void sendDeclaration() {
        final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Declarations");
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();


        if (contentUri != null){
            declaration.setReceiptPhoto(contentUri.getLastPathSegment());

            mStorageRef = mStorageRef.child("photos").child(declaration.getReceiptPhoto());

            mStorageRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDatabaseRef.push().setValue(declaration);

                    Toast.makeText(CheckDeclarationActivity.this,"Upload success", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    private void fillFields() {
        String price = "€" + String.valueOf(declaration.getPrice());
        txtPrice.setText(price);
        txtAuthority.setText(declaration.getAuthority());
        txtDescription.setText(declaration.getDescription());

        File f = new File(declaration.getReceiptPhoto());
        contentUri = Uri.fromFile(f);
        Glide.with(this).load(contentUri).apply(new RequestOptions().centerCrop()).into(imageView);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
