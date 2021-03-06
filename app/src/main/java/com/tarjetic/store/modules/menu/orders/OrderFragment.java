package com.tarjetic.store.modules.menu.orders;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tarjetic.store.R;
import com.tarjetic.store.apis.ApiRetrofitClient;
import com.tarjetic.store.interfaces.OrderInterface;
import com.tarjetic.store.listeners.OnItemClickListener;
import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.Order;
import com.tarjetic.store.models.User;
import com.tarjetic.store.modules.menu.orders.adapters.OrderAdapter;
import com.tarjetic.store.utilities.GridSpacingItemDecoration;
import com.tarjetic.store.utilities.JsonPretty;
import com.tarjetic.store.utilities.NetworkHelper;
import com.tarjetic.store.utilities.PreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/13/18.
 */

public class OrderFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @BindView(R.id.pb_products_offers) ProgressBar mPBProducts;

    @BindView(R.id.tv_order) TextView mOrderMessage;

    private String TAG = OrderFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    public static OrderFragment newInstance(){
        return new OrderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        ctx = container.getContext();
        ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        initCollapsingToolbar();
        service();

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
                    collapsingToolbar.setTitle("Tus pedidos");
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
            User user = PreferencesHelper.getMyUserPref(ctx);
            Log.d(TAG, "verifyShoppingCar: "+user.getId_usuario());
            OrderInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(OrderInterface.class);
            Call<JsonRequest> mCall = mInterface.getOrdersByUsuario(user.getId_usuario());
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    if (!response.body().getResults().getOrders().isEmpty()){
                        mPBProducts.setVisibility(View.GONE);

                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 1);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, GridSpacingItemDecoration.dpToPx(0, ctx), true));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(new OrderAdapter(response.body().getResults().getOrders(), new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                Order order = (Order) o;
                                Toast.makeText(ctx, ""+order.getId_pedido(), Toast.LENGTH_SHORT).show();
                                //Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                                //ctx.startActivity(mIntent);
                            }
                        }, ctx));

                        //mRecyclerView.setHasFixedSize(true);
                        //mRecyclerView.setNestedScrollingEnabled(false);
                    }else{
                        mOrderMessage.setVisibility(View.VISIBLE);
                        mPBProducts.setVisibility(View.GONE);
                    }


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
