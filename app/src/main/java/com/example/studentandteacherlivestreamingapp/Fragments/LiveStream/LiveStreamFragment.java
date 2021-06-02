package com.example.studentandteacherlivestreamingapp.Fragments.LiveStream;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.studentandteacherlivestreamingapp.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import io.agora.rtc.Constants;

public class LiveStreamFragment extends Fragment {

    ExtendedFloatingActionButton extendedFloatingActionButton;
    ScrollView scrollView;
    RadioButton host,audience;
    Button submit;
    EditText channel;

    int channelProfile;
    public static final String channelMessage = "com.agora.samtan.agorabroadcast.CHANNEL";
    public static final String profileMessage = "com.agora.samtan.agorabroadcast.PROFILE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_livestream,container,false);


        int MY_PERMISSIONS_REQUEST_CAMERA = 0;
        // Here, this is the current activity
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_CAMERA);

        }

        extendedFloatingActionButton = view.findViewById(R.id.extended_fab);
        scrollView = view.findViewById(R.id.scrollView);
        host = view.findViewById(R.id.host);
        audience = view.findViewById(R.id.audience);
        submit = view.findViewById(R.id.submit);
        channel = view.findViewById(R.id.channel);


        host.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checked = host.isChecked();
                if (checked) {
                    channelProfile = Constants.CLIENT_ROLE_BROADCASTER;
                    return;
                }
            }
        });

        audience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checked = audience.isChecked();
                if (checked) {
                    channelProfile = Constants.CLIENT_ROLE_AUDIENCE;
                    return;
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String channelName = channel.getText().toString();
                Intent intent = new Intent(getContext(), VideoLiveStreamActivity.class);
                intent.putExtra(channelMessage, channelName);
                intent.putExtra(profileMessage, channelProfile);
                startActivity(intent);
            }
        });






        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (i1 > 0 && extendedFloatingActionButton.getVisibility() == view.VISIBLE){
//                    extendedFloatingActionButton.hide();
                    extendedFloatingActionButton.shrink();
                } else if(i1 < 0 && extendedFloatingActionButton.getVisibility() != view.VISIBLE) {
//                    extendedFloatingActionButton.show();
                    extendedFloatingActionButton.extend();
                }
            }
        });

        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(),VideoLiveStreamActivity.class));
            }
        });

        return view;
    }

}
