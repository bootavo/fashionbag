package com.tarjetic.store.modules.menu.catalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.listeners.OnItemClickListener;
import com.tarjetic.store.interfaces.ProductsInterface;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.Product;
import com.tarjetic.store.modules.menu.catalogue.adapters.ProductAdapter;
import com.tarjetic.store.modules.menu.product_detail.ProductDetailActivity;
import com.tarjetic.store.utilities.GridSpacingItemDecoration;
import com.tarjetic.store.utilities.NetworkHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/13/18.
 */

public class ProductFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @BindView(R.id.pb_products_offers) ProgressBar mPBProducts;

    @BindView(R.id.menu) FloatingActionMenu fab;
    @BindView(R.id.menu_item_all) FloatingActionButton fabAll;
    @BindView(R.id.menu_item_whisky) FloatingActionButton fabWhisky;
    @BindView(R.id.menu_item_vodka) FloatingActionButton fabVodka;
    @BindView(R.id.menu_item_ron) FloatingActionButton fabRon;
    @BindView(R.id.menu_item_beer) FloatingActionButton fabBeer;
    @BindView(R.id.menu_item_complements) FloatingActionButton fabComplements;

    private int mMaxProgress = 100;

    private String TAG = ProductFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    private boolean flagDecoration = false;

    public static ProductFragment newInstance(){
        return new ProductFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jewels, container, false);
        ctx = container.getContext();
        ButterKnife.bind(this, view);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        initCollapsingToolbar();

        fabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service();
            }
        });

        fabWhisky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceByCategory(1);
            }
        });

        fabVodka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceByCategory(2);
            }
        });

        fabRon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceByCategory(3);
            }
        });

        fabBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceByCategory(4);
            }
        });

        fabComplements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceByCategory(5);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "----> onCreateView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "----> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "----> onResume");
        service();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "----> onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "----> onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "----> onDestroy");
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public void service(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonRequest> mCall = mInterface.getProducts();
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    //Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    mPBProducts.setVisibility(View.GONE);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    if(!flagDecoration){
                        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                        flagDecoration = true;
                    }

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

                    //mRecyclerView.setHasFixedSize(true);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void serviceByCategory(int id_categoria){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonRequest> mCall = mInterface.getProductByCategory(id_categoria);
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    //Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    mPBProducts.setVisibility(View.GONE);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    if(!flagDecoration){
                        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                        flagDecoration = true;
                    }
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

                    //mRecyclerView.setHasFixedSize(true);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

}
