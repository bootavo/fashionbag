package info.fashion.bag.modules.menu.home.saerch_product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.Product;
import info.fashion.bag.modules.menu.catalogue.adapters.ProductAdapter;
import info.fashion.bag.modules.menu.product_detail.ProductDetailActivity;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GridSpacingItemDecoration;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.ProgressDialogHelper;
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
    private ProgressDialogHelper mPD;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initButterKnife();
        getExtras();
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

        btnSearch.setOnClickListener(this);

        mPD = new ProgressDialogHelper(ctx);
    }

    public void getExtras(){
        mSearch.setText(getIntent().getStringExtra("product_name"));
        mSearch.setSelection(getSearch().length());
        verifySearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            Call<JsonRequest> mCall = mInterface.getProductByFilter(getSearch());
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    mPBProducts.setVisibility(View.GONE);

                    if(response.body().getResults().getProducts().size() == 0){
                        mSearching.setText("No se encontraron resultados");
                    }else{
                        mSearching.setText("Resultados:");
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(new ProductAdapter(response.body().getResults().getProducts(), new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                Product product = (Product) o;
                                Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                                mIntent.putExtra("product_type", "P");
                                mIntent.putExtra("id_generic_pp", product.getId_producto());
                                ctx.startActivity(mIntent);
                            }
                        }, ctx, 12));
                    }
                    //mRecyclerView.setHasFixedSize(true);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    mSearching.setText("No se encontraron resultados");
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mSearching.setText("Vuelva a intentarlo");
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search:
                verifySearch();
                break;
        }
    }
}
