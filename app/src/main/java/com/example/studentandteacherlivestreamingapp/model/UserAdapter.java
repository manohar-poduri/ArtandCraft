package com.example.studentandteacherlivestreamingapp.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity;
import com.example.studentandteacherlivestreamingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context activity;
    List<UsersDetails> arrayList;
    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;


    public UserAdapter(Context activity, List<UsersDetails> usersDetailsArrayList) {

        this.activity = activity;
        this.arrayList = usersDetailsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.item_user_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        UsersDetails usersDetails = arrayList.get(position);


        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(usersDetails.getUid())){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }

//        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        holder.user_name.setText(usersDetails.name);
        holder.user_status.setText(usersDetails.status);
        Picasso.get().load(usersDetails.imageUri).into(holder.user_profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("name",usersDetails.getName());
                intent.putExtra("receiverImage",usersDetails.getImageUri());
                intent.putExtra("uid",usersDetails.getUid());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView user_profile;
        TextView user_name, user_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_profile = itemView.findViewById(R.id.user_profile);
            user_name = itemView.findViewById(R.id.chatName);
            user_status = itemView.findViewById(R.id.chatMessage);

        }
    }
}
