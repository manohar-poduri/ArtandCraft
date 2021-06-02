package com.example.studentandteacherlivestreamingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.studentandteacherlivestreamingapp.Fragments.HomeFragment.HomeFragment;
import com.example.studentandteacherlivestreamingapp.Fragments.LiveStream.LiveStreamFragment;
import com.example.studentandteacherlivestreamingapp.Fragments.Messages.MessagesFragment;
import com.example.studentandteacherlivestreamingapp.Fragments.Users.UsersFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_IS_CUSTOM = "is_custom_overflow_menu";


    ChipNavigationBar chipNavigationBar;
    ImageView profile_page,profile_menu;
    Toolbar toolbar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private boolean isCustomOverflowMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Window window = getWindow();
//        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),Color.BLACK));

        profile_page = findViewById(R.id.profilePage);

        chipNavigationBar = findViewById(R.id.bottomNav);
        chipNavigationBar.setItemSelected(R.id.home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

       /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        bottom_menu();

//        isCustomOverflowMenu = getIntent().getBooleanExtra(EXTRA_IS_CUSTOM, false);



        profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfilePageActivity.class));
            }
        });

    }

    private void bottom_menu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;

                switch (i){
                    case R.id.home:
                        fragment =  new HomeFragment();
                        break;
                    case R.id.live_stream:
                        fragment = new LiveStreamFragment();
                        break;
                    case R.id.users:
                        fragment = new UsersFragment();
                        break;
                    case R.id.messages:
                        fragment = new MessagesFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }



}
