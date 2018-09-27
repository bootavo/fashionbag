package com.tarjetic.store.modules.menu.orders.adapters;

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
import com.tarjetic.store.R;
import com.tarjetic.store.listeners.OnItemClickListener;
import com.tarjetic.store.models.Order;
import com.tarjetic.store.utilities.Constant;

/**
 * Created by gtufinof on 3/12/18.
 */

public class OrderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_picture_reserve) CircleImageView mPicture;
    @BindView(R.id.tv_date) TextView mDate;
    @BindView(R.id.tv_name) TextView mName;
    @BindView(R.id.tv_state) TextView mState;
    @BindView(R.id.tv_coins_gain) TextView mCoinsGain;
    @BindView(R.id.tv_quantity) TextView mQuantity;
    @BindView(R.id.tv_total_coins) TextView mTotalCoins;
    @BindView(R.id.tv_total_price) TextView mTotalMoney;

    private Context ctx;
    private List<Order> mList;

    public OrderHolder(View itemView, List<Order> mList, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        this.mList = mList;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final Order order, @NonNull final OnItemClickListener listener){

        if (order.getEstado().equals(Constant.ORDER_PENDANT)){
            mPicture.setBorderColor(ctx.getResources().getColor(R.color.stars));
            mState.setBackground(ctx.getResources().getDrawable(R.drawable.bg_order_pendant));
        }else if (order.getEstado().equals(Constant.ORDER_DONE)){
            mPicture.setBorderColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
            mState.setBackground(ctx.getResources().getDrawable(R.drawable.bg_order_done));
        }else if (order.getEstado().equals(Constant.ORDER_CANCEL)){
            mPicture.setBorderColor(ctx.getResources().getColor(R.color.red));
            mState.setBackground(ctx.getResources().getDrawable(R.drawable.bg_order_cancel));
        }

        mName.setText("Pedido Tarjetic- #"+order.getId_usuario()+order.getId_carrito_compra()+order.getId_pedido());
        mState.setText(order.getEstado());
        mDate.setText(order.getFecha());

        if(order.getPago_efectivo() != 0){
            int coins = (Constant.getCoinsByOrderDone(order.getPago_efectivo()));
            mCoinsGain.setText(""+coins);
            mTotalMoney.setText("S/."+order.getPago_efectivo());
        }else{
            mCoinsGain.setText("-");
            mTotalMoney.setText("-");

        }

        if(order.getPago_fichas() != 0){
            mTotalCoins.setText(""+order.getPago_fichas());
        }else{
            mTotalCoins.setText("-");
        }

        mQuantity.setText(""+order.getCantidad());

    }

}
