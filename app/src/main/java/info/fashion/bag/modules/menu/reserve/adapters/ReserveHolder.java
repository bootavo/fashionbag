package info.fashion.bag.modules.menu.reserve.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Order;
import info.fashion.bag.models.ShoppingCarItem;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ReserveHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_picture_reserve) CircleImageView mPicture;
    @BindView(R.id.tv_category) TextView mCategory;
    @BindView(R.id.tv_name) TextView mName;
    @BindView(R.id.tv_quantity) TextView mQuantity;
    @BindView(R.id.tv_price) TextView mPrice;
    @BindView(R.id.tv_sub_total_price) TextView mSubTotal;
    @BindView(R.id.btn_delete) Button btnDelete;

    private Context ctx;
    private List<ShoppingCarItem> mList;

    public ReserveHolder(View itemView, List<ShoppingCarItem> mList, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        this.mList = mList;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final ShoppingCarItem shoppingCarItem, @NonNull final OnItemClickListener listener){

        if (shoppingCarItem.getItemProduct() != null){
            GlideApp.with(ctx)
                    .load(shoppingCarItem.getItemProduct().get(0).getImagen())
                    .into(mPicture);
            mName.setText("Producto "+shoppingCarItem.getItemProduct().get(0).getNombre());

            if (shoppingCarItem.getTipo_compra().equals(Constant.ORDER_PAY_EFFECTIVE)){
                mPrice.setText("S/."+shoppingCarItem.getItemProduct().get(0).getPrecio());
                mSubTotal.setText("S/."+shoppingCarItem.getPrecio_total());
            }else {
                mPrice.setText("Fichas "+shoppingCarItem.getItemProduct().get(0).getPrecio_fichas());
                mSubTotal.setText("Fichas "+(shoppingCarItem.getCantidad()*shoppingCarItem.getItemProduct().get(0).getPrecio_fichas()));
            }

        }else {
            GlideApp.with(ctx)
                    .load(shoppingCarItem.getItemPromotion().get(0).getImagen())
                    .into(mPicture);
            mName.setText("Promoción "+shoppingCarItem.getItemPromotion().get(0).getNombre());

            if (shoppingCarItem.getTipo_compra().equals(Constant.ORDER_PAY_EFFECTIVE)){
                mPrice.setText("S/."+shoppingCarItem.getItemPromotion().get(0).getPrecio());
                mSubTotal.setText("S/."+shoppingCarItem.getPrecio_total());
            }else {
                mPrice.setText("Fichas "+shoppingCarItem.getItemPromotion().get(0).getPrecio_fichas());
                mSubTotal.setText("Fichas "+(shoppingCarItem.getCantidad()*shoppingCarItem.getItemPromotion().get(0).getPrecio_fichas()));
            }
        }
        mQuantity.setText(shoppingCarItem.getCantidad()+"");

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Delete", Toast.LENGTH_SHORT).show();
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(shoppingCarItem, mList.indexOf(shoppingCarItem));
            }
        });

    }

}
