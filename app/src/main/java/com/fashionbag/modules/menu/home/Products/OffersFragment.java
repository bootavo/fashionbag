package com.fashionbag.modules.menu.home.Products;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fashionbag.R;
import com.fashionbag.modules.menu.catalogue.CatalogFragment;
import com.fashionbag.modules.menu.home.HomeFragment;
import com.fashionbag.modules.menu.maps.MapFragment;
import com.fashionbag.modules.menu.settings.SettingsFragment;

/**
 * Created by gtufinof on 3/12/18.
 */

public class OffersFragment extends Fragment {

    private String TAG = HomeFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offers, container, false);
        ctx = container.getContext();

        mSectionPageAdapter = new SectionPageAdapter(getFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

        return view;
    }

    public void setupViewPager(ViewPager mViewPager){
        mSectionPageAdapter.addFragment(new JewelOfferFragment(), "CARTERAS");
        mSectionPageAdapter.addFragment(new MapFragment(), "JOYAS");
        mSectionPageAdapter.addFragment(new SettingsFragment(), "TODOS");
        mViewPager.setAdapter(mSectionPageAdapter);
    }

}
