package com.example.studentandteacherlivestreamingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.studentandteacherlivestreamingapp.util.SectionStatePageAdapter;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ImageView back;

    private SectionStatePageAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = findViewById(R.id.back);
//        setupSettingsAccount();
    }

    /*private void setupSettingsAccount(){
        ListView listView = findViewById(R.id.lvAccountSettings);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Settings");
        arrayList.add("Help");
        arrayList.add("Sign Out");

        ArrayAdapter adapter = new ArrayAdapter(SettingsActivity.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                setViewPager(position);
            }
        });
    }*/

    private void setViewPager(int fragmentNumber) {

        mRelativeLayout.setVisibility(View.GONE);
//        Log.d(TAG, "setViewPager: navigating to fragment #: " + fragmentNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.back:
                startActivity(new Intent(SettingsActivity.this, ProfilePageActivity.class));
                finish();
                break;
        }
    }
}
