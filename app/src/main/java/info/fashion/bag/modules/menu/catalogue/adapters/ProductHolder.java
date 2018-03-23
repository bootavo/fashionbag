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
import info.fashion.bag.models.Products;
import info.fashion.bag.utilities.Constant;
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
    @BindView(R.id.tv_promo) TextView mPromoLabel;

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

        float discount_price = 0.0f;
        if(Constant.ACTIVE_PERCENT){
            Log.d("PORCENTAJE",""+Constant.CATEGORIES_DISCOUNT_OFFER);
            Log.d("CATEGORIE", ""+products.getCategory());
            if(Constant.CATEGORIES_DISCOUNT_OFFER.contains(products.getCategory().getId())){
                Log.d("Category: ", "ENTRO AL PRIMER IF DE PORCENTAJE®");
                discount_price = products.getProduct().getSuggested_price()*Constant.DISCOUNT_PERCENT/100.0f;
                mPriceWholeSale.setText("S/."+discount_price+"");
                Constant.OFFER_PRICE = discount_price;

                mPromoLabel.setVisibility(View.VISIBLE);
                mPromoLabel.setText(getOfferPerfecnt(products.getProduct().getSuggested_price(), discount_price));
            }
        }else if(Constant.ACTIVE_AMOUNT){
            Log.d("MONTO",""+Constant.CATEGORIES_DISCOUNT_OFFER);
            if(Constant.CATEGORIES_DISCOUNT_OFFER.contains(products.getCategory().getId())){
                discount_price = products.getProduct().getSuggested_price()-Constant.DISCOUNT_AMOUNT;
                mPriceWholeSale.setText("S/."+discount_price+"");
                Constant.OFFER_PRICE = discount_price;
                mPromoLabel.setVisibility(View.VISIBLE);
                mPromoLabel.setText(getOfferPerfecnt(products.getProduct().getSuggested_price(), discount_price));
            }
        }else{
            Log.d("NORMAL",""+Constant.CATEGORIES_DISCOUNT_OFFER);
            if(products.getProduct().getOnly_price()>0.0f){
                Log.d("NORMAL CON OFERTA",""+Constant.CATEGORIES_DISCOUNT_OFFER);
                mPriceWholeSale.setText("S/."+products.getProduct().getOnly_price()+"");
                Constant.OFFER_PRICE = products.getProduct().getOnly_price();
                mPromoLabel.setVisibility(View.VISIBLE);
                mPromoLabel.setText(getOfferPerfecnt(products.getProduct().getSuggested_price(), products.getProduct().getOnly_price()));
            }else {
                Log.d("NORMAL SIN OFERTA",""+Constant.CATEGORIES_DISCOUNT_OFFER);
                mPriceWholeSale.setText("S/."+products.getProduct().getSale_price()+"");
                Constant.OFFER_PRICE = products.getProduct().getSale_price();
            }
        }

        Constant.ID_PRODUCT = products.getId();
        Log.d("Holder", "id: "+Constant.ID_PRODUCT);
        mPriceUnitary.setText("S/."+products.getProduct().getSuggested_price()+"");

        Constant.SUGGESTED_PRICE = products.getProduct().getSuggested_price();

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
                Log.d("Holder", "click");
                listener.onItemClick(products, mListProducts.indexOf(products));
                Constant.ID_PRODUCT = products.getId();
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
