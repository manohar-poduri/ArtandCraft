package com.example.studentandteacherlivestreamingapp.Fragments.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentandteacherlivestreamingapp.R;
import com.example.studentandteacherlivestreamingapp.model.Messages;
import com.example.studentandteacherlivestreamingapp.model.MessagesAdapter;
import com.example.studentandteacherlivestreamingapp.model.UsersDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    public static String receiverUID, receiverName, receiverImage, senderUID;

    ImageView back;
    CircleImageView chatProfileImage;
    TextView chatUsername;
    ImageView btnSendMessage;
    EditText edtChatMessage;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    public static String sImage;
    public static String rImage;
//    public static String senderRoom, receiverRoom;
//    ArrayList<Messages> messagesArrayList;
//    MessagesAdapter messagesAdapter;

    List<Messages> messagesList;
    MessagesAdapter messagesAdapter;


    String friendID;
    String message,myId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        auth = FirebaseAuth.getInstance();
        myId = auth.getCurrentUser().getUid();

        receiverName = getIntent().getStringExtra("name");
        receiverImage = getIntent().getStringExtra("receiverImage");
//        receiverUID = getIntent().getStringExtra("uid");
        friendID = getIntent().getStringExtra("uid");

//        messagesArrayList = new ArrayList<>();

        back = findViewById(R.id.back);
        chatProfileImage = findViewById(R.id.chat_profile_image);
        chatUsername = findViewById(R.id.chatUsername);
        recyclerView = findViewById(R.id.messagesRecyclerView);
        edtChatMessage = findViewById(R.id.edtSendMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        back = findViewById(R.id.back);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(this,messagesArrayList);
        messageRecyclerView.setAdapter(messagesAdapter);*/

        /*chatUsername.setText(receiverName);
        Picasso.get().load(receiverImage).into(chatProfileImage);*/

//        senderUID = auth.getUid();
//
//        senderRoom = senderUID+receiverUID;
//        receiverRoom = receiverUID+senderUID;



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(friendID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UsersDetails usersDetails = snapshot.getValue(UsersDetails.class);
                chatUsername.setText(usersDetails.getName());
                Picasso.get().load(usersDetails.getImageUri()).into(chatProfileImage);


                readMessages(myId, friendID, usersDetails.getImageUri());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(auth.getUid());
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                sImage = snapshot.child("imageUri").getValue().toString();
                rImage = receiverImage;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*DatabaseReference chatReference = firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Messages messages = dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/





/*
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtChatMessage.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Please enter the message", Toast.LENGTH_SHORT).show();
                    return;
                }
                edtChatMessage.setText("");
                Date date = new Date();

                Messages messages = new Messages(message,auth.getUid(),date.getTime(),receiverUID);

                firebaseDatabase = FirebaseDatabase.getInstance();

                firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages").push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDatabase.getReference().child("chats").child(receiverRoom).child("messages").push()
                                .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
            }
        });
*/


        edtChatMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 0){
                    btnSendMessage.setEnabled(true);
                } else {
                    btnSendMessage.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!edtChatMessage.getText().toString().startsWith(" ")){

                    edtChatMessage.getText().insert(0," ");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* MessagesFragment messagesFragment = new MessagesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("RUID",receiverUID);
                messagesFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,messagesFragment);
                fragmentTransaction.commit();*/

//               startActivity(new Intent(ChatActivity.this,MessagesFragment.class));
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = edtChatMessage.getText().toString();

                sendMessage(myId, friendID, message);

                edtChatMessage.setText(" ");
            }
        });


    }

    private void readMessages(String myId, String friendID, String imageUri) {

        messagesList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesList.clear();

                for (DataSnapshot ds : snapshot.getChildren()){

                    Messages messages = ds.getValue(Messages.class);

                    if (messages.getSender().equals(myId) && messages.getReceiver().equals(friendID) || messages.getSender().equals(friendID) && messages.getReceiver().equals(myId)){

                        messagesList.add(messages);
                    }

                    messagesAdapter = new MessagesAdapter(ChatActivity.this,messagesList,imageUri);
                    recyclerView.setAdapter(messagesAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String myId, String friendID, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",myId);
        hashMap.put("receiver",friendID);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("ChatsList").child(myId).child(friendID);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    reference1.child("id").setValue(friendID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
