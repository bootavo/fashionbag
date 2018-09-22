package info.fashion.bag.modules.menu.settings;

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
import info.fashion.bag.R;
import info.fashion.bag.models.User;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.PreferencesHelper;

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
                try {
                    Bitmap bitmap = BitmapFactory.decodeResource
                            (getResources(), R.mipmap.ic_launcher); // your bitmapG
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bs);

                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        //intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "FashionBag");
                        intent.putExtra(Intent.EXTRA_STREAM, bs.toByteArray());

                        User user = PreferencesHelper.getMyUserPref(getApplicationContext());
                        String sAux = "";
                        if (user != null){
                            sAux = "\nDescarga Ahora Tarjetic y aprovecha nuestras ofertas en licores\n\n"+
                                    "Ingresa ahora el siguiente código:"+ user.getCodigo_app() +"y obten fichas para canjearls por productos gratis";
                        }else{
                            sAux = "\nDescarga Ahora Tarjetic y aprovecha nuestras ofertas en licores\n\n"+
                                    "además obten fichas para canjearls por productos gratis";
                        }
                        //sAux = sAux + "https://play.google.com/store/apps/details?id="+ getActivity().getPackageName() +"\n\n";
                        sAux = sAux + "https://call.midocvirtual.com/device.html"+"\n\n";
                        intent.putExtra(Intent.EXTRA_TEXT, sAux);
                        startActivity(Intent.createChooser(intent, "choose one"));
                    } catch(Exception e) {
                        e.getMessage();
                        e.toString();
                    }
                } catch(Exception e) {
                    e.getMessage();
                    e.toString();
                }

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
