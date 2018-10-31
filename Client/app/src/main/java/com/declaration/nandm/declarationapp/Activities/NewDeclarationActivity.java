package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewDeclarationActivity extends AppCompatActivity {

    private ImageView imageView;
    private FloatingActionButton fab;
    private EditText editPrice;
    private EditText editDescription;
    private Spinner spinnerAuthority;

    private Declaration declaration;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_declaration);

        User user = (User)getIntent().getSerializableExtra("user");

        declaration = new Declaration();
        declaration.setUserId(user.getEmail());

        editDescription = (EditText)findViewById(R.id.editDescription);
        spinnerAuthority = (Spinner) findViewById(R.id.spinnerAuthority);
        fab = (FloatingActionButton)findViewById(R.id.fabSend);
        imageView = (ImageView)findViewById(R.id.imageView);

        editPrice = (EditText)findViewById(R.id.editPrice);

        ArrayList<String> authorities = new ArrayList<>();

        for (Authority item:user.getAuthority()){
            authorities.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, authorities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthority.setAdapter(adapter);

        spinnerAuthority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dispatchPhotoIntent();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPhotoIntent();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDeclaration();
            }
        });
    }

    private void sendDeclaration() {
        boolean allFieldsFilled = true;

        if (!editPrice.getText().toString().matches("") &&
                !editDescription.getText().toString().matches("")
                && declaration.getReceiptPhoto() != null){
            declaration.setPrice(Double.parseDouble(editPrice.getText().toString()));
            declaration.setAuthority(spinnerAuthority.getSelectedItem().toString());
            declaration.setDescription(editDescription.getText().toString());

        } else if(declaration.getReceiptPhoto() == null) {
            Toast.makeText(NewDeclarationActivity.this,"Take a picture", Toast.LENGTH_SHORT).show();
            allFieldsFilled = false;
        } else if (editPrice.getText().toString().matches("")){
            Toast.makeText(NewDeclarationActivity.this,"Add price", Toast.LENGTH_SHORT).show();
            allFieldsFilled = false;
        } else if (editDescription.getText().toString().matches("")){
            Toast.makeText(NewDeclarationActivity.this,"Add description", Toast.LENGTH_SHORT).show();
            allFieldsFilled = false;
        }

        if (allFieldsFilled){
            Intent intent = new Intent(NewDeclarationActivity.this ,CheckDeclarationActivity.class);
            intent.putExtra("declaration", declaration);
            startActivityForResult(intent, 999);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyymmdd_hhmm", Locale.GERMANY).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchPhotoIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.declaration.nandm.declarationapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            declaration.setReceiptPhoto(mCurrentPhotoPath);

            Glide.with(this).load(contentUri).apply(new RequestOptions().centerCrop()).into(imageView);
        }
        else if (requestCode == 999){
            if (resultCode == RESULT_OK){
                finish();
            } else if (resultCode == RESULT_CANCELED){

            }
        }
    }

}
