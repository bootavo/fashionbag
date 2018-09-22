package info.fashion.bag.modules.menu.settings;

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
import info.fashion.bag.R;
import info.fashion.bag.models.User;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.PreferencesHelper;

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

}
