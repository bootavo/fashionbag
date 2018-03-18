package info.fashion.bag.modules.menu.catalogue;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.fashion.bag.R;

/**
 * Created by gtufinof on 3/12/18.
 */

public class CatalogFragment extends Fragment {

    private String TAG = CatalogFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public static CatalogFragment newInstance(){
        return new CatalogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "----> onCreate");
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ctx = container.getContext();

        mSectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "----> onCreateView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "----> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "----> onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "----> onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "----> onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "----> onDestroy");
    }

    public void setupViewPager(ViewPager mViewPager){
        mSectionPageAdapter.addFragment(BagsFragment.newInstance(), "CARTERAS");
        mSectionPageAdapter.addFragment(JewelsFragment.newInstance(), "JOYAS");
        //mSectionPageAdapter.addFragment(new SettingsFragment(), "TODOS");
        mViewPager.setAdapter(mSectionPageAdapter);
    }

}
