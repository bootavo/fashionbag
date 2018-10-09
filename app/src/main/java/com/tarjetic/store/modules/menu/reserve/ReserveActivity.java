package com.tarjetic.store.modules.menu.reserve;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.ShoppingCarInterface;
import com.tarjetic.store.interfaces.UserInterface;
import com.tarjetic.store.listeners.OnItemClickListener;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.ShoppingCarItem;
import com.tarjetic.store.models.User;
import com.tarjetic.store.modules.menu.reserve.adapters.ReserveAdapter;
import com.tarjetic.store.modules.menu.reserve.register_order.OrderRegisterOne;
import com.tarjetic.store.utilities.BaseActivity;
import com.tarjetic.store.utilities.Constant;
import com.tarjetic.store.utilities.GridSpacingItemDecoration;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import com.tarjetic.store.utilities.PreferencesHelper;
import com.tarjetic.store.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/11/18.
 */

public class ReserveActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view_reserve) RecyclerView mRecyclerView;
    @BindView(R.id.pb_reserve) ProgressBar mPBProducts;

    @BindView(R.id.tv_reserve) TextView mSearching;
    @BindView(R.id.btn_confirm_order) Button btnConfirm;

    @BindView(R.id.rl_btn) RelativeLayout mRLBtn;

    private String TAG = ReserveActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    private int id_carrito_compra = 0;
    private float cash = 0;
    private int coins = 0;
    private int cantidad = 0;

    private User userSession = null;

    public static Activity activity;

    public static ReserveActivity newInstance(){
        return new ReserveActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        userSession = PreferencesHelper.getMyUserPref(ctx);
        initButterKnife();
        init();
        eventUI();
        activity = this;
    }

    public void initButterKnife(){
        ButterKnife.bind(this);
    }

    public void eventUI(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init(){
        setTitle(null);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPD = new ProgressDialogHelper(ctx);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userSession = PreferencesHelper.getMyUserPref(ctx);
        mRecyclerView.removeAllViewsInLayout();
        verifyShoppingCar();
        cash = 0;
        coins = 0;
        User user = PreferencesHelper.getMyUserPref(ctx);
        if(user != null) {
            loginService();
        }
    }

    public void verifyShoppingCar(){
        coins = 0;
        cash = 0;
        mPD.showPD();
        mSearching.setText("Cargando reservas...");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            User user = PreferencesHelper.getMyUserPref(ctx);
            Log.d(TAG, "verifyShoppingCar: "+user.getId_usuario());
            ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
            Call<JsonRequest> mCall = mInterface.getShoppingCarByUser(user.getId_usuario());
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response sales_order: "+JsonPretty.getPrettyJson(response));

                    if(response.body().getResults().getShopping_car()  != null){
                        Log.d(TAG, "-------> TIENE RESERVAS");

                        if(!response.body().getResults().getShopping_car().isEmpty()){
                            id_carrito_compra = response.body().getResults().getShopping_car().get(0).getId_carrito_compra();
                            getReserves();
                        }else {
                            mRLBtn.setVisibility(View.GONE);
                            mPBProducts.setVisibility(View.GONE);
                            mSearching.setVisibility(View.VISIBLE);
                            mSearching.setText("No cuenta con reservas");
                            Log.d(TAG, "NO TIENE RESERVAS");
                        }

                    }else {
                        mPBProducts.setVisibility(View.GONE);
                        mSearching.setVisibility(View.VISIBLE);
                        mSearching.setText("No cuenta con reservas");
                        Log.d(TAG, "NO TIENE RESERVAS");
                    }

                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPBProducts.setVisibility(View.GONE);
                    mSearching.setVisibility(View.VISIBLE);
                    mSearching.setText("No se han podido cargar las reservas");
                    mPD.dimissPD();
                }
            });
        }else{
            mPBProducts.setVisibility(View.GONE);
            mPD.dimissPD();
            mSearching.setVisibility(View.VISIBLE);
            mSearching.setText("No se han podido cargar las reservas");
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void getReserves(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            User user = PreferencesHelper.getMyUserPref(ctx);
            Log.d(TAG, "id_usaurio: "+user.getId_usuario());
            Log.d(TAG, "id_carrito_compra: "+id_carrito_compra);
            ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
            Call<JsonRequest> mCall = mInterface.getShoppingItems(user.getId_usuario(), id_carrito_compra);
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response sales_order: "+JsonPretty.getPrettyJson(response));

                    if (response.body().getResults().getShopping_car_items().size() > 0){
                        btnConfirm.setEnabled(true);
                        mSearching.setVisibility(View.GONE);
                        mPBProducts.setVisibility(View.GONE);

                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 1);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(new ReserveAdapter(response.body().getResults().getShopping_car_items(), new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                ShoppingCarItem shoppingCarItem = (ShoppingCarItem) o;
                                Toast.makeText(ctx, ""+shoppingCarItem.getCantidad(), Toast.LENGTH_SHORT).show();
                                //Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                                //ctx.startActivity(mIntent);
                            }
                        }, ctx));


                        for (int i=0; i<response.body().getResults().getShopping_car_items().size(); i++){

                            if (response.body().getResults().getShopping_car_items().get(i).getItemProduct() != null){
                                if(response.body().getResults().getShopping_car_items().get(i).getTipo_compra().equals(Constant.ORDER_KIND_PAY_CASH)){
                                    cash = cash+response.body().getResults().getShopping_car_items().get(i).getPrecio_total();
                                }else{
                                    coins = coins+response.body().getResults().getShopping_car_items().get(i).getItemProduct().get(0).getPrecio_fichas()*response.body().getResults().getShopping_car_items().get(i).getCantidad();
                                }
                            }else {
                                if(response.body().getResults().getShopping_car_items().get(i).getTipo_compra().equals(Constant.ORDER_KIND_PAY_CASH)){
                                    cash = cash+response.body().getResults().getShopping_car_items().get(i).getPrecio_total();
                                }else{
                                    coins = coins+response.body().getResults().getShopping_car_items().get(i).getItemPromotion().get(0).getPrecio_fichas()*response.body().getResults().getShopping_car_items().get(i).getCantidad();
                                }
                            }

                            cantidad = cantidad + response.body().getResults().getShopping_car_items().get(i).getCantidad();

                        }

                        Log.d(TAG, "-----------------> CANTIDAD: "+cantidad);

                        mRLBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(ctx, "Efectivo: "+cash+", Tarjicoins: "+coins, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Efectivo: "+cash+", Tarjicoins: "+coins);
                    }else{
                        mRecyclerView.removeAllViewsInLayout();
                        mRLBtn.setVisibility(View.GONE);
                        mPBProducts.setVisibility(View.GONE);
                        mSearching.setVisibility(View.VISIBLE);
                        mSearching.setText("No cuenta con reservas");
                        Log.d(TAG, "NO TIENE RESERVAS");
                    }

                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mPBProducts.setVisibility(View.GONE);
                    mSearching.setVisibility(View.VISIBLE);
                    mSearching.setText("No se han podido cargar las reservas");
                    mPD.dimissPD();
                }
            });
        }else{
            mPBProducts.setVisibility(View.GONE);
            mPD.dimissPD();
            mSearching.setVisibility(View.VISIBLE);
            mSearching.setText("No se han podido cargar las reservas");
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm_order:
                orderProcess();
                break;
        }
    }

    public void orderProcess(){

        if(coins > 0){

            Log.d(TAG, "coins: "+coins);
            Log.d(TAG, "session_coins: "+userSession.getTotal_fichas());

            if (coins <= userSession.getTotal_fichas()) {
                Intent mIntent = new Intent(ctx, OrderRegisterOne.class);
                mIntent.putExtra("cash",cash);
                mIntent.putExtra("coins", coins);
                mIntent.putExtra("cantidad", cantidad);
                ctx.startActivity(mIntent);
            }else{
                Toast.makeText(ctx, "Tarjicoins insuficientes 1", Toast.LENGTH_LONG).show();
            }

        }else if(cash >= 20){
            if (coins <= userSession.getTotal_fichas()) {
                Intent mIntent = new Intent(ctx, OrderRegisterOne.class);
                mIntent.putExtra("cash",cash);
                mIntent.putExtra("coins", coins);
                mIntent.putExtra("cantidad", cantidad);
                ctx.startActivity(mIntent);
            }else {
                if(cash < 20 || (userSession.getTotal_fichas() < coins ) ){
                    if (userSession.getTotal_fichas() > coins){
                        Toast.makeText(ctx, "El monto minimo de compra es S/20.00", Toast.LENGTH_LONG).show();
                    }else if (cash < 20){
                        Toast.makeText(ctx, "Fichas Tarjicoins 2", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Intent mIntent = new Intent(ctx, OrderRegisterOne.class);
                    mIntent.putExtra("cash",cash);
                    mIntent.putExtra("coins", coins);
                    mIntent.putExtra("cantidad", cantidad);
                    ctx.startActivity(mIntent);
                }
            }
        }else {
            Toast.makeText(ctx, "El monto mínimo para hacer un pedido es de: S/ 20.00", Toast.LENGTH_SHORT).show();
        }

    }

    public static void notifiyChanges(){
        Log.d("STATIC NOTIFY", "change");
        reloadPage();
    }

    public static final void reloadPage(){
        Log.d("reloadPage", "change");
        ReserveActivity activity2 = (ReserveActivity) activity;
        activity2.verifyShoppingCar();
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
