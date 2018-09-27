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

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.PromotionInterface;
import com.tarjetic.store.listeners.OnItemClickListener;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.Promotion;
import com.tarjetic.store.modules.menu.catalogue.adapters.PromotionAdapter;
import com.tarjetic.store.modules.menu.product_detail.ProductDetailActivity;
import com.tarjetic.store.utilities.Constant;
import com.tarjetic.store.utilities.GridSpacingItemDecoration;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/13/18.
 */

public class PromotionFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @BindView(R.id.pb_products_offers) ProgressBar mPBProducts;

    private String TAG = PromotionFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    public static PromotionFragment newInstance(){
        return new PromotionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bags, container, false);
        ctx = container.getContext();
        ButterKnife.bind(this, view);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        initCollapsingToolbar();
        service();

        return view;
    }

    public void resetUniqueDiscountOffer(){
        Constant.ACTIVE_PERCENT = false;
        Constant.ACTIVE_AMOUNT = false;
        Constant.DISCOUNT_AMOUNT = 0.0f;
        Constant.DISCOUNT_PERCENT = 0.0f;
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

        //ProductsRealm productsRealm = new ProductsRealm(Realm.getDefaultInstance());
        //List<Products> listBagsOffers = productsRealm.getBagsOffersRealm();

        //if(listBagsOffers.size()>0){
        //    localService(listBagsOffers);
        //}else{
            resetUniqueDiscountOffer();
        //}

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
            PromotionInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(PromotionInterface.class);
            Call<JsonRequest> mCall = mInterface.getPromotiones();
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    //ProductsRealm productsRealm = new ProductsRealm(Realm.getDefaultInstance());
                    //productsRealm.setListProducts(response.body().getResults());

                    mPBProducts.setVisibility(View.GONE);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(new PromotionAdapter(response.body().getResults().getPromotions(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Promotion promotion = (Promotion) o;
                            Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                            mIntent.putExtra("product_type", "S");
                            mIntent.putExtra("id_generic_pp", promotion.getId_promocion());
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
