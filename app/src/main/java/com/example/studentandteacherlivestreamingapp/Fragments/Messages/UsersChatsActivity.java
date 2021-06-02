package com.example.studentandteacherlivestreamingapp.Fragments.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.studentandteacherlivestreamingapp.MainActivity;
import com.example.studentandteacherlivestreamingapp.R;
import com.example.studentandteacherlivestreamingapp.model.Messages;
import com.example.studentandteacherlivestreamingapp.model.UserAdapter;
import com.example.studentandteacherlivestreamingapp.model.UsersDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersChatsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    ArrayList<UsersDetails> usersDetailsArrayList;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chats);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails");

        usersDetailsArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UsersDetails usersDetails = dataSnapshot.getValue(UsersDetails.class);
                    usersDetailsArrayList.add(usersDetails);
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(UsersChatsActivity.this,usersDetailsArrayList);
        recyclerView.setAdapter(userAdapter);

    }



}
