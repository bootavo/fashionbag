package info.fashion.bag.modules.menu.reserve.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Products;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ReserveHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_picture_product) ImageView mPictureProduct;
    @BindView(R.id.iv_picture_reserve) TextView mTitleProduct;
    @BindView(R.id.tv_brand) TextView mBrand;
    @BindView(R.id.civ_color) CircleImageView mColor;
    @BindView(R.id.tv_price) TextView mPrice;
    @BindView(R.id.tv_quantity) TextView mQuantity;

    private Context ctx;
    private List<Products> mListProducts;

    public ReserveHolder(View itemView, List<Products> mListProducts, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        this.mListProducts = mListProducts;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final Products products, @NonNull final OnItemClickListener listener){

        mTitleProduct.setText(products.getProduct().getName());

    }

    public String getOfferPerfecnt(float price, float discount_price) {

        float result = 100.0f - (discount_price * 100 / price);
        result = Math.round(result);

        return "- "+result+"%";
    }

}
