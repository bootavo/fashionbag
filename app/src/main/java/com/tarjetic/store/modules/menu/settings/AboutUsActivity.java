package com.tarjetic.store.modules.menu.settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tarjetic.store.R;
import com.tarjetic.store.models.User;
import com.tarjetic.store.utilities.BaseActivity;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ShareAppHelper;

/**
 * Created by bootavo on 30/12/2017.
 */

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    //@BindView(R.id.tv_aboutus) TextView mAboutUs;
    @BindView(R.id.fab_share) FloatingActionButton btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        initButterKnife();
        init();
        eventUI();
    }

    public void initButterKnife(){
        ButterKnife.bind(this);
    }

    public void init(){
        btnShare.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String text = "<body>"+
                "<p align='justify'> Midoc Vida Sana es un aplicativo (APP) en la cual podrás tener una Video Asesoría con una red de profesionales y asesores en temas de hábitos y estilos de vida saludable, que te atenderán de manera virtual, para lograr tus objetivo de bajar de peso o resolver tus problemas que tanto te aquejan.</p>"+
                "<b><p align='justify'> <font color='#000000'>Nunca fue tan fácil bajar de peso y estar en forma con las video asesorías y recomendaciones de MIDOC Vida Sana</font> </p></b>"+
                "<p align='justify'> <font color='#000000'>Resolveremos tus problemas para:</font> </p>"+

                "<ul style='list-style: none;'>"+
                "<li style='margin-bottom: 5px;'><font color='#000000'> Reducir el sobrepeso, obesidad, diabetes, hipertensión, entre otras.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> La etapa de embarazo.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> El adulto Mayor.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> Las madres con niños.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> Los deportistas.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> Las personas que realizan actividad física.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> Los desórdenes alimenticios.</font></li>" +
                "<li style='margin-bottom: 5px;'><font color='#000000'> Prevenir enfermedades.</font></li>" +
                "</ul>"+

                "</body>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //mAboutUs.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            //mAboutUs.setText(Html.fromHtml(text));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("Tarjetic");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_share:
                ShareAppHelper.shareApp(getApplicationContext(), this);
                break;
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

}
