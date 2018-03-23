package info.fashion.bag.modules.menu.settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.modules.auth.login.LoginActivity;
import info.fashion.bag.modules.auth.register.RegisterActivity;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.SharedPreferencesHelper;

/**
 * Created by gtufinof on 3/11/18.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Nullable @BindView(R.id.tv_name) TextView mName;
    @Nullable @BindView(R.id.iv_picture) CircleImageView mPicture;

    @Nullable @BindView(R.id.btn_login) Button btnLogin;
    @Nullable @BindView(R.id.btn_register) Button btnRegister;

    @Nullable @BindView(R.id.rl_share_us) RelativeLayout btnShareUs;
    @Nullable @BindView(R.id.rl_about_midoc) RelativeLayout btnAboutUs;
    @Nullable @BindView(R.id.rl_terms_conditions) RelativeLayout btnTermsConditions;

    private String TAG = SettingsFragment.class.getSimpleName();
    private SharedPreferencesHelper mSP;
    private Context ctx = null;
    private View view = null;
    private int state = 0;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = container.getContext();
        mSP = new SharedPreferencesHelper(ctx);

        Log.d(TAG, "token: "+mSP.getToken());

        if(mSP.getToken().equals("No definido")){
            Log.d(TAG, "no token");
            view = inflater.inflate(R.layout.fragment_need_login, container, false);
            state = 0;
        }else{
            Log.d(TAG, "si token");
            view = inflater.inflate(R.layout.fragment_settings, container, false);
            state = 1;
        }

        ButterKnife.bind(this, view);

        return view;
    }

    public void getPatientData(){
        Log.d(TAG, "name: "+mSP.getFirstname());
        mName.setText(mSP.getFirstname());

        Log.d(TAG, "profile_picture"+mSP.getProfilePicture());
        if (mSP.getProfilePicture().equals("No definido")) {
            GlideApp
                    .with(ctx)
                    .load(R.drawable.picture_bag)
                    .into(mPicture);
        }else{
            GlideApp
                    .with(ctx)
                    .load(mSP.getProfilePicture())
                    .into(mPicture);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Configuración");
    }

    @Override
    public void onStart() {
        super.onStart();
        getPatientData();
        callService();
        if(state == 1){
            btnShareUs.setOnClickListener(this);
            btnAboutUs.setOnClickListener(this);
            btnTermsConditions.setOnClickListener(this);
        }else{
            btnLogin.setOnClickListener(this);
            btnRegister.setOnClickListener(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void callService(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intentLogin = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(intentLogin);
                break;
            case R.id.btn_register:
                Intent intentRegister= new Intent(ctx, RegisterActivity.class);
                ctx.startActivity(intentRegister);
                break;
            case R.id.rl_terms_conditions:
                startActivity(new Intent(ctx, TermsConditions.class));
                break;
            case R.id.rl_share_us:
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
                    String sAux = "\nDescarga Ahora FashionBag y aprovecha nuestras ofertas\n\n";
                    //sAux = sAux + "https://play.google.com/store/apps/details?id="+ getActivity().getPackageName() +"\n\n";
                    sAux = sAux + "https://call.midocvirtual.com/device.html"+"\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(intent, "choose one"));
                } catch(Exception e) {
                    e.getMessage();
                    e.toString();
                }
                break;
            case R.id.rl_about_midoc:
                startActivity(new Intent(ctx, AboutUsActivity.class));
                break;
        }
    }
}
