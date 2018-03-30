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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.BusinessRulesInterface;
import info.fashion.bag.interfaces.ColorsInterfaces;
import info.fashion.bag.interfaces.PromotionInterface;
import info.fashion.bag.interfaces.SalesOrdersInterface;
import info.fashion.bag.interfaces.UserInterface;
import info.fashion.bag.interfaces.VariantsInterface;
import info.fashion.bag.models.Color;
import info.fashion.bag.models.JsonBusinessRules;
import info.fashion.bag.models.JsonPromotion;
import info.fashion.bag.models.JsonSalesOrders;
import info.fashion.bag.models.JsonSalesOrdersDetails;
import info.fashion.bag.models.JsonUser;
import info.fashion.bag.models.Products;
import info.fashion.bag.models.Promotion;
import info.fashion.bag.models.SalesOrders;
import info.fashion.bag.models.SalesOrdersDatails;
import info.fashion.bag.models.User;
import info.fashion.bag.models.Variant;
import info.fashion.bag.modules.auth.login.LoginActivity;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.JsonHelper;
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

    private float business_rules_director_mount = 450.0f;
    private List<SalesOrdersDatails> listSalesOrderDetails;

    float total_mount = 0.0f;

    private List<Promotion> listPromotion;
    private int promotion_id = 0;

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
                    showDialogLogin();
                }else{
                    verifyMajorityUser();
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

    public void showDialogLogin(){
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
                        next(LoginActivity.class, false);
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

        if(isNegative(current_quantity)){

        }else {
            mQuantity.setText(current_quantity+"");
        }

    }

    public boolean isNegative(int number){
        if(number < 1){
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
        businessRules();
        promotion();
    }

    public void getProductColor(int id){
        Log.d(TAG, "***************** GET PRODUCT COLOR *****************");
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
        Log.d(TAG, "***************** SERVICE *****************");
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
                    Constant.ID_PRODUCT = response.body().getId();
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
        Log.d(TAG, "***************** VERIFIY MAJORITY USER *****************");
        if(verifyValidQuantity()){
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
                            Log.d(TAG, "-------> "+Constant.IS_MAJORITY_USER);
                            Log.d(TAG, "-------> IS_MAJORITY_USER");
                        }else {
                            Log.d(TAG, "-------> "+Constant.IS_MAJORITY_USER);
                            Log.d(TAG, "-------> NOT IS_MAJORITY_USER");
                        }
                        verifiySalesStore();
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
        }else {
            showDialogQuantity();
        }

    }

    public void verifiySalesStore(){
        Log.d(TAG, "***************** VERIFY SALES STORE *****************");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<JsonSalesOrders> mCall = mInterface.getSalesOrders(mSP.getToken());
            mCall.enqueue(new Callback<JsonSalesOrders>() {
                @Override
                public void onResponse(Call<JsonSalesOrders> call, Response<JsonSalesOrders> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));

                    Constant.RESERVA = "";
                    Constant.SALES_ORDERS_ID = 0;

                    for (int i=0; i<response.body().getResults().size(); i++){
                        if(response.body().getResults().get(i).getStatus().equals("RESERVA")){
                            Constant.RESERVA = "RESERVA";
                            Constant.SALES_ORDERS_ID = response.body().getResults().get(i).getId();
                            Log.d(TAG, "------> SALES ORDER ID: "+Constant.SALES_ORDERS_ID);

                            Constant.DIRECTOR_AMMOUNT = 0.0f;
                            Constant.DIRECTOR_AMMOUNT = response.body().getResults().get(i).getDirector_mount();
                            break;
                        }
                    }

                    if(Constant.RESERVA.equals("RESERVA")){
                        Log.d(TAG, "-------> TIENE RESERVAS");
                        getOrderDetails();
                        updateDirectorAmmout();
                    }else {
                        Log.d(TAG, "NO TIENE RESERVAS");
                        createSalesOrder();
                    }

                }

                @Override
                public void onFailure(Call<JsonSalesOrders> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void createSalesOrder(){
        Log.d(TAG, "***************** CREATE SALES ORDER *****************");
        final JSONObject jsonSettings = new JSONObject();
        final JSONObject jsonSalesOrder = new JSONObject();
        Map<String, Object> requestBody = new HashMap<>();

        try {
            jsonSettings.put("voucher_type", "BOLETA");

            jsonSalesOrder.put("client", mSP.getId());
            jsonSalesOrder.put("shipping_method", "PICKUP");
            jsonSalesOrder.put("location", 1);
            jsonSalesOrder.put("mailing", false);
            jsonSalesOrder.put("settings", jsonSettings);

            requestBody = JsonHelper.toMap(jsonSalesOrder);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<SalesOrders> mCall = mInterface.createSalesOrders(mSP.getToken(), requestBody);
            mCall.enqueue(new Callback<SalesOrders>() {
                @Override
                public void onResponse(Call<SalesOrders> call, Response<SalesOrders> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));

                    Constant.SALES_ORDERS_ID = 0;

                    if(response.body().getDetail() != null){
                        Log.d(TAG, "-------> NO SE PUDO CREAR EL SALES ORDERS");
                        Toast.makeText(ctx, response.body().getDetail(), Toast.LENGTH_SHORT).show();
                    }else {
                        Constant.SALES_ORDERS_ID = response.body().getId();
                        updateDirectorAmmout();
                        Log.d(TAG, "-------> SE CREO EL SALES ORDERS");
                    }

                }

                @Override
                public void onFailure(Call<SalesOrders> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }

    }

    public void createSalesOrderDetails(){
        Log.d(TAG, "***************** CREATE SALES ORDER DETAILS *****************");
        final JSONObject jsonSalesOrderDetails = new JSONObject();
        Map<String, Object> requestBody = new HashMap<>();

        try {
            jsonSalesOrderDetails.put("variant", Constant.ID_PRODUCT);
            jsonSalesOrderDetails.put("sales_order", Constant.SALES_ORDERS_ID);
            jsonSalesOrderDetails.put("units", Integer.parseInt(getQuantity()));
            jsonSalesOrderDetails.put("position", 1);

            requestBody = JsonHelper.toMap(jsonSalesOrderDetails);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "--------> Json SalesOrderDetails");
        Log.d(TAG, JsonPretty.getPrettyJson(jsonSalesOrderDetails));
        Log.d(TAG, "--------> Json SalesOrderDetails");

        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<SalesOrdersDatails> mCall = mInterface.createSalesOrdersDetails(mSP.getToken(), requestBody);
            mCall.enqueue(new Callback<SalesOrdersDatails>() {
                @Override
                public void onResponse(Call<SalesOrdersDatails> call, Response<SalesOrdersDatails> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response order_details: "+JsonPretty.getPrettyJson(response));

                    Constant.SALES_ORDERS_DETAIL_ID = response.body().getId();
                    //updateDirectorAmmout();
                    Log.d(TAG, "-------> SE CREO EL SALES ORDERS DETAILS");

                    //verify amount in promotion
                    for (int i=0; i<listPromotion.size(); i++){
                        if (total_mount >= listPromotion.get(i).getStart_amount() && total_mount < listPromotion.get(i).getEnd_amount()){
                            Log.d(TAG, "-------> TIENE PROMOCIÓN");
                            promotion_id = listPromotion.get(i).getVariation();

                            int old_promotion_id = 0;

                            for(int j=0; j<listSalesOrderDetails.size(); j++){
                                if(listSalesOrderDetails.get(j).isIs_promotion()) {
                                    Log.d(TAG, "-------> TIENE PROMOCIÓN");
                                    old_promotion_id = listSalesOrderDetails.get(j).getId();
                                    addPromotion();
                                    break;
                                }
                            }

                            if(old_promotion_id != 0){
                                deleteOldPromotion(old_promotion_id);
                            }else {
                                addPromotion();
                            }

                        }else{
                            Log.d(TAG, "-------> NO TIENE PROMOCIÓN "+i);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SalesOrdersDatails> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }

    }

    public void addPromotion(){
        Log.d(TAG, "***************** ADD PROMOTION *****************");
        final JSONObject jsonSalesOrderDetails = new JSONObject();
        Map<String, Object> requestBody = new HashMap<>();

        try {
            jsonSalesOrderDetails.put("variant", promotion_id);
            jsonSalesOrderDetails.put("sales_order", Constant.SALES_ORDERS_ID);
            jsonSalesOrderDetails.put("units", Integer.parseInt(getQuantity()));
            jsonSalesOrderDetails.put("is_promotion", true);
            jsonSalesOrderDetails.put("position", 1);

            requestBody = JsonHelper.toMap(jsonSalesOrderDetails);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "--------> Json Promotion");
        Log.d(TAG, JsonPretty.getPrettyJson(jsonSalesOrderDetails));
        Log.d(TAG, "--------> Json Promotion");

        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<SalesOrdersDatails> mCall = mInterface.createSalesOrdersDetails(mSP.getToken(), requestBody);
            mCall.enqueue(new Callback<SalesOrdersDatails>() {
                @Override
                public void onResponse(Call<SalesOrdersDatails> call, Response<SalesOrdersDatails> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "-------> SE CREO LA PROMOCIÓN");
                }

                @Override
                public void onFailure(Call<SalesOrdersDatails> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOldPromotion(int id){
        Log.d(TAG, "***************** DELETE OLD PROMOTION *****************");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            PromotionInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(PromotionInterface.class);
            Call<Promotion> mCall = mInterface.deletePromotion(mSP.getToken(), id);
            mCall.enqueue(new Callback<Promotion>() {
                @Override
                public void onResponse(Call<Promotion> call, Response<Promotion> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "-------> SE ELIMINO LA PROMOCIÓN");
                    addPromotion();
                }

                @Override
                public void onFailure(Call<Promotion> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDirectorAmmout(){
        Log.d(TAG, "***************** UPDATE DIRECTOR AMOUNT *****************");
        total_mount = Constant.DIRECTOR_AMMOUNT + (Constant.SALE_PRICE * Integer.parseInt(getQuantity()));

        if (total_mount >= business_rules_director_mount){
            if(NetworkHelper.isNetworkAvailable(ctx)){

                final JSONObject jsonUSers = new JSONObject();
                Map<String, Object> requestBody = new HashMap<>();

                try {
                    jsonUSers.put("is_majority_user_fake", true);
                    requestBody = JsonHelper.toMap(jsonUSers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UserInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(UserInterface.class);
                Call<User> mCall = mInterface.updateUser(mSP.getToken(), mSP.getId(), requestBody);
                mCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        mPD.dimissPD();
                        Log.d(TAG, "Retrofit Response: "+JsonPretty.getPrettyJson(response));

                        if(response.body().getDetail() != null){
                            Log.d(TAG, "-------> NO SE PUDO ACTALIZAR EL USUARIO");
                            Toast.makeText(ctx, response.body().getDetail(), Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG, "-------> SE PUDO ACTUALIZAR EL USUARIO");

                            boolean product_repeat = false;

                            for (int i=0; i<listSalesOrderDetails.size(); i++){
                                if(listSalesOrderDetails.get(i).getId() == Constant.ID_PRODUCT){
                                    Log.d(TAG, "-------> EL PRODUCTO ES REPETIDO");
                                    product_repeat = true;
                                    break;
                                }
                            }

                            if(product_repeat){
                                Log.d(TAG, "-------> ACTUALIZAR CANTIDAD DEL PRODUCTO");
                                updateQuantityOfSalesOrderDetail(Constant.ID_PRODUCT);
                            }else{
                                Log.d(TAG, "-------> CREAR SALES ORDER DETAIL");
                                createSalesOrderDetails();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                        mPD.dimissPD();
                    }
                });
            }else{
                mPD.dimissPD();
                Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }
        }else {
            createSalesOrderDetails();
        }

    }

    public void updateQuantityOfSalesOrderDetail(int id){

        if(NetworkHelper.isNetworkAvailable(ctx)){

            final JSONObject jsonUSers = new JSONObject();
            Map<String, Object> requestBody = new HashMap<>();

            try {
                jsonUSers.put("units", getQuantity());
                requestBody = JsonHelper.toMap(jsonUSers);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<SalesOrdersDatails> mCall = mInterface.updateSalesOrderDetail(mSP.getToken(), id, requestBody);
            mCall.enqueue(new Callback<SalesOrdersDatails>() {
                @Override
                public void onResponse(Call<SalesOrdersDatails> call, Response<SalesOrdersDatails> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response update quantity of product: "+JsonPretty.getPrettyJson(response));

                    if(response.body().getDetail() != null){
                        Log.d(TAG, "-------> NO SE PUDO ACTALIZAR LA CANTIDAD DEL PRODUCTO");
                        Toast.makeText(ctx, response.body().getDetail(), Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d(TAG, "-------> SE PUDO ACTUALIZAR LA CANTIDAD DEL PRODUCTO");
                    }

                }

                @Override
                public void onFailure(Call<SalesOrdersDatails> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
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

    public void promotion(){
        Log.d(TAG, "***************** PROMOTIONS *****************");
        mPD.showPD();
        if(NetworkHelper.isNetworkAvailable(ctx)){
            PromotionInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(PromotionInterface.class);
            Call<JsonPromotion> mCall = mInterface.getPromotions(mSP.getToken());
            mCall.enqueue(new Callback<JsonPromotion>() {
                @Override
                public void onResponse(Call<JsonPromotion> call, Response<JsonPromotion> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response Promotion: "+ JsonPretty.getPrettyJson(response));
                    listPromotion = response.body().getResults();
                }

                @Override
                public void onFailure(Call<JsonPromotion> call, Throwable t) {
                    mPD.dimissPD();
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void getOrderDetails(){
        Log.d(TAG, "***************** GET SALES ORDER DETAILS *****************");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<JsonSalesOrdersDetails> mCall = mInterface.getSalesOrdersDetails(mSP.getToken());
            mCall.enqueue(new Callback<JsonSalesOrdersDetails>() {
                @Override
                public void onResponse(Call<JsonSalesOrdersDetails> call, Response<JsonSalesOrdersDetails> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response sales_order_details: "+JsonPretty.getPrettyJson(response));

                    listSalesOrderDetails = response.body().getResults();

                }

                @Override
                public void onFailure(Call<JsonSalesOrdersDetails> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void businessRules(){
        Log.d(TAG, "***************** BUSINESS RULES *****************");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            BusinessRulesInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(BusinessRulesInterface.class);
            Call<JsonBusinessRules> mCall = mInterface.getBusinessRules(mSP.getToken());
            mCall.enqueue(new Callback<JsonBusinessRules>() {
                @Override
                public void onResponse(Call<JsonBusinessRules> call, Response<JsonBusinessRules> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response business_rules: "+JsonPretty.getPrettyJson(response));

                    business_rules_director_mount = response.body().getResults().get(0).getDirector_mount();

                }

                @Override
                public void onFailure(Call<JsonBusinessRules> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

}
