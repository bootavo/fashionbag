package com.tarjetic.store.modules.menu.product_detail;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.ProductsInterface;
import com.tarjetic.store.interfaces.PromotionInterface;
import com.tarjetic.store.interfaces.ShoppingCarInterface;
import com.tarjetic.store.interfaces.UserInterface;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.Product;
import com.tarjetic.store.models.Promotion;
import com.tarjetic.store.models.ShoppingCar;
import com.tarjetic.store.models.ShoppingCarItem;
import com.tarjetic.store.models.User;
import com.tarjetic.store.modules.auth.login.LoginActivity;
import com.tarjetic.store.utilities.BaseActivity;
import com.tarjetic.store.utilities.Constant;
import com.tarjetic.store.utilities.GlideApp;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tarjetic.store.utilities.Constant.ORDER_KIND_PAY_CASH;
import static com.tarjetic.store.utilities.Constant.ORDER_KIND_PAY_COINS;

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

    @BindView(R.id.tv_name) TextView mName;
    @BindView(R.id.tv_category) TextView mCategory;
    @BindView(R.id.tv_stock) TextView mStock;
    @BindView(R.id.tv_price) TextView mPrice;
    @BindView(R.id.tv_coins) TextView mCoins;
    @BindView(R.id.tv_description) TextView mDescription;
    @BindView(R.id.iv_picture) ImageView mPicture;

    @BindView(R.id.btn_share) Button btnShare;

    private String TAG = ProductDetailActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;
    private String product_type;
    private int id_generic_pp = 0;
    private int stock = 0;

    private float price = 0;
    private int coins = 0;
    private float total_price = 0;
    private int total_coins = 0;
    private String kind_buy = "";

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

        product_type = getIntent().getStringExtra("product_type");
        id_generic_pp = getIntent().getIntExtra("id_generic_pp", 0);
        Log.d(TAG, "product_type: "+product_type);
        Log.d(TAG, "id_generic_pp: "+id_generic_pp);

        mPD = new ProgressDialogHelper(ctx);
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    public void setDataProduct(Product product){
        mName.setText(product.getNombre());
        mCategory.setText(product.getCategoria()+"");
        mStock.setText(product.getStock()+" en Stock");
        mDescription.setText(product.getDescripcion()+".");
        mPrice.setText("S/."+product.getPrecio()+"");

        if(product.getPrecio_fichas() == 0){
            mCoins.setText("No aplica");
        }else{
            mCoins.setText(product.getPrecio_fichas()+"");
        }

        GlideApp
                .with(ctx)
                .load(product.getImagen())
                .into(mPicture);

        price = product.getPrecio();
        coins = product.getPrecio_fichas();

        total_price = product.getPrecio();
        total_coins = product.getPrecio_fichas();
    }

    public void setDataPromotion(Promotion promotion){
        mName.setText(promotion.getNombre());
        mCategory.setText("Promociones");
        mStock.setVisibility(View.GONE);
        mDescription.setText(promotion.getDescripcion()+".");
        mPrice.setText("S/."+promotion.getPrecio()+"  ");
        mCoins.setText(promotion.getPrecio_fichas()+"");

        GlideApp
                .with(ctx)
                .load(promotion.getImagen())
                .into(mPicture);

        price = promotion.getPrecio();
        coins = promotion.getPrecio_fichas();

        total_price = promotion.getPrecio();
        total_coins = promotion.getPrecio_fichas();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_call:
                permissions();
                break;
            case R.id.btn_bag:
                User user = PreferencesHelper.getMyUserPref(ctx);
                if(user == null){
                    dialogLogin();
                }else{
                    dialogKindBuy();
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

    public void dialogLogin(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
        builder.setTitle("Aún no has iniciado sesión")
                .setMessage("¿Desea iniciar sesión o crearse una cuenta ahora?")
                .setPositiveButton("INICIAR SESION", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ctx, "Yes", Toast.LENGTH_SHORT).show();
                        next(LoginActivity.class, false);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ctx, "ATRAS", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    public void dialogKindBuy(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
        builder.setTitle("TIPO DE COMPRA")
                .setMessage("¿Como desea adquirir este producto?.");

                if (total_coins > 0){
                    builder.setPositiveButton("CON FICHAS", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(ctx, "CON FICHAS", Toast.LENGTH_SHORT).show();
                            kind_buy = ORDER_KIND_PAY_COINS;
                            verifyShoppingCar();
                        }
                    });
                }

                builder.setNegativeButton("CON EFECTIVO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ctx, "CON EFECTIVO", Toast.LENGTH_SHORT).show();
                        kind_buy = ORDER_KIND_PAY_CASH;
                        verifyShoppingCar();
                    }
                });

                builder.setIcon(R.mipmap.ic_launcher)
                .show();
    }

    public void showDialogQuantity(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
        builder.setTitle("No ah seleccionado la cantidad de productos")
                .setMessage("Por favor, escoga la cantidad deseada")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_user)
                .show();
    }

    public boolean verifyValidQuantity(){
        if(Integer.parseInt(getQuantity()) > 0){
            return true;
        }else {
            return false;
        }
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

        if(isNegative(current_quantity) || verifyStock(current_quantity)){

        }else {
            mQuantity.setText(current_quantity+"");

            Log.d(TAG, "SIZE: "+mPrice.getText().toString().length());

            total_price = current_quantity*price;
            total_coins = current_quantity*coins;

            mPrice.setText("S/."+ total_price +"");
            mCoins.setText(total_coins +"");
        }

    }

    public boolean isNegative(int number){
        if(number < 1){
            return true;
        }else {
            return false;
        }
    }

    public boolean verifyStock(int current_quantity){
        if (stock != 0){
            if (current_quantity <= stock) {
                return false;
            }else {
                Toast.makeText(ctx, "Has llegado al limite del stock", Toast.LENGTH_SHORT).show();
                return true;
            }
        }else {
            return false;
        }
    }

    public String getQuantity(){
        return mQuantity.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(null);
        serviceControl(product_type);
        User user = PreferencesHelper.getMyUserPref(ctx);
        if(user != null) {
            loginService();
        }
    }

    public void activeControls(){
        btnBag.setEnabled(true);
        btnIncrease.setEnabled(true);
        btnDecrease.setEnabled(true);
    }

    public void serviceControl(String product_type){
        if(product_type.equals("P")){//P = Product, S = Promotion
            retrieveProduct();
        }else {
            retrievePromotion();
        }
    }

    public void retrieveProduct(){
        Log.d(TAG, "***************** SERVICE PRDUCTO *****************");
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonRequest> mCall = mInterface.getProductById(id_generic_pp);
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    mPD.dimissPD();
                    stock = response.body().getResults().getProduct().getStock();
                    activeControls();
                    Log.d(TAG, "Retrofit Response retrieveProduct: "+ JsonPretty.getPrettyJson(response));
                    setDataProduct(response.body().getResults().getProduct());
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
    }

    public void retrievePromotion(){
        Log.d(TAG, "***************** SERVICE PROMOTION *****************");
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(ctx)){
            PromotionInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(PromotionInterface.class);
            Call<JsonRequest> mCall = mInterface.getPromotionById(id_generic_pp);
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    mPD.dimissPD();
                    activeControls();
                    Log.d(TAG, "Retrofit Response retrievePromotion: "+ JsonPretty.getPrettyJson(response));
                    setDataPromotion(response.body().getResults().getPromotion());
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
    }

    public void verifyShoppingCar(){
        mPD.showPD();
        //Verificar tipo de compra
        User user = PreferencesHelper.getMyUserPref(ctx);

        Log.d(TAG, "coins: "+mCoins.getText().toString());
        Log.d(TAG, "session_coins: "+user.getTotal_fichas());

        if (kind_buy.equals(Constant.ORDER_KIND_PAY_COINS)){
            if (Integer.parseInt(mCoins.getText().toString()) > user.getTotal_fichas()){
                Toast.makeText(ctx, "No tiene suficiente fichas para guardar el producto en el carrito de compras.", Toast.LENGTH_LONG).show();
                mPD.dimissPD();
                return;
            }
        }

        if(NetworkHelper.isNetworkAvailable(ctx)){
            ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
            Call<JsonRequest> mCall = mInterface.getShoppingCarByUser(user.getId_usuario());
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response verifyShoppingCar: "+JsonPretty.getPrettyJson(response));

                    if(response.body().getResults().getShopping_car() != null){
                        if(response.body().getResults().getShopping_car().size() > 0){
                            //mPD.dimissPD();
                            Log.d(TAG, "-------> TIENE CARRITO DE COMPRAS");
                            int id_carrito_compra = response.body().getResults().getShopping_car().get(0).getId_carrito_compra();
                            addItemsToShoppingCar(id_carrito_compra);
                        }else {
                            //mPD.dimissPD();
                            Log.d(TAG, "NO TIENE CARRITO DE COMPRAS");
                            createShoppingCar();
                        }

                    }else {
                        mPD.dimissPD();
                        Log.d(TAG, "NO TIENE CARRITO DE COMPRAS");
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

    }

    public void addItemsToShoppingCar(int id_carrito_compra){
        //mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(ctx)){
            User user = PreferencesHelper.getMyUserPref(ctx);

            ShoppingCarItem shoppingCarItem = new ShoppingCarItem();

            shoppingCarItem.setId_carrito_compra(id_carrito_compra);
            if (product_type.equals("P")){
                shoppingCarItem.setId_producto(id_generic_pp);
            }else {
                shoppingCarItem.setId_promocion(id_generic_pp);
            }

            if (kind_buy.equals(Constant.ORDER_KIND_PAY_CASH)){
                shoppingCarItem.setPrecio_total(total_price);
                shoppingCarItem.setFichas_total(0);
            }else {
                shoppingCarItem.setFichas_total(total_coins);
                shoppingCarItem.setPrecio_total(0);
            }

            shoppingCarItem.setCantidad(Integer.parseInt(mQuantity.getText().toString()));
            shoppingCarItem.setTipo_compra(kind_buy);

            ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
            Call<JsonRequest> mCall = mInterface.addItemsToShoppingCar(product_type, shoppingCarItem);

            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response addItemsToShoppingCar: "+JsonPretty.getPrettyJson(response));

                    if(response.body().getStatus().getCode() == 200){
                        Log.d(TAG, "-------> SE AGREGO AL CARRITO DE COMPRAS");
                        Toast.makeText(ctx, "El poducto se agrego al carrito de compras", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Log.d(TAG, "NO SE AGREGO AL CARRITO DE COMPRAS");
                        Toast.makeText(ctx, "No se pudo agregar al carrito de compras", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    mPD.dimissPD();
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void createShoppingCar(){
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(ctx)){
            User user = PreferencesHelper.getMyUserPref(ctx);

            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setId_usuario(user.getId_usuario());

            ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
            Call<JsonRequest> mCall = mInterface.createShoppingCar(shoppingCar);

            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    //mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response createShoppingCar: "+JsonPretty.getPrettyJson(response));

                    if(response.body().getStatus().getCode() == 200){
                        Log.d(TAG, "-------> SE CREO EL CARRITO DE COMPRAS");
                        //Toast.makeText(ctx, "SE CREO EL CARRITO DE COMPRAS", Toast.LENGTH_SHORT).show();
                        verifyShoppingCar();
                    }else {
                        Log.d(TAG, "NO SE CREO EL CARRITO DE COMPRAS");
                        //Toast.makeText(ctx, "NO SE CREO EL CARRITO DE COMPRAS", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    mPD.dimissPD();
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
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
