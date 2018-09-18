package info.fashion.bag.modules.menu.settings;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.UserInterface;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.User;
import info.fashion.bag.modules.auth.register.RegisterActivity;
import info.fashion.bag.modules.menu.MainApp;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.PreferencesHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;


    @BindView(R.id.et_old_password) EditText mOldPassword;
    @BindView(R.id.et_new_password) EditText mNewPassword;
    @BindView(R.id.et_password_confirm) EditText mPasswordConfirm;

    @BindView(R.id.btn_change_password) Button btnChangePassword;

    private String TAG = ChangePasswordActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initButterKnide();
        init();
        initEventUI();
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    public void init(){
        mToolbar.setTitle("");

        btnChangePassword.setOnClickListener(this);

        mOldPassword.setText("bootavo");
        mNewPassword.setText("bootavo12");
        mPasswordConfirm.setText("bootavo12");

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

    public String getOldPassword(){
        return mOldPassword.getText().toString().trim();
    }

    public String getNewPassword(){
        return mNewPassword.getText().toString();
    }

    public String getPasswordConfirm(){
        return mPasswordConfirm.getText().toString().trim();
    }

    public boolean verifyLogin(){

        if(getOldPassword().equals("") || getOldPassword() == null){
            mOldPassword.setError("Ingrese la contraseña anterior");
            return false;
        }

        User user = PreferencesHelper.getMyUserPref(ctx);
        Log.d(TAG, "clave: "+user.getClave());
        if (!getOldPassword().equals(user.getClave())){
            mOldPassword.setError("Contraseña anterior incorrecta");
            return false;
        }

        if(getNewPassword().equals("") || getNewPassword() == null){
            mNewPassword.setError("Ingrese la contraseña");
            return false;
        }

        if(getPasswordConfirm().equals("") || getPasswordConfirm() == null){
            mPasswordConfirm.setError("Ingrese la contraseña");
            return false;
        }

        if(!getNewPassword().equals(getPasswordConfirm())){
            mNewPassword.setError("Las contraseñas no coinciden");
            mPasswordConfirm.setError("Las contraseñas no coinciden");
            return false;
        }

        return true;

    }

    public void service(){
        mPD.showPD();
        if(verifyLogin()){
            if(NetworkHelper.isNetworkAvailable(this)){
                UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
                final User user = PreferencesHelper.getMyUserPref(ctx);
                user.setClave(getNewPassword());
                Call<JsonRequest> mCall = mInterface.changePassword(user.getId_usuario(), user);
                mCall.enqueue(new Callback<JsonRequest>() {
                    @Override
                    public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));
                        if (response.body().getStatus().getCode() == 200){
                            PreferencesHelper.setMyUserPref(ctx, user);
                            Toast.makeText(ctx, "Se ha cambiado la contraseña ", Toast.LENGTH_SHORT).show();
                            nextActivity();
                        }else{
                            Toast.makeText(ctx, "Dato incorrectos", Toast.LENGTH_LONG).show();
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
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_password:
                service();
                break;
        }
    }
}
