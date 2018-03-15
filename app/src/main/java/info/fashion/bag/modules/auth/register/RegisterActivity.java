package info.fashion.bag.modules.auth.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.UserInterface;
import info.fashion.bag.models.JsonUser;
import info.fashion.bag.models.Token;
import info.fashion.bag.utilities.JsonHelper;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import info.fashion.bag.utilities.SharedPreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/12/18.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_email) EditText mEmail;
    @BindView(R.id.et_first_name) EditText mFirrstName;
    @BindView(R.id.et_last_name) EditText mLastName;
    @BindView(R.id.et_mobile_number) EditText mMobileNumber;
    @BindView(R.id.et_dni) EditText mDni;
    @BindView(R.id.et_password) EditText mPassword;

    @BindView(R.id.btn_register) Button btnRegister;

    private String TAG = RegisterActivity.class.getSimpleName();
    private SharedPreferencesHelper mSP;
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initButterKnide();
        init();
        initEventUI();
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    public void init(){
        mToolbar.setTitle("");

        btnRegister.setOnClickListener(this);

        mSP = new SharedPreferencesHelper(ctx);
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

    public String getFirstName(){
        return mFirrstName.getText().toString().trim();
    }

    public String getLastName(){
        return mLastName.getText().toString().trim();
    }

    public String getMobileNumber(){
        return mMobileNumber.getText().toString().trim();
    }

    public String getDni(){
        return mDni.getText().toString().trim();
    }

    public String getPassword(){
        return mPassword.getText().toString().trim();
    }

    public boolean verifyRegister(){

        if(getEmail().equals("") || getEmail() == null){
            mEmail.setError("Ingrese su email");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()){
            mEmail.setError("Ingrese un correo valido");
            return false;
        }

        if(getPassword().equals("") || getPassword() == null){
            mPassword.setError("Ingrese su password");
            return false;
        }

        if(getFirstName().equals("") || getPassword() == null){
            mFirrstName.setError("Ingrese su nombre");
            return false;
        }

        if(getLastName().equals("") || getPassword() == null){
            mLastName.setError("Ingrese sus apellidos");
            return false;
        }

        if(getDni().equals("") || getPassword() == null){
            mDni.setError("Ingrese su dni");
            return false;
        }

        if(getMobileNumber().equals("") || getPassword() == null){
            mMobileNumber.setError("Ingrese su número de teléfono");
            return false;
        }

        return true;

    }

    public void service(){
        mPD.showPD();
        if(verifyRegister()){
            if(NetworkHelper.isNetworkAvailable(this)){
                UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
                Call<Token> mCall = mInterface.createUser(getJson());
                Log.d(TAG, "------> email: "+getEmail());
                Log.d(TAG, "------> pass : "+ getPassword());
                mCall.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));
                        mSP.saveString("token", response.body().getToken());
                        Log.d(TAG, "SharedPreferences: "+mSP.getToken());
                        getUserData();
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
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

    public void getUserData(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
            Call<JsonUser> mCall = mInterface.getUserData(mSP.getToken());
            mCall.enqueue(new Callback<JsonUser>() {
                @Override
                public void onResponse(Call<JsonUser> call, Response<JsonUser> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));
                    mSP.saveString("first_name", response.body().getResults().get(0).getFirst_name());
                    mSP.saveString("last_name", response.body().getResults().get(0).getLast_login());
                    mSP.saveString("email", response.body().getResults().get(0).getEmail());
                    mSP.saveString("dni", response.body().getResults().get(0).getDni());
                    mSP.saveInt("id", response.body().getResults().get(0).getId());
                    mSP.saveInt("min_mount_jewels", response.body().getResults().get(0).getMin_mount_jewels());
                    mSP.saveInt("min_sales_units", response.body().getResults().get(0).getMin_sales_units());
                    mSP.saveInt("min_shoes_units", response.body().getResults().get(0).getMin_shoes_units());
                    mSP.saveString("mobile_phone", response.body().getResults().get(0).getMobile_phone());
                    mSP.saveString("phone_number", response.body().getResults().get(0).getPhone_number());
                    mSP.saveString("status", response.body().getResults().get(0).getStatus());
                    mSP.showAllSPData();
                }

                @Override
                public void onFailure(Call<JsonUser> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                service();
                Toast.makeText(ctx, "Registrando", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public Map<String, Object> getJson(){
        final JSONObject jsonChangePassword = new JSONObject();
        Map<String, Object> requestBody = new HashMap<>();

        try {
            jsonChangePassword.put("email", getEmail());
            jsonChangePassword.put("first_name", getFirstName());
            jsonChangePassword.put("last_name", getLastName());
            jsonChangePassword.put("password", getPassword());
            jsonChangePassword.put("dni", getDni());
            jsonChangePassword.put("mobile_number", getMobileNumber());
            requestBody = JsonHelper.toMap(jsonChangePassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestBody;
    }

}
