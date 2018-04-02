package info.fashion.bag.modules.menu.reserve;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.SalesOrdersInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.JsonSalesOrders;
import info.fashion.bag.models.JsonSalesOrdersDetails;
import info.fashion.bag.models.Products;
import info.fashion.bag.models.SalesOrdersDatails;
import info.fashion.bag.modules.menu.catalogue.adapters.ProductAdapter;
import info.fashion.bag.modules.menu.product_detail.ProductDetailActivity;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GridSpacingItemDecoration;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
import info.fashion.bag.utilities.SharedPreferencesHelper;
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
    private SharedPreferencesHelper mSP;
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    private int altura;

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
        mSP = new SharedPreferencesHelper(ctx);
    }

    @Override
    protected void onStart() {
        super.onStart();
        callService();
    }

    public void callService(){
        mPD.showPD();
        mSearching.setText("Cargando reservas...");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<JsonSalesOrders> mCall = mInterface.getSalesOrders(mSP.getToken());
            mCall.enqueue(new Callback<JsonSalesOrders>() {
                @Override
                public void onResponse(Call<JsonSalesOrders> call, Response<JsonSalesOrders> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response sales_order: "+JsonPretty.getPrettyJson(response));

                    mSearching.setText("Reservas:");

                    mPBProducts.setVisibility(View.GONE);


                    //Encontrando el SALES ORDER ID
                    int sales_order_id = 0;
                    String reserve = "";
                    for (int i=0; i<response.body().getResults().size(); i++){
                        if(response.body().getResults().get(i).getStatus().equals("RESERVA")){
                            reserve = response.body().getResults().get(i).getStatus();
                            sales_order_id = response.body().getResults().get(i).getId();
                            Log.d(TAG, "------> SALES ORDER ID: "+sales_order_id);
                            break;
                        }
                    }

                    if(reserve.equals("RESERVA")){
                        Log.d(TAG, "-------> TIENE RESERVAS");
                        getReserves(sales_order_id);
                    }else {
                        Log.d(TAG, "NO TIENE RESERVAS");
                        mSearching.setText("No cuenta con reservas");
                    }

                    /*
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(new ProductAdapter(response.body().getResults(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Products products = (Products) o;
                            Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                            ctx.startActivity(mIntent);
                        }
                    }, ctx));
                    */

                }

                @Override
                public void onFailure(Call<JsonSalesOrders> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mSearching.setText("No se han podido cargar las reservas");
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            mSearching.setText("No se han podido cargar las reservas");
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void getReserves(int id){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            SalesOrdersInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(SalesOrdersInterface.class);
            Call<JsonSalesOrdersDetails> mCall = mInterface.getReserve(mSP.getToken(), id);
            mCall.enqueue(new Callback<JsonSalesOrdersDetails>() {
                @Override
                public void onResponse(Call<JsonSalesOrdersDetails> call, Response<JsonSalesOrdersDetails> response) {
                    mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response sales_order: "+JsonPretty.getPrettyJson(response));

                    mSearching.setText("Reservas:");

                    mPBProducts.setVisibility(View.GONE);

                    List<SalesOrdersDatails> listVariants = null;
                    listVariants = response.body().getResults();

                    /*
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(new ProductAdapter(response.body().getResults(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Products products = (Products) o;
                            Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                            ctx.startActivity(mIntent);
                        }
                    }, ctx));
                    */

                }

                @Override
                public void onFailure(Call<JsonSalesOrdersDetails> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    mSearching.setText("No se han podido cargar las reservas");
                    mPD.dimissPD();
                }
            });
        }else{
            mPD.dimissPD();
            mSearching.setText("No se han podido cargar las reservas");
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

}
