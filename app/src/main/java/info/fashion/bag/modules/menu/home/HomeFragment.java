package info.fashion.bag.modules.menu.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.PromotionInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.interfaces.ProductsInterface;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.Product;
import info.fashion.bag.models.Promotion;
import info.fashion.bag.models.User;
import info.fashion.bag.modules.auth.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.modules.menu.catalogue.adapters.ProductAdapter;
import info.fashion.bag.modules.menu.catalogue.adapters.PromotionAdapter;
import info.fashion.bag.modules.menu.product_detail.ProductDetailActivity;
import info.fashion.bag.modules.menu.home.saerch_product.SearchProducts;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.PreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/11/18.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.ll_auth) LinearLayout llAuth;
    @BindView(R.id.btn_login) Button btnLogin;

    @BindView(R.id.et_search) EditText mSearch;
    @BindView(R.id.btn_search) ImageButton btnSearch;

    @BindView(R.id.recycler_view_bags) RecyclerView mRecyclerViewBags;
    @BindView(R.id.recycler_view_jewels) RecyclerView mRecyclerViewJewels;

    @BindView(R.id.pb_bags_offers) ProgressBar mPBBags;
    @BindView(R.id.pb_jewels_offers) ProgressBar mPBJewels;

    private String TAG = HomeFragment.class.getSimpleName();
    private Context ctx = null;
    private View view = null;

    ViewPager viewPager;
    LinearLayout sliderDots;
    public int dotCounts;
    public ImageView[] dots;

    Timer timer;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ctx = container.getContext();
        ButterKnife.bind(this, view);

        btnSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        sliderDots = (LinearLayout) view.findViewById(R.id.sliderDots);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(ctx);
        viewPager.setAdapter(viewPageAdapter);
        dotCounts=viewPageAdapter.getCount();
        dots = new ImageView[dotCounts];

        verifyUserLogin();

        return view;
    }

    public String getSearch(){
        return mSearch.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*
            case R.id.btn_jewel_offers:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new CatalogFragment(), CatalogFragment.class.getSimpleName())
                        .commit();
                break;
            case R.id.btn_bags_offers:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new CatalogFragment(), CatalogFragment.class.getSimpleName())
                        .commit();
                break;
                */
            case R.id.btn_login:
                Intent intent = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(intent);
                break;
            case R.id.btn_search:
                verifySearch();
                break;
        }
    }

    public void verifySearch(){
        if(getSearch() == null || getSearch().equals("")){
            Toast.makeText(ctx, "Ingrese el nombre de algún producto", Toast.LENGTH_SHORT).show();
        }else{
            nextActivity();
        }
    }

    public void nextActivity(){
        Intent mIntent = new Intent(ctx, SearchProducts.class);
        mIntent.putExtra("product_name", getSearch());
        startActivity(mIntent);
    }

    public class myTimerTask extends TimerTask {
        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Inicio");

        for(int i=0;i<dotCounts;i++){
            dots[i]=new ImageView(ctx);
            dots[i].setImageDrawable(ContextCompat.getDrawable(ctx,R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotCounts; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(), 4000 ,4000);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "----> onStart");
        callPromocions();
        callProducts();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "----> onStop");
        timer.cancel();
        timer.purge();
    }

    public void callPromocions(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            PromotionInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(PromotionInterface.class);
            Call<JsonRequest> mCall = mInterface.getPromotiones();
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, final Response<JsonRequest> response) {
                    //Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    mPBBags.setVisibility(View.GONE);

                    //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
                    mRecyclerViewBags.setLayoutManager(horizontalLayoutManagaer);
                    //mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    mRecyclerViewBags.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerViewBags.setHasFixedSize(true);
                    mRecyclerViewBags.setAdapter(new PromotionAdapter(response.body().getResults().getPromotions(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Promotion promotion = (Promotion) o;
                            Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                            mIntent.putExtra("product_type", "S");
                            mIntent.putExtra("id_generic_pp", promotion.getId_promocion());
                            ctx.startActivity(mIntent);
                        }
                    }, ctx));

                    //mRecyclerViewJewels.setHasFixedSize(true);
                    //mRecyclerViewJewels.getLayoutManager().setMeasurementCacheEnabled(false);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    public void callProducts(){
        if(NetworkHelper.isNetworkAvailable(ctx)){
            ProductsInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ProductsInterface.class);
            Call<JsonRequest> mCall = mInterface.getProducts();
            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, final Response<JsonRequest> response) {
                    //Log.d(TAG, "Retrofit Response: "+ JsonPretty.getPrettyJson(response));

                    mPBJewels.setVisibility(View.GONE);

                    //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
                    mRecyclerViewJewels.setLayoutManager(horizontalLayoutManagaer);
                    //mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    mRecyclerViewJewels.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerViewJewels.setHasFixedSize(true);
                    mRecyclerViewJewels.setAdapter(new ProductAdapter(response.body().getResults().getProducts(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Product product = (Product) o;
                            Intent mIntent = new Intent(ctx, ProductDetailActivity.class);
                            mIntent.putExtra("product_type", "P");
                            mIntent.putExtra("id_generic_pp", product.getId_producto());
                            ctx.startActivity(mIntent);
                        }
                    }, ctx));

                    //mRecyclerViewJewels.setHasFixedSize(true);
                    //mRecyclerViewJewels.getLayoutManager().setMeasurementCacheEnabled(false);
                    //mRecyclerView.setNestedScrollingEnabled(false);
                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    Toast.makeText(ctx, getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ctx, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        verifyUserLogin();
        Log.d(TAG, "----> onResume");
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

    public void verifyUserLogin(){
        User user = PreferencesHelper.getMyUserPref(ctx);
        if(user == null){
            llAuth.setVisibility(View.VISIBLE);
        }else {
            llAuth.setVisibility(View.GONE);
        }
    }

}
