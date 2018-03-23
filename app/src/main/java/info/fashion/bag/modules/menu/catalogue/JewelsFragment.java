package info.fashion.bag.modules.menu.catalogue;

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
import android.widget.Toast;

import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.models.JsonProducts;
import info.fashion.bag.models.Products;
import info.fashion.bag.modules.menu.catalogue.adapters.ProductAdapter;
import info.fashion.bag.modules.menu.catalogue.product_detail.ProductDetailActivity;
import info.fashion.bag.utilities.GridSpacingItemDecoration;
import info.fashion.bag.utilities.NetworkHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/13/18.
 */

public class JewelsFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private String TAG = JewelsFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    public static JewelsFragment newInstance(){
        return new JewelsFragment();
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
            Call<JsonProducts> mCall = mInterface.getOfferJewels();
            mCall.enqueue(new Callback<JsonProducts>() {
                @Override
                public void onResponse(Call<JsonProducts> call, Response<JsonProducts> response) {
                    //Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

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

                    //mRecyclerView.setHasFixedSize(true);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonProducts> call, Throwable t) {
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

}
