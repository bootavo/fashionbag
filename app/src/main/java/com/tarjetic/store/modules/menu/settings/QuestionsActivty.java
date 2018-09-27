package com.tarjetic.store.modules.menu.settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tarjetic.store.R;
import com.tarjetic.store.models.User;
import com.tarjetic.store.utilities.BaseActivity;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ShareAppHelper;

public class QuestionsActivty extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab_share) FloatingActionButton btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        initButterKnife();
        init();
        eventUI();
    }

    public void initButterKnife(){
        ButterKnife.bind(this);
    }

    public void init() {
        btnShare.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void eventUI(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_share:
                ShareAppHelper.shareApp(getApplicationContext(), this);
                break;
        }
    }

}
