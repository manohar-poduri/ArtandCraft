package com.example.studentandteacherlivestreamingapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentandteacherlivestreamingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.rImage;
import static com.example.studentandteacherlivestreamingapp.Fragments.Messages.ChatActivity.sImage;

/*
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    Context context;
    List<Messages> messagesList;
    String imageURL;
    public static final int MESSAGE_RIGHT = 0;
    public static final int MESSAGE_LEFT = 1;

    public MessagesAdapter(Context context, List<Messages> messagesList,String imageURL) {
        this.context = context;
        this.messagesList = messagesList;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MESSAGE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Messages messages = messagesList.get(position);


        holder.txtMessages.setText(messages.getMessage());
        Picasso.get().load(imageURL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtMessages;
        CircleImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMessages = itemView.findViewById(R.id.txtMessages);
            imageView = itemView.findViewById(R.id.profile_image);


        }
    }

    @Override
    public int getItemViewType(int position) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        assert user != null;
        if (messagesList.get(position).getSender().equals(user.getUid())){
            return MESSAGE_RIGHT;
        } else {
            return MESSAGE_LEFT;
        }

    }
}
*/

public class MessagesAdapter extends RecyclerView.Adapter{

    Context context;
    List<Messages> messagesList;
    String imageUri;
    public static final int MESSAGE_RIGHT = 0;
    public static final int MESSAGE_LEFT = 1;

    public MessagesAdapter(Context context, List<Messages> messagesList, String imageUri) {
        this.context = context;
        this.messagesList = messagesList;
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MESSAGE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages = messagesList.get(position);

        if (holder.getClass() == SenderViewHolder.class){
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.txtMessages.setText(messages.getMessage());
            Picasso.get().load(sImage).into(((SenderViewHolder) holder).imageView);
        } else {

            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.txtMessages.setText(messages.getMessage());
            Picasso.get().load(rImage).into(((ReceiverViewHolder) holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (messagesList.get(position).getSender().equals(user.getUid())){
            return MESSAGE_RIGHT;
        } else {
            return MESSAGE_LEFT;
        }    }

    class SenderViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView txtMessages;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_image);
            txtMessages = itemView.findViewById(R.id.txtMessages);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView txtMessages;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_image);
            txtMessages = itemView.findViewById(R.id.txtMessages);
        }
    }
}
