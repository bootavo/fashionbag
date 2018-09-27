package com.tarjetic.store.modules.auth.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.UserInterface;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.User;
import com.tarjetic.store.modules.auth.register.RegisterActivity;
import com.tarjetic.store.modules.menu.MainApp;
import com.tarjetic.store.utilities.BaseActivity;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ProgressDialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/12/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_email) EditText mEmail;
    @BindView(R.id.et_password) EditText mPassword;

    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.btn_register) Button btnRegister;

    private String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButterKnide();
        init();
        initEventUI();
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    public void init(){
        mToolbar.setTitle("");

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

//        mEmail.setText("bootavo");
//        mPassword.setText("bootavo");

        mPD = new ProgressDialogHelper(ctx);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void initEventUI(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getEmail(){
        return mEmail.getText().toString().trim();
    }

    public String getPassword(){
        return mPassword.getText().toString().trim();
    }

    public boolean verifyLogin(){

        if(getEmail().equals("") || getEmail() == null){
            mEmail.setError("Ingrese su email");
            return false;
        }

        /*
        if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()){
            mEmail.setError("Ingrese un correo valido");
            return false;
        }*/

        if(getPassword().equals("") || getPassword() == null){
            mPassword.setError("Ingrese su password");
            return false;
        }

        return true;

    }

    public void service(){
        mPD.showPD();
        if(verifyLogin()){
            if(NetworkHelper.isNetworkAvailable(this)){
                UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
                User user = new User();
                user.setUsuario(getEmail());
                user.setClave(getPassword());
                Call<JsonRequest> mCall = mInterface.login(user);
                Log.d(TAG, "------> email: "+getEmail());
                Log.d(TAG, "------> pass : "+ getPassword());
                mCall.enqueue(new Callback<JsonRequest>() {
                    @Override
                    public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));
                        if (response.body().getStatus().getCode() == 200){
                            PreferencesHelper.setMyUserPref(ctx, response.body().getResults().getUser());
                            nextActivity();
                        }else{
                            Toast.makeText(ctx, "Credenciales incorrectas, intentelo nuevamente", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonRequest> call, Throwable t) {
                        Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                        mPD.dimissPD();
                    }
                });
            }else{
                mPD.dimissPD();
                Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }
        }else{
            mPD.dimissPD();
        }
    }

    public void nextActivity(){
        Intent mIntent = new Intent(this, MainApp.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                service();
                break;
            case R.id.btn_register:
                next(RegisterActivity.class, false);
                break;
        }
    }
}
