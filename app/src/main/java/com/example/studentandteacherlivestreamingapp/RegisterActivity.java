package com.example.studentandteacherlivestreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.studentandteacherlivestreamingapp.model.UsersDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private TextInputLayout name,email,password;
    private Button signUpBtn;
    private DatabaseReference databaseReference;
    private UsersDetails usersDetails;
    String status = "Hello User";
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.nameSignUp);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        signUpBtn = findViewById(R.id.signUpBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserDetails");

        usersDetails = new UsersDetails();

        firebaseUser = auth.getCurrentUser();

        Log.i("auth uid", "onCreate: " + auth.getUid());

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signUpBtn:

                if (name.getEditText().getText().toString().equals("") || email.getEditText().getText().toString().equals("") || password.getEditText().getText().toString().equals("")){

                    name.setError("Name!!");
                    name.requestFocus();

                    email.setError("Email!!");
                    email.requestFocus();

                    password.setError("Password!!");
                    password.requestFocus();

                } else {
//                    databaseReference.child(auth.getCurrentUser().getUid()).setValue(usersDetails);


                    auth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),password.getEditText().getText().toString()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                usersDetails.setName(name.getEditText().getText().toString());
                                usersDetails.setEmail(email.getEditText().getText().toString());;
                                usersDetails.setPassword(password.getEditText().getText().toString());
                                usersDetails.setUid(auth.getCurrentUser().getUid());
                                usersDetails.setStatus(status);

                                databaseReference.child(auth.getCurrentUser().getUid()).setValue(usersDetails);

                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                                Log.i("TAG3", "onComplete: " + auth.getCurrentUser().getUid());
//                                Log.i("TAG2", "onComplete: " + auth.getCurrentUser().getDisplayName());
                                Toast.makeText(RegisterActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed!!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
                break;
            case R.id.signInHereBtn:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;

        }
    }
}
