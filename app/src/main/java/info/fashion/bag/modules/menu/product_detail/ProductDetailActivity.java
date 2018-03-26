package info.fashion.bag.modules.menu.product_detail;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.ColorsInterfaces;
import info.fashion.bag.interfaces.UserInterface;
import info.fashion.bag.interfaces.VariantsInterface;
import info.fashion.bag.models.Color;
import info.fashion.bag.models.JsonUser;
import info.fashion.bag.models.Variant;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import info.fashion.bag.utilities.SharedPreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/14/18.
 */

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.btn_call) Button btnCall;
    @BindView(R.id.btn_bag) Button btnBag;

    @BindView(R.id.tv_quantity) TextView mQuantity;
    @BindView(R.id.btn_increase) Button btnIncrease;
    @BindView(R.id.btn_decrease) Button btnDecrease;

    @BindView(R.id.tv_product_name) TextView mProductName;
    @BindView(R.id.tv_product_category) TextView mProductCategory;
    @BindView(R.id.tv_product_sku) TextView mProductSku;
    @BindView(R.id.tv_price_unitary) TextView mUnitaryPrice;
    @BindView(R.id.tv_price_whole_sale) TextView mPriceWholeSale;
    @BindView(R.id.tv_description) TextView mDescription;
    @BindView(R.id.iv_picture_product) ImageView mProductPicture;
    @BindView(R.id.civ_color) CircleImageView mProductColor;

    @BindView(R.id.btn_share) Button btnShare;

    private String TAG = ProductDetailActivity.class.getSimpleName();
    private SharedPreferencesHelper mSP;
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initButterKnide();
        init();
        eventUI();
    }

    public void init(){
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnCall.setOnClickListener(this);
        btnBag.setOnClickListener(this);

        btnIncrease.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);

        mPD = new ProgressDialogHelper(ctx);
        mSP = new SharedPreferencesHelper(ctx);
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    public void setData(Variant variant){

        Log.d(TAG, variant.getColor()+"");
        getProductColor(variant.getColor());

        mProductName.setText(variant.getName());
        mProductCategory.setText("Carteras");
        mProductSku.setText(variant.getSku());
        mDescription.setText("Peso aproximado de: "+variant.getWeight()+" gr.");
        mUnitaryPrice.setText("S/."+Constant.SUGGESTED_PRICE+"");
        mPriceWholeSale.setText("S/."+Constant.OFFER_PRICE+"");

        GlideApp
                .with(ctx)
                .load(variant.getImage_url())
                .into(mProductPicture);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_call:
                permissions();
                break;
            case R.id.btn_bag:
                if(mSP.getToken().equals("No definido")){
                    showDialog();
                }else{
                    Toast.makeText(ctx, "El producto se ha agregado correctamente", Toast.LENGTH_SHORT).show();
                    verifiySalesStore();
                }
                break;
            case R.id.btn_increase:
                increase();
                break;
            case R.id.btn_decrease:
                decrease();
                break;
        }
    }

    public void showDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
        builder.setTitle("Aún no has iniciado sesión")
                .setMessage("¿Desea iniciar sesión o crearse una cuenta ahora?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ctx, "Yes", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ctx, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.drawable.ic_user)
                .show();
    }

    public void increase(){
        updateQuantity(1);
    }

    public void decrease(){
        updateQuantity(-1);
    }

    public void updateQuantity(int value){

        int current_quantity = Integer.parseInt(getQuantity());
        current_quantity += value;

        if(isNegative(current_quantity)){

        }else {
            mQuantity.setText(current_quantity+"");
        }

    }

    public boolean isNegative(int number){
        if(number < 0){
            return true;
        }else {
            return false;
        }
    }

    public String getQuantity(){
        return mQuantity.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(null);
        service();
    }

    public void getProductColor(int id){
        Log.d(TAG, "color id: "+id);
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ColorsInterfaces mInterface = ApiRetrofitClient.getRetrofitClient().create(ColorsInterfaces.class);
            Call<Color> mCall = mInterface.getVariant(id);
            mCall.enqueue(new Callback<Color>() {
                @Override
                public void onResponse(Call<Color> call, Response<Color> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));
                    //mProductColor.setBackgroundColor(android.graphics.Color.parseColor(response.body().getColor()));
                    mProductColor.setColorFilter(android.graphics.Color.parseColor(response.body().getColor()));

                    //Second form / need some fixes
                    //Log.d(TAG, "color 3: "+response.body().getColor().substring(1,response.body().getColor().length()));
                    //int color = Integer.parseInt(response.body().getColor().substring(1,response.body().getColor().length()), 16)+0xFF000000;
                    //mProductColor.setImageResource(color);

                }

                @Override
                public void onFailure(Call<Color> call, Throwable t) {
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void service(){
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(ctx)){
            VariantsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(VariantsInterface.class);
            Call<Variant> mCall = mInterface.getVariant(Constant.ID_PRODUCT);
            mCall.enqueue(new Callback<Variant>() {
                @Override
                public void onResponse(Call<Variant> call, Response<Variant> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));
                    Variant variant = new Variant();
                    variant.setName(Constant.PRODUCT_NAME);
                    variant.setColor(response.body().getColor());
                    variant.setSku(response.body().getSku());
                    variant.setImage_url(response.body().getImage_url());
                    variant.setWeight(response.body().getWeight());
                    setData(variant);
                }

                @Override
                public void onFailure(Call<Variant> call, Throwable t) {
                    mPD.dimissPD();
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyMajorityUser(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
            Call<JsonUser> mCall = mInterface.getUserData(mSP.getToken());
            mCall.enqueue(new Callback<JsonUser>() {
                @Override
                public void onResponse(Call<JsonUser> call, Response<JsonUser> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));

                    Constant.IS_MAJORITY_USER = response.body().getResults().get(0).isIs_majority_user();

                    if(Constant.IS_MAJORITY_USER){

                    }else {

                    }
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

    public void verifiySalesStore(){

    }

    public void permissions() {
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
        String phoneNumber = "tel: 956573855";
        Toast.makeText(ctx, "LLamando...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
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
