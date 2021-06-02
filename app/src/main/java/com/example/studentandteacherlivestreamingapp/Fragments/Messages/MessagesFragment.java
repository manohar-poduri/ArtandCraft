package com.example.studentandteacherlivestreamingapp.Fragments.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentandteacherlivestreamingapp.R;
import com.example.studentandteacherlivestreamingapp.model.ChatsList;
import com.example.studentandteacherlivestreamingapp.model.Messages;
import com.example.studentandteacherlivestreamingapp.model.MessagesAdapter;
import com.example.studentandteacherlivestreamingapp.model.UserAdapter;
import com.example.studentandteacherlivestreamingapp.model.UsersDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.senderRoom;
//import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.receiverRoom;
//import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.senderUID;
//import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.receiverUID;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.receiverUID;

public class MessagesFragment extends Fragment {


    ImageButton btnSearch,btnUsersChat;
    EditText edtSearchChat;
    RecyclerView recyclerView;

    List<ChatsList> userLists;
    List<UsersDetails> mUsers;
    UserAdapter mAdapter;
    FirebaseUser firebaseUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_messages,container,false);

        btnSearch = view.findViewById(R.id.imgBtnSearch);
        btnUsersChat = view.findViewById(R.id.imgBtnUsersChat);
        edtSearchChat = view.findViewById(R.id.edtChatSearch);
        recyclerView = view.findViewById(R.id.messagesChatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        userLists = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatsList").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userLists.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    ChatsList chatsList = ds.getValue(ChatsList.class);

                    userLists.add(chatsList);

                }

                chatsListing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*userList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/


//        Bundle bundle = new Bundle();
//        String receiverUID = getArguments().getString("RUID");




       /* senderUID = firebaseUser.getUid();
        receiverUID = usersDetails.getUid();


        senderRoom = senderUID+receiverUID;
        receiverRoom = receiverUID+senderUID;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom).child("messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Messages messages = dataSnapshot.getValue(Messages.class);
                    userList.add(messages);
                }

//                chatsListing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

       /* reference = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom).child("messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Messages messages = dataSnapshot.getValue(Messages.class);

                    if (messages.getSenderId().equals(fUser.getUid())){
                        usersList.add(receiverUID);
                    }
                    if (messages.getReceiverId().equals(fUser.getUid())){
                        usersList.add(senderUID);
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        btnUsersChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),UsersChatsActivity.class));
            }
        });

        return view;
    }

    private void chatsListing() {

        mUsers = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    UsersDetails usersDetails = dataSnapshot.getValue(UsersDetails.class);
                    for (ChatsList chatsList : userLists){
                        assert usersDetails != null;
                        if (usersDetails.getUid().equals(chatsList.getId())){

                            mUsers.add(usersDetails);
                        }
                    }
                }

                mAdapter = new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void readChats() {

        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("UserDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UsersDetails usersDetails = dataSnapshot.getValue(UsersDetails.class);

                    for (String id :usersList){
                        if (usersDetails.getUid().equals(id)){
                            if (mUsers.size() != 0){
                                for (UsersDetails usersDetails1 : mUsers){
                                    if (!usersDetails.getUid().equals(usersDetails1.getUid())){
                                        mUsers.add(usersDetails);
                                    }
                                }
                            } else {
                                mUsers.add(usersDetails);
                            }
                        }
                    }
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                userAdapter = new UserAdapter(getActivity(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/



}
