package info.fashion.bag.modules.menu.catalogue.product_detail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.fashion.bag.R;
import info.fashion.bag.utilities.BaseActivity;

/**
 * Created by gtufinof on 3/14/18.
 */

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_call) Button btnCall;

    private String TAG = ProductDetailActivity.class.getSimpleName();
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initButterKnide();
        init();
    }

    public void init(){
        btnCall.setOnClickListener(this);
    }

    public void initButterKnide(){
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_call:
                Toast.makeText(ctx, "Llamando", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
