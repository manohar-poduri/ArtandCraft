package com.example.studentandteacherlivestreamingapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentandteacherlivestreamingapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<MessageBean> messageBeanList;
    private LayoutInflater inflater;

    public MessageAdapter(Context context,List<MessageBean> messageBeansList){
        inflater = ((Activity) context).getLayoutInflater();
        this.messageBeanList = messageBeansList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.msg_item_layout,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MessageBean bean = messageBeanList.get(position);
        if (bean.isBeSelf()){
            holder.localIcon.setText(bean.getAccount());
            holder.localMessage.setText(bean.getMessage());
        } else {
            holder.remoteIcon.setText(bean.getAccount());
            holder.remoteMessage.setText(bean.getMessage());
        }

        holder.remoteLayout.setVisibility(bean.isBeSelf() ? View.GONE : View.VISIBLE);
        holder.localLayout.setVisibility(bean.isBeSelf() ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return messageBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView remoteIcon;
        private TextView remoteMessage;
        private TextView localIcon;
        private TextView localMessage;
        private RelativeLayout remoteLayout;
        private RelativeLayout localLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            remoteIcon = itemView.findViewById(R.id.remote_icon);
            remoteMessage = itemView.findViewById(R.id.remote_message);
            localIcon = itemView.findViewById(R.id.local_icon);
            localMessage = itemView.findViewById(R.id.local_message);
            remoteLayout = itemView.findViewById(R.id.remote_message_layout);
            localLayout = itemView.findViewById(R.id.local_message_layout);
        }
    }
}
