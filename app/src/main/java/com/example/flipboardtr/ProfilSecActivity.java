package com.example.flipboardtr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ProfilSecActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    static SQLiteDatabase database;
    Bitmap selectedimage;
    Uri selecteduri;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private ImageView profilsecIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_sec);

      /*  Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.equalsIgnoreCase("new")){

        }*/

       final Context context = this;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        Button profilkaydet = findViewById(R.id.button_kaydet);
        profilkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagename = "Images/image.jpg";
                StorageReference newReference = storageReference.child(imagename);
                newReference.putFile(selecteduri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("Images/image.jpg");
                        profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();
                                FirebaseUser currentuser = firebaseAuth.getCurrentUser();
                                String useremail = currentuser.getEmail().toString();

                                UUID uuid = UUID.randomUUID();
                                String uuidstring = uuid.toString();
                                databaseReference.child("Profiles").child("userimageurl").setValue(downloadUrl);
                                databaseReference.child("Profiles").child("useremail").setValue(useremail);

                                String mesaj = getString(R.string.title_toastsaved);
                                Toast.makeText(getApplicationContext(),""+mesaj,Toast.LENGTH_LONG).show();
                                finish();

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        profilsecIv = findViewById(R.id.imageVieww);
        profilsecIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProfilSecActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                }else{
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode == RESULT_OK && data!=null){
            selecteduri = data.getData();
            try{
                selectedimage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selecteduri);
                profilsecIv.setImageBitmap(selectedimage);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

