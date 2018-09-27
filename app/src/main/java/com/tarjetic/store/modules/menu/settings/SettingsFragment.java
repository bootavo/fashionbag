package com.tarjetic.store.modules.menu.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.UserInterface;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.User;
import com.tarjetic.store.modules.auth.login.LoginActivity;
import com.tarjetic.store.modules.auth.register.RegisterActivity;
import com.tarjetic.store.modules.menu.MainApp;
import com.tarjetic.store.utilities.GlideApp;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ShareAppHelper;

/**
 * Created by gtufinof on 3/11/18.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Nullable @BindView(R.id.tv_name) TextView mName;
    @Nullable @BindView(R.id.iv_picture) CircleImageView mPicture;
    @Nullable @BindView(R.id.tv_coins) TextView mCoins;

    @Nullable @BindView(R.id.btn_login) Button btnLogin;
    @Nullable @BindView(R.id.btn_register) Button btnRegister;

    @Nullable @BindView(R.id.rl_questions) RelativeLayout btnQuestions;
    @Nullable @BindView(R.id.rl_share_us) RelativeLayout btnShareUs;
    @Nullable @BindView(R.id.rl_about_midoc) RelativeLayout btnAboutUs;
    @Nullable @BindView(R.id.rl_terms_conditions) RelativeLayout btnTermsConditions;
    @Nullable @BindView(R.id.rl_change_password) RelativeLayout btnChangePassword;
    @Nullable @BindView(R.id.rl_call) RelativeLayout btnCall;
    @Nullable @BindView(R.id.rl_change_coins) RelativeLayout btnChangeCoins;
    @Nullable @BindView(R.id.rl_end_session) RelativeLayout btnEndSession;

    private String TAG = SettingsFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;
    private int state = 0;

    private LayoutInflater inflater;
    private ViewGroup container;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = container.getContext();
        this.inflater = inflater;
        this.container = container;
        verifyLoginUser();
        ButterKnife.bind(this, view);
        return view;
    }

    public void getPatientData(){
        User user = PreferencesHelper.getMyUserPref(ctx);
        if (user == null) {
            GlideApp
                    .with(ctx)
                    .load(R.drawable.ic_user)
                    .into(mPicture);
        }else{
            mName.setText(user.getNombres()+" "+user.getApellidos());
            mCoins.setText(""+user.getTotal_fichas());
            Log.d(TAG, "profile_picture"+user.getImagen());
            if(user.getImagen() == null || user.getImagen().equals("")){
                GlideApp
                        .with(ctx)
                        .load(R.drawable.ic_user)
                        .into(mPicture);
            }else{
                GlideApp
                        .with(ctx)
                        .load(user.getImagen())
                        .into(mPicture);
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Mi Cuenta");
    }

    @Override
    public void onStart() {
        super.onStart();
        if(state == 1){
            btnQuestions.setOnClickListener(this);
            btnShareUs.setOnClickListener(this);
            btnAboutUs.setOnClickListener(this);
            btnTermsConditions.setOnClickListener(this);
            btnChangePassword.setOnClickListener(this);
            btnCall.setOnClickListener(this);
            btnChangeCoins.setOnClickListener(this);
            btnEndSession.setOnClickListener(this);
            getPatientData();

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
        User user = PreferencesHelper.getMyUserPref(ctx);
        if(user != null) {
            loginService();
        }
        verifyLoginUser();
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
            case R.id.rl_change_password:
                startActivity(new Intent(ctx, ChangePasswordActivity.class));
                break;
            case R.id.rl_questions:
                startActivity(new Intent(ctx, QuestionsActivty.class));
                break;
            case R.id.rl_share_us:
                ShareAppHelper.shareApp(ctx, (Activity) ctx);
                break;
            case R.id.rl_change_coins:
                Intent mIntent = new Intent(ctx, ClaimCoinsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_call:
                permissions();
                break;
            case R.id.rl_about_midoc:
                startActivity(new Intent(ctx, AboutUsActivity.class));
                break;
            case R.id.rl_end_session:
                PreferencesHelper.cleanMyUserPref(ctx);
                Intent i = new Intent(ctx, MainApp.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
    }

    public void verifyLoginUser(){
        User user = PreferencesHelper.getMyUserPref(ctx);
        if(user == null){
            Log.d(TAG, "------------------------> User vacio");
            view = inflater.inflate(R.layout.fragment_need_login, container, false);
            state = 0;
        }else{
            Log.d(TAG, "------------------------> User con data");
            view = inflater.inflate(R.layout.fragment_settings, container, false);
            state = 1;
        }
    }

    public void permissions(){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
            return;
        } else {
            callPhone();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission accepted.
                    //Toast.makeText(ctx, "If", Toast.LENGTH_SHORT).show();
                    callPhone();
                } else {
                    //Toast.makeText(ctx, "If", Toast.LENGTH_SHORT).show();
                    //permission denied.
                }
                return;
            }
        }
    }

    public void callPhone() {
        String phoneNumber = "tel: 999885197";
        Toast.makeText(ctx, "LLamando...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);

    }

    public void loginService(){
        UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
        User userSession = PreferencesHelper.getMyUserPref(ctx);
        User user = new User();
        user.setUsuario(userSession.getUsuario());
        user.setClave(userSession.getClave());
        Call<JsonRequest> mCall = mInterface.login(user);
        mCall.enqueue(new Callback<JsonRequest>() {
            @Override
            public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));
                if (response.body().getStatus().getCode() == 200){
                    PreferencesHelper.setMyUserPref(ctx, response.body().getResults().getUser());
                }else{
                    Toast.makeText(ctx, "Credenciales incorrectas, intentelo nuevamente", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonRequest> call, Throwable t) {
            }
        });
    }

}