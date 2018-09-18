package info.fashion.bag.modules.menu.catalogue.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Product;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_picture_product) ImageView mPicture;
    @BindView(R.id.tv_title_product) TextView mTitle;
    @BindView(R.id.tv_price) TextView mPrice;
    @BindView(R.id.tv_coins) TextView mCoins;
    //@BindView(R.id.tv_promo) TextView mPromoLabel;

    private Context ctx;
    private List<Product> mList;

    public ProductHolder(View itemView, List<Product> mList, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        this.mList = mList;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final Product product, @NonNull final OnItemClickListener listener){

        mTitle.setText(product.getNombre());
        mPrice.setText("S/."+product.getPrecio()+"");

        if(product.getPrecio_fichas() == 0){
            mCoins.setText("No aplica");
        }else {
            mCoins.setText(product.getPrecio_fichas()+"");
        }


        if(product.getImagen() == null || product.getImagen().equals("")){
            GlideApp.with(ctx).load(R.drawable.empty_product).into(mPicture);
        }else{
            GlideApp.with(ctx).load(product.getImagen()).into(mPicture);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Holder", "click");
                listener.onItemClick(product, mList.indexOf(product));
                Constant.ID_PRODUCT = product.getId_producto();
                Log.d("Holder", "id2: "+Constant.ID_PRODUCT);

            }
        });

    }

    public String getOfferPerfecnt(float price, float discount_price) {

        float result = 100.0f - (discount_price * 100 / price);
        result = Math.round(result);

        return "- "+result+"%";
    }

}