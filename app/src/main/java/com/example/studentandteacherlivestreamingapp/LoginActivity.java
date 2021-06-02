package com.example.studentandteacherlivestreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Handler;
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

import java.util.Random;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputLayout email,password;
    String currentUserid;
    FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);

        firebaseUser=auth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserDetails");
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signInBtn:

                if (email.getEditText().getText().toString().equals("") || password.getEditText().getText().toString().equals("")){

                    email.setError("Email!!");
                    email.requestFocus();

                    password.setError("Password!!");
                    password.requestFocus();
                } else {
                    auth.signInWithEmailAndPassword(email.getEditText().getText().toString(),password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                startActivity(new Intent(LoginActivity.this,MainActivity.class));

//                                Log.i("TAG1", "onComplete: " + firebaseUser.getUid());
//                                Log.i("TAG2", "onComplete: " + auth.getCurrentUser().getDisplayName());
                                Toast.makeText(LoginActivity.this, "Login Successful!!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "Login failed!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


                break;
            case R.id.forgotPassword:
                break;
            case R.id.registerHereBtn:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
    }

}
