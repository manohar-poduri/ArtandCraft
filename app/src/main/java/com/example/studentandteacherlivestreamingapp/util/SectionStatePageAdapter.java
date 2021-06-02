package com.example.studentandteacherlivestreamingapp.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionStatePageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final HashMap<Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentsNumber = new HashMap<>();
    private final HashMap<Integer, String> mFragmentNames = new HashMap<>();

    public SectionStatePageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    private void addFragment(Fragment fragment, String fragmentName){

        mFragmentList.add(fragment);
        mFragments.put(fragment,mFragmentList.size()-1);
        mFragmentsNumber.put(fragmentName,mFragmentList.size()-1);
        mFragmentNames.put(mFragmentList.size()-1,fragmentName);
    }

    public Integer getFragmentNumber(String fragmentName){
        if (mFragmentsNumber.containsKey(fragmentName)){
            return mFragmentsNumber.get(fragmentName);
        }else {
            return null;
        }
    }

    public Integer getFragmentNumber(Fragment fragment){
        if (mFragmentsNumber.containsKey(fragment)){
            return mFragmentsNumber.get(fragment);
        }else {
            return null;
        }
    }

    public String getFragmentName(String fragmentNumber){
        if (mFragmentNames.containsKey(fragmentNumber)){
            return mFragmentNames.get(fragmentNumber);
        }else {
            return null;
        }
    }
}
