package com.fashionbag.modules.menu.home.Products;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fashionbag.R;
import com.fashionbag.apis.ApiRetrofitClient;
import com.fashionbag.interfaces.OnItemClickListener;
import com.fashionbag.interfaces.ProductsInterface;
import com.fashionbag.models.JsonProducts;
import com.fashionbag.models.Product;
import com.fashionbag.utilities.GridSpacingItemDecoration;
import com.fashionbag.utilities.JsonPretty;
import com.fashionbag.utilities.NetworkHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/13/18.
 */

public class JewelOfferFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private String TAG = JewelOfferFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jewel_offers, container, false);
        ctx = container.getContext();
        ButterKnife.bind(this, view);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        initCollapsingToolbar();
        service();

        return view;
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void service(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonProducts> mCall = mInterface.getOfferJewels();
            mCall.enqueue(new Callback<JsonProducts>() {
                @Override
                public void onResponse(Call<JsonProducts> call, Response<JsonProducts> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(new ProductAdapter(response.body().getResults(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Product product = (Product) o;
                            Toast.makeText(ctx, product.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }, ctx));

                    //mRecyclerView.setHasFixedSize(true);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonProducts> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

}
