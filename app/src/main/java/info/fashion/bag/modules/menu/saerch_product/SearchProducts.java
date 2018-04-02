package info.fashion.bag.modules.menu.saerch_product;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.DiscountOfferInterface;
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.JsonProducts;
import info.fashion.bag.models.JsonUniqueDiscountOffer;
import info.fashion.bag.models.Products;
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
 * Created by gtufinof on 3/30/18.
 */

public class SearchProducts extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view_bags) RecyclerView mRecyclerView;
    @BindView(R.id.pb_bags_offers) ProgressBar mPBProducts;

    @BindView(R.id.tv_searching) TextView mSearching;
    @BindView(R.id.et_search) EditText mSearch;
    @BindView(R.id.btn_search) ImageButton btnSearch;

    private String TAG = SearchProducts.class.getSimpleName();
    private SharedPreferencesHelper mSP;
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initButterKnife();
        init();
        eventUI();
        getExtras();
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

        btnSearch.setOnClickListener(this);

        mPD = new ProgressDialogHelper(ctx);
        mSP = new SharedPreferencesHelper(ctx);
    }

    public void getExtras(){
        mSearch.setText(getIntent().getStringExtra("product_name"));
        mSearch.setSelection(getSearch().length());
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetUniqueDiscountOffer();
        verifyUniqueDiscounOffer();
    }

    public void resetUniqueDiscountOffer(){
        Constant.ACTIVE_PERCENT = false;
        Constant.ACTIVE_AMOUNT = false;
        Constant.DISCOUNT_AMOUNT = 0.0f;
        Constant.DISCOUNT_PERCENT = 0.0f;
    }

    public String getSearch(){
        return mSearch.getText().toString();
    }

    public void verifySearch(){
        if(getSearch() == null || getSearch().equals("")){
            Toast.makeText(ctx, "Ingrese el nombre de algún producto", Toast.LENGTH_SHORT).show();
        }else{
            service();
        }
    }

    public void service(){
        mSearching.setText("Buscando productos...");
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonProducts> mCall = mInterface.getSearchResult(getSearch());
            mCall.enqueue(new Callback<JsonProducts>() {
                @Override
                public void onResponse(Call<JsonProducts> call, Response<JsonProducts> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    //ProductsRealm productsRealm = new ProductsRealm(Realm.getDefaultInstance());
                    //productsRealm.setListProducts(response.body().getResults());

                    mPBProducts.setVisibility(View.GONE);

                    if(response.body().getCount() == 0){
                        mSearching.setText("No se encontraron resultados");
                    }else{
                        mSearching.setText("Resultados:");
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
                    }
                    //mRecyclerView.setHasFixedSize(true);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonProducts> call, Throwable t) {
                    mSearching.setText("No se encontraron resultados");
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mSearching.setText("Vuelva a intentarlo");
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyUniqueDiscounOffer(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            DiscountOfferInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(DiscountOfferInterface.class);
            Call<JsonUniqueDiscountOffer> mCall = mInterface.getDiscountOffer();
            mCall.enqueue(new Callback<JsonUniqueDiscountOffer>() {
                @Override
                public void onResponse(Call<JsonUniqueDiscountOffer> call, Response<JsonUniqueDiscountOffer> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));
                    if(response.body().getResults().size()>0){
                        Log.d(TAG, "Hay ofertas");
                        if(response.body().getResults().get(0).isActive_percent()){
                            Log.d(TAG, "Cierra puertas en Porcentaje están activadas");
                            List<String> listString = new ArrayList<>(Arrays.asList(response.body().getResults().get(0).getCategories().split(" , ")));

                            Log.d(TAG, "ListString: "+listString);

                            Constant.ACTIVE_PERCENT = true;
                            Constant.DISCOUNT_PERCENT = response.body().getResults().get(0).getDiscount_percent();

                            List<Integer> listCategories = new ArrayList<>();
                            for(String s : listString) {
                                listCategories.add(Integer.valueOf(Integer.parseInt(s)));
                            }
                            Constant.CATEGORIES_DISCOUNT_OFFER = listCategories;
                        }else{
                            Log.d(TAG, "Cierra puertas en Monto están activadas");
                            List<String> listString = new ArrayList<>(Arrays.asList(response.body().getResults().get(0).getCategories().split(" , ")));

                            Log.d(TAG, "ListString: "+listString);

                            Constant.ACTIVE_AMOUNT = true;
                            Constant.DISCOUNT_AMOUNT = response.body().getResults().get(0).getDiscount_amount();

                            List<Integer> listCategories = new ArrayList<>();
                            for(String s : listString) {
                                listCategories.add(Integer.valueOf(Integer.parseInt(s)));
                            }
                            Constant.CATEGORIES_DISCOUNT_OFFER = listCategories;
                        }
                        Log.d(TAG, "Service");
                        service();
                    }else{
                        Log.d(TAG, "No hay Cierra Puertas");
                        Log.d(TAG, "Service");
                        service();
                    }
                }

                @Override
                public void onFailure(Call<JsonUniqueDiscountOffer> call, Throwable t) {
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Service");
                    service();
                }
            });
        }else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search:

                break;
        }
    }
}
