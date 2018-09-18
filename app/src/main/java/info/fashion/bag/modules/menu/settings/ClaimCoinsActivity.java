package info.fashion.bag.modules.menu.settings;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.interfaces.PromotionInterface;
import info.fashion.bag.interfaces.ShoppingCarInterface;
import info.fashion.bag.interfaces.TarjeticCodeInterface;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.Product;
import info.fashion.bag.models.Promotion;
import info.fashion.bag.models.ShoppingCar;
import info.fashion.bag.models.ShoppingCarItem;
import info.fashion.bag.models.TarjeticCode;
import info.fashion.bag.models.User;
import info.fashion.bag.modules.auth.login.LoginActivity;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.PreferencesHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.fashion.bag.utilities.Constant.ORDER_KIND_PAY_CASH;
import static info.fashion.bag.utilities.Constant.ORDER_KIND_PAY_COINS;

/**
 * Created by gtufinof on 3/14/18.
 */

public class ClaimCoinsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_code) EditText mCode;
    @BindView(R.id.btn_claim) Button btnClaim;

    private String TAG = ClaimCoinsActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_coins);
        initButterKnide();
        init();
        eventUI();
    }

    public void init(){
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnClaim.setOnClickListener(this);

        mPD = new ProgressDialogHelper(ctx);
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_claim:
                claimCoins();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(null);
    }

    public boolean verify(){
        if (mCode.getText().toString().equals("")){
            mCode.setError("Debe ingresar un código promocional");
            return false;
        }

        if (mCode.getText().toString().length() < 6){
            mCode.setError("El código debe contener 6 dígitos");
            return false;
        }

        return true;
    }

    public void claimCoins(){
        Log.d(TAG, "***************** SERVICE PRDUCTO *****************");
        mPD.showPD();
        if (verify()){
            if(NetworkHelper.isNetworkAvailable(ctx)){
                TarjeticCodeInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(TarjeticCodeInterface.class);
                Call<JsonRequest> mCall = mInterface.verifyCode(mCode.getText().toString());
                mCall.enqueue(new Callback<JsonRequest>() {
                    @Override
                    public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response claimCoins: "+ JsonPretty.getPrettyJson(response));
                        if (response.body().getStatus().getCode() == 200){
                            if (response.body().getResults().getCodigo().getEstado_cupon().equals("A")){
                                retrieveCoins(response.body().getResults().getCodigo().getId_canje(), response.body().getResults().getCodigo().getCantidad_fichas());
                            }else {
                                Toast.makeText(ctx, "El cupon ya ha sido usado", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ctx, "Código invalido", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonRequest> call, Throwable t) {
                        mPD.dimissPD();
                        Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mPD.dimissPD();
                Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    public void retrieveCoins(final int id_canje, final int coins){
        final User user = PreferencesHelper.getMyUserPref(ctx);
        Log.d(TAG, "***************** SERVICE PRDUCTO *****************");
        mPD.showPD();
        if (verify()){
            if(NetworkHelper.isNetworkAvailable(ctx)){
                TarjeticCodeInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(TarjeticCodeInterface.class);
                TarjeticCode tarjeticCode = new TarjeticCode();
                tarjeticCode.setId_canje(id_canje);
                Call<JsonRequest> mCall = mInterface.retrieveCoins(user.getId_usuario(), tarjeticCode);
                mCall.enqueue(new Callback<JsonRequest>() {
                    @Override
                    public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response retrieveCoins: "+ JsonPretty.getPrettyJson(response));
                        if (response.body().getStatus().getCode() == 200){
                            user.setTotal_fichas(user.getTotal_fichas()+coins);
                            PreferencesHelper.setMyUserPref(ctx, user);
                            Toast.makeText(ctx, "Fichas actuales: "+user.getTotal_fichas(), Toast.LENGTH_SHORT).show();
                            mCode.setText("");
                        }else{
                            Toast.makeText(ctx, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonRequest> call, Throwable t) {
                        mPD.dimissPD();
                        Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mPD.dimissPD();
                Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show();
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
