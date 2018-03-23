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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.DiscountOfferInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.models.JsonProducts;
import info.fashion.bag.models.JsonUniqueDiscountOffer;
import info.fashion.bag.models.Products;
import info.fashion.bag.modules.menu.catalogue.adapters.ProductAdapter;
import info.fashion.bag.modules.menu.product_detail.ProductDetailActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GridSpacingItemDecoration;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/13/18.
 */

public class BagsFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private String TAG = BagsFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    public static BagsFragment newInstance(){
        return new BagsFragment();
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
            verifyUniqueDiscounOffer();
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

    public void localService(List<Products> products){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10, ctx), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new ProductAdapter(products, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                Products products = (Products) o;
                Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                ctx.startActivity(mIntent);
            }
        }, ctx));
    }

    public void service(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonProducts> mCall = mInterface.getOffersBags();
            mCall.enqueue(new Callback<JsonProducts>() {
                @Override
                public void onResponse(Call<JsonProducts> call, Response<JsonProducts> response) {
                    Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    //ProductsRealm productsRealm = new ProductsRealm(Realm.getDefaultInstance());
                    //productsRealm.setListProducts(response.body().getResults());

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

}
