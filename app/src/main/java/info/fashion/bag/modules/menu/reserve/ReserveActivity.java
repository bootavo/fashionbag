package info.fashion.bag.modules.menu.reserve;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.ShoppingCarInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.ShoppingCarItem;
import info.fashion.bag.models.User;
import info.fashion.bag.modules.menu.reserve.adapters.ReserveAdapter;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GridSpacingItemDecoration;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.PreferencesHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/11/18.
 */

public class ReserveActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view_reserve) RecyclerView mRecyclerView;
    @BindView(R.id.pb_reserve) ProgressBar mPBProducts;

    @BindView(R.id.tv_reserve) TextView mSearching;

    private String TAG = ReserveActivity.class.getSimpleName();
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    private int id_carrito_compra = 0;

    public static ReserveActivity newInstance(){
        return new ReserveActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        initButterKnife();
        init();
        eventUI();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        verifyShoppingCar();
    }

    public void verifyShoppingCar(){
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

                    if(response.body().getResults().getShopping_car() != null){
                        Log.d(TAG, "-------> TIENE RESERVAS");

                        if(!response.body().getResults().getShopping_car().isEmpty()){
                            id_carrito_compra = response.body().getResults().getShopping_car().get(0).getId_carrito_compra();
                            getReserves();
                        }else {
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
                    mSearching.setVisibility(View.VISIBLE);
                    mSearching.setText("No se han podido cargar las reservas");
                    mPD.dimissPD();
                }
            });
        }else{
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

                    float cash = 0;
                    int coins = 0;

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

                    }

                    Toast.makeText(ctx, "Efectivo: "+cash+", Fichas: "+coins, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Efectivo: "+cash+", Fichas: "+coins);

                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mSearching.setVisibility(View.VISIBLE);
                    mSearching.setText("No se han podido cargar las reservas");
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            mSearching.setVisibility(View.VISIBLE);
            mSearching.setText("No se han podido cargar las reservas");
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

}
