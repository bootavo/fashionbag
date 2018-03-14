package com.fashionbag.utilities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fashionbag.R;

import java.io.Serializable;

/**
 * Created by gtufinof on 3/13/18.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MMRTEXT.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        */

    }

    //Method better thet start activity
    public void next(Class<?> activity, boolean isDestroy){

        startActivity(new Intent(this,activity));
        if (isDestroy) finish();
    }

    public void next(String name, @Nullable Object object, Class<?> activity, boolean isDestroy){
        Intent intent = new Intent(this,activity);
        if(object != null) intent.putExtra(name, (Serializable) object);
        startActivity(intent);
        if (isDestroy) finish();
    }

    public void next(String name, String data, Class<?> activity, boolean isDestroy){

        Intent intent = new Intent(this,activity);
        if(data != null) intent.putExtra(name, data);
        startActivity(intent);
        if (isDestroy) finish();
    }

}