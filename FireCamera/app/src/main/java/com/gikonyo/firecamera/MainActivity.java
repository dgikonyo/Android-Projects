package com.gikonyo.firecamera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView imageSelected;
    Button cameraButton;
    Button galleryButton;

    String currentPhotoPath;
    StorageReference storageReference;//this is the firebase storage reference


    public static final int CAMERA_PERM_CODE=101;
    public static final int CAMERA_REQUEST_CODE=102;
    public static final int GALLERY_REQUEST_CODE=105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSelected=findViewById(R.id.displayImageView);
        cameraButton=findViewById(R.id.btn_camera);
        galleryButton=findViewById(R.id.btn_gallery);

        //now we inititialize the firebase storage reference
        storageReference= FirebaseStorage.getInstance().getReference();

        //now we set the onClicklistener
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no we ask for permissoin from user to use camera--go to manifests and enable it
                askCameraPermission();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,GALLERY_REQUEST_CODE);//we use the GALLERY_REQUEST_CODE to distinguish the gallery pick request

            }
        });
    }

    private void askCameraPermission() {//will seek camera permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        }else{
            dispatchTakePictureIntent();
        }
    }

      //so we overide this method to give the user the request code for a permission that's been denied to a user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CAMERA_PERM_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){//if grandReuslt=0, permissoin is not granted
                dispatchTakePictureIntent();

            }else{
                Toast.makeText(this,"Camera permission is required to open camera",Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void openCamera() {//the camera will be opened
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//MediaStore handles all media operations in android
        startActivityForResult(camera,CAMERA_REQUEST_CODE);

        //now we save our image using onActivityResult
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//this method enables us to save the image into the gallery
        if(requestCode==CAMERA_REQUEST_CODE){
            //display the image from camera and save to gallery
            if(resultCode== Activity.RESULT_OK){
                File f=new File(currentPhotoPath);
                //imageSelected.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute URL od the Image is "+ Uri.fromFile(f));
                //will display the save location of the image in the storage device

                //now we want to save the data into the gallery
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                //so now we want to upload data to firebase
                uploadImageToFirebase(f.getName(),contentUri);//will give us the filename and the fil location
            }

        }

        if(requestCode==GALLERY_REQUEST_CODE){
            //display the image from camera and save to gallery
            if(resultCode== Activity.RESULT_OK){
                Uri contentUri=data.getData();
                String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName="JPEG_"+ timestamp + "."+ getFileExt(contentUri);//getFileExt gives us the file extension of image selected
                Log.d("tag","onActivityResult: Gallery Image Uri: "+ imageFileName);
                //imageSelected.setImageURI(contentUri);

                uploadImageToFirebase(imageFileName,contentUri);//will give us the filename and the fil location


            }

        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image =storageReference.child("images/"+ name);//this is how the image will be stored in firebase

        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//when our upload is successful, this method will be called
                //so now we get the URL of that image and upload to the imageview
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {//is the uri of image uploaded into the server
                        Picasso.get().load(uri).into(imageSelected);
                    }
                });
                Toast.makeText(MainActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {   //if the upload fails, this method will be called
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Upload failed",Toast.LENGTH_SHORT).show();
            }
        });
    }




    private String getFileExt(Uri contentUri) {
        ContentResolver c =getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();//getSingelton enables the program to accept all supported types of image
        //the map enables us to get the extension of the MimeType selected from the gallery
        return mime.getExtensionFromMimeType(c.getType(contentUri));//from the Uri we are getting the image extension
    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());//we are creating a timestamp for the filename
        String imageFileName = "JPEG_" + timeStamp + "_";

       // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File storageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(//we are creating the file name
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */ //is the extension for the image
                storageDir      /* directory */ //directory for storing the file
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();//contains the file path of the photo
        return image;
    }

//    static final int REQUEST_TAKE_PHOTO = 1; //we already have it in CAMERA_REQUEST_CODE

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//checks if the camera is present in the device or not
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,//then we get the Uri for the file
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


}