package com.fashionbag.modules.menu;

/**
 * Created by bootavo on 9/01/2018.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.fashionbag.modules.menu.bag.BagFragment;
import com.fashionbag.modules.menu.catalogue.CatalogFragment;
import com.fashionbag.modules.menu.home.HomeFragment;
import com.fashionbag.modules.menu.maps.MapFragment;
import com.fashionbag.modules.menu.settings.SettingsFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.fashionbag.R;
import com.fashionbag.utilities.SharedPreferencesHelper;

public class MainApp extends AppCompatActivity{

    @BindView(R.id.bnve) BottomNavigationViewEx mBottomNavigationViewEx;
    @BindView(R.id.fab) FloatingActionButton mFloatingActionButton;

    private static final String TAG = MainApp.class.getSimpleName();
    private SharedPreferencesHelper mSP;
    private Context ctx = MainApp.this;

    private List<Fragment> fragments;
    private Fragment currentFragment = null;

    private static final MainApp instance = new MainApp();

    public static MainApp getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_fab);
        initButterKnife();
        initData();
        initView();
        initEvent();
        init();
    }

    public void initButterKnife(){
        ButterKnife.bind(this);
    }

    public void initData(){
        fragments = new ArrayList<>(4);

        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);

        CatalogFragment catalogFragment = new CatalogFragment();
        fragments.add(catalogFragment);

        BagFragment bagFragment = new BagFragment();
        fragments.add(bagFragment);

        MapFragment mapFragment = new MapFragment();
        fragments.add(mapFragment);

        SettingsFragment settingsFragment = new SettingsFragment();
        fragments.add(settingsFragment);
        //updateFragment();

    }

    public void init(){
        mSP = new SharedPreferencesHelper(ctx);
        getDataPatientService();
    }

    private void initView() {
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableAnimation(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, fragments.get(0),null);
        transaction.commit();
    }

    private void initEvent(){

        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Log.d(TAG, "popBackStack");

                final FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.i_home:
                        transaction.replace(R.id.content, fragments.get(0));
                        transaction.commit();
                        return true;
                    case R.id.i_catalog:
                        transaction.replace(R.id.content, fragments.get(1));
                        transaction.commit();
                        return true;
                    case R.id.i_map:
                        transaction.replace(R.id.content, fragments.get(3));
                        transaction.commit();
                        return true;
                    case R.id.i_settings:
                        transaction.replace(R.id.content, fragments.get(4));
                        transaction.commit();
                        return true;
                    case R.id.i_empty:
                        return false;
                }
                return true;
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //next(SpecialistActivity.class, false);
                mBottomNavigationViewEx.setItemTextColor(getResources().getColorStateList(R.color.selector_item_primary_color));
                mBottomNavigationViewEx.setIconTintList(mBottomNavigationViewEx.getCurrentItem(), getResources().getColorStateList(R.color.selector_item_primary_color));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, fragments.get(2));
                transaction.commit();
            }
        });

    }

    public void getDataPatientService() {
        /*
        Log.d(TAG, "-------------> getDataPatientService");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            UsersInteface mInteface = ApiRetrofitClient.getRetrofitClient().create(UsersInteface.class);
            Call<Users> mCall = mInteface.getDataPatient(mSP.getToken());
            mCall.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Log.d(TAG, "response: "+ JsonPretty.getPrettyJson(response));
                    mSP.saveInt("id", response.body().getId());
                    mSP.saveString("email", response.body().getEmail());
                    mSP.saveString("first_name", response.body().getFirstName());
                    mSP.saveString("last_name", response.body().getLastName());
                    mSP.saveInt("age", response.body().getAge());
                    mSP.saveString("birth_date", response.body().getBirthDate());
                    mSP.saveString("photo", response.body().getPhoto());
                    mSP.saveString("gender", response.body().getGender());
                    mSP.saveString("blood_type", response.body().getBloodType());
                    mSP.saveString("size", response.body().getSize()+"");
                    mSP.saveString("weight", response.body().getWeight()+"");
                    mSP.saveString("dni", response.body().getDni());
                    mSP.saveString("cellphone", response.body().getCellphone());
                    mSP.saveBoolean("is_subscribe", response.body().isIs_subscribe());
                    mSP.saveString("url1_photo", response.body().getUrl_photo());
                    mSP.saveInt("month_used_calls", response.body().getMonth_used_calls());
                    mSP.saveInt("total_used_calls", response.body().getTotal_used_calls());
                    mSP.saveInt("available_plan_calls", response.body().getAvailable_plan_calls());
                    mSP.saveInt("available_unitary_calls", response.body().getAvailable_unitary_calls());
                    mSP.saveString("subscription_id", response.body().getSubscription_id());
                    //updateFragment();
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Log.d(TAG, "onFailure");
                    Log.d(TAG, "RetrofitError: "+t.getMessage());
                    Toast.makeText(ctx, "Problemas del Servidor, intentelo nuevamente", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(ctx, "Problemas de Conexión a Internet, intentelo nuevamente", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "-------------> /getDataPatientService");
        */
    }

    public void updateFragment(){
        /*
        Log.d(TAG, "-------------> updateFragment");
        //BlogFragment blankFragment = new BlogFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content, fragments.get(0),null);
        transaction.commit();

        //mBottomNavigationViewEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigationViewEx.setSelectedItemId(R.id.i_home);

        Log.d(TAG, "-------------> /updateFragment");
        */
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "-------------------------------> onStop");
        super.onStop();
    }

}
