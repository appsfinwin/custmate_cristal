package com.finwin.cristal.custmate.e_shopping;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.finwin.cristal.custmate.R;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by AnVin on 1/9/2017.
 */

public class ContainerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
     View rootView=inflater.inflate(R.layout.container_fragment,container,false);

        ViewPager viewPager =  rootView.findViewById(R.id.viewPager);
        TabLayout tabLayout =  rootView.findViewById(R.id.tabLayout);

        Tabs_Adapter tabsAdapter = new Tabs_Adapter(getChildFragmentManager());
        tabsAdapter.addFragment(new E_Shopping(20),"e-Shopping");
        tabsAdapter.addFragment(new E_Shopping(21),"My-QR");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }
}
