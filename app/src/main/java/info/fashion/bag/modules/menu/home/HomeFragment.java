package info.fashion.bag.modules.menu.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import info.fashion.bag.R;
import info.fashion.bag.modules.auth.login.LoginActivity;
import info.fashion.bag.modules.menu.home.products.OffersFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gtufinof on 3/11/18.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.btn_search) ImageButton btnSearch;
    @BindView(R.id.btn_jewel_offers) CardView btnJewelOffers;
    @BindView(R.id.btn_bags_offers) CardView btnBagsOffers;

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

        btnBagsOffers.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnJewelOffers.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        sliderDots = (LinearLayout) view.findViewById(R.id.sliderDots);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(ctx);
        viewPager.setAdapter(viewPageAdapter);
        dotCounts=viewPageAdapter.getCount();
        dots = new ImageView[dotCounts];

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_jewel_offers:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new OffersFragment(), OffersFragment.class.getSimpleName())
                        .commit();
                break;
            case R.id.btn_bags_offers:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new OffersFragment(), OffersFragment.class.getSimpleName())
                        .commit();
                break;
            case R.id.btn_login:
                Intent intent = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(intent);
                break;
            case R.id.btn_search:
                Toast.makeText(ctx, "Buscando", Toast.LENGTH_SHORT).show();
                break;
        }
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
        callService();
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void callService(){

    }

}
