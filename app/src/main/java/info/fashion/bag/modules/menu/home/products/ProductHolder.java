package info.fashion.bag.modules.menu.home.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.fashion.bag.R;
import info.fashion.bag.interfaces.OnItemClickListener;
import info.fashion.bag.models.Products;
import info.fashion.bag.utilities.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_picture_product) ImageView mPictureProduct;
    @BindView(R.id.tv_title_product) TextView mTitleProduct;
    @BindView(R.id.tv_price_unitary) TextView mPriceUnitary;
    @BindView(R.id.tv_price_whole_sale) TextView mPriceWholeSale;

    private Context ctx;
    private List<Products> mListProducts;

    public ProductHolder(View itemView, List<Products> mListProducts, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        this.mListProducts = mListProducts;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final Products products, @NonNull final OnItemClickListener listener){

        mTitleProduct.setText(products.getProduct().getName());
        mPriceUnitary.setText("S/."+products.getProduct().getSuggested_price()+"");
        mPriceWholeSale.setText("S/."+products.getProduct().getSale_price()+"");

        GlideApp
                .with(ctx)
                //.asBitmap()
                .load(products.getThumbnail_url())
                .into(mPictureProduct);
                /*
                .into(new SimpleTarget<Bitmap>(500,500) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        mPictureProduct.setImageBitmap(bitmap);
                        //blogs.setBitmap(bitmap);
                        //BitmapHelper.getInstance().setBitmap(bitmap);
                    }
                });
                */

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(products, mListProducts.indexOf(products));
            }
        });

    }

}
