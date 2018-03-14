package info.fashion.bag.modules.menu.settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.modules.auth.login.LoginActivity;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.SharedPreferencesHelper;

/**
 * Created by gtufinof on 3/11/18.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Nullable @BindView(R.id.tv_name) TextView mName;
    @Nullable @BindView(R.id.iv_picture) CircleImageView mPicture;
    @Nullable @BindView(R.id.btn_login) Button btnLogin;

    private String TAG = SettingsFragment.class.getSimpleName();
    private SharedPreferencesHelper mSP;
    private Context ctx = null;
    private View view = null;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = container.getContext();
        mSP = new SharedPreferencesHelper(ctx);
        if(mSP.getToken().equals("No definido")){
            Log.d(TAG, "no token");
            view = inflater.inflate(R.layout.fragment_need_login, container, false);
        }else{
            Log.d(TAG, "si token");
            view = inflater.inflate(R.layout.fragment_settings, container, false);
            getPatientData();
        }
        ButterKnife.bind(this, view);

        btnLogin.setOnClickListener(this);

        return view;
    }

    public void getPatientData(){
        mName.setText(mSP.getFirstname());

        if (mSP.getProfilePicture().equals("No Definido")) {
            GlideApp
                    .with(ctx)
                    .load(R.drawable.picture_bag)
                    .into(mPicture);
        }else{
            GlideApp
                    .with(ctx)
                    .load(mSP.getProfilePicture())
                    .into(mPicture);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Configuración");
    }

    @Override
    public void onStart() {
        super.onStart();
        callService();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intent = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(intent);
                break;
        }
    }
}
