package info.fashion.bag.modules.auth.splash;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import info.fashion.bag.R;
import info.fashion.bag.utilities.BaseActivity;
import info.fashion.bag.modules.menu.MainApp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gtufinof on 3/12/18.
 */

public class SplashActivity extends BaseActivity{

    @BindView(R.id.iv_logo) ImageView mLogo;

    private String TAG = SplashActivity.class.getSimpleName();
    private Context ctx = this;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " ----------------> SplashActivity");
        setContentView(R.layout.activity_splash);
        initButterKnife();
        init();
        hideStatusBar();
    }

    public void initButterKnife(){
        ButterKnife.bind(this);
    }

    public void init(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x/4;
        int height = size.y;

        //Animation anim = new TranslateAnimation(0, 0, height/2, 0);
        //anim.setStartOffset(100);
        //anim.setInterpolator(new DecelerateInterpolator());
        //anim.setFillEnabled(true);
        //anim.setFillAfter(true);
        //anim.setDuration(1000);
        //mLogo.setAnimation(anim);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 1000);
    }

    public void hideStatusBar(){
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public void nextActivity(){
        Log.d(TAG, " ----------------> nextActivity");
        next(MainApp.class, true);
    }

}
