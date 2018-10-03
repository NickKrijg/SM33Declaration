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
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewDeclarationActivity extends AppCompatActivity {

    private ImageView imageView;
    private FloatingActionButton fab;
    private EditText editPrice;
    private EditText editDescription;

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
        editPrice = (EditText)findViewById(R.id.editPrice);
        fab = (FloatingActionButton)findViewById(R.id.fabSend);
        imageView = (ImageView)findViewById(R.id.imageView);

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
        declaration.setPrice(Double.parseDouble(editPrice.getText().toString()));
        declaration.setAuthority("Sportclub");
        declaration.setDescription(editDescription.getText().toString());

        Intent intent = new Intent(NewDeclarationActivity.this ,CheckDeclarationActivity.class);
        intent.putExtra("declaration", declaration);
        startActivity(intent);
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
        super.onActivityResult(requestCode, resultCode, data);

        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        declaration.setReceiptPhoto(mCurrentPhotoPath);

        Glide.with(this).load(contentUri).apply(new RequestOptions().centerCrop()).into(imageView);
    }

}