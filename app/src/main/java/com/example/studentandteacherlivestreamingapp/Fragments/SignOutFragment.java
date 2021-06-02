package com.example.studentandteacherlivestreamingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentandteacherlivestreamingapp.LoginActivity;
import com.example.studentandteacherlivestreamingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private ProgressBar progressBar;
    private TextView signout,tvSigningOut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_signout,container,false);

        signout = view.findViewById(R.id.confirmSignOut);
        progressBar = view.findViewById(R.id.progressBar);
        tvSigningOut = view.findViewById(R.id.tvSigningOut);

        Button btnSigningOut = view.findViewById(R.id.btnConfirmSignOut);

        progressBar.setVisibility(View.GONE);
        tvSigningOut.setVisibility(View.GONE);

//        setupFirebaseAuth();

        btnSigningOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                tvSigningOut.setVisibility(View.VISIBLE);

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"You have logged out successful!!",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();

            }
        });
        return view;
    }

}
