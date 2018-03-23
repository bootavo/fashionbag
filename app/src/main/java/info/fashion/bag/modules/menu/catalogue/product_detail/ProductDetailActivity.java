package info.fashion.bag.modules.menu.catalogue.product_detail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.interfaces.VariantsInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Color;
import info.fashion.bag.models.JsonProducts;
import info.fashion.bag.models.Products;
import info.fashion.bag.models.Variant;
import info.fashion.bag.modules.menu.catalogue.adapters.ProductAdapter;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.GridSpacingItemDecoration;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
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
        mPD = new ProgressDialogHelper(ctx);
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
                Toast.makeText(ctx, "Agregando a la bolsa...", Toast.LENGTH_SHORT).show();
                break;
        }
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
