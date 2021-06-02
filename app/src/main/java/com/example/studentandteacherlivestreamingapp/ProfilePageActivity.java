package com.example.studentandteacherlivestreamingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentandteacherlivestreamingapp.model.UsersDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePageActivity extends AppCompatActivity {

    ImageView back,settings;
    CircleImageView profile_image;
    Uri imageUri;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    String imageURI;
    TextView textEditProfile, displayName, description;
    String currentUserid;
    UsersDetails usersDetails;

    String status = "Hello User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        back = findViewById(R.id.back);
        settings = findViewById(R.id.settings);
        profile_image = findViewById(R.id.profile_image);
        textEditProfile = findViewById(R.id.textEditProfle);
        displayName = findViewById(R.id.display_name);
        description = findViewById(R.id.discription);

        usersDetails = new UsersDetails();

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails");
        StorageReference storageReference = firebaseStorage.getReference().child("users/" + auth.getCurrentUser().getUid() + "/upload");

        StorageReference file = firebaseStorage.getReference().child("users/" + auth.getCurrentUser().getUid() + "/upload");
        file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_image);

                databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name = snapshot.child("name").getValue(String.class);
                        String status = snapshot.child("status").getValue().toString();
                        displayName.setText(name);
                        description.setText(status);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        textEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (imageUri!=null){
                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()){

                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageURI = uri.toString();

                                        Picasso.get().load(uri).into(profile_image);

//                                        UsersDetails usersDetails = new UsersDetails();
//                                        usersDetails.setImageUri(imageURI);

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("imageUri",imageURI);
                                        

                                        databaseReference.child(auth.getCurrentUser().getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(ProfilePageActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                });
                            } else {
                                String status = "Hello User";

                                imageURI = "https://firebasestorage.googleapis.com/v0/b/surface-24c27.appspot.com/o/upload%2Fprofile_image.png?alt=media&token=de45e1d7-6dd8-412b-8ddc-ce5a9bc770c3";

                                UsersDetails usersDetails = new UsersDetails();
//                                usersDetails.setUid(auth.getUid());
                                usersDetails.setImageUri(imageURI);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("ProfileImage").setValue(usersDetails);

                            }
                        }
                    });
                }

            }
        });

    }

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.back:
                startActivity(new Intent(ProfilePageActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.settings:
//                startActivity(new Intent(ProfilePageActivity.this,SettingsActivity.class));

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfilePageActivity.this,"You have logged out successful!!",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProfilePageActivity.this,LoginActivity.class));
                this.finishAffinity();
                finish();
                break;

            case R.id.profile_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10){
            if (data!=null){
                imageUri = data.getData();
//                profile_image.setImageURI(imageUri);
            }
        }
    }
}
