package info.fashion.bag.modules.menu.reserve.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import info.fashion.bag.R;
import info.fashion.bag.apis.ApiRetrofitClient;
import info.fashion.bag.interfaces.ShoppingCarInterface;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.ShoppingCar;
import info.fashion.bag.models.ShoppingCarItem;
import info.fashion.bag.models.User;
import info.fashion.bag.modules.menu.reserve.ReserveActivity;
import info.fashion.bag.utilities.Constant;
import info.fashion.bag.utilities.GlideApp;
import info.fashion.bag.utilities.JsonPretty;
import info.fashion.bag.utilities.NetworkHelper;
import info.fashion.bag.utilities.PreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ReserveHolder> {

    private List<ShoppingCarItem> mList;
    private OnItemClickListener listener;
    private Context ctx;

    private String TAG = ReserveAdapter.class.getSimpleName();

    public ReserveAdapter(List<ShoppingCarItem> mListProducts, OnItemClickListener listener, Context ctx) {
        this.mList = mListProducts;
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    public ReserveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reserve, parent, false);
        return new ReserveHolder(v, mList, ctx);
    }

    @Override
    public void onBindViewHolder(ReserveHolder holder, final int position) {
        holder.bind(mList.get(position), listener);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList.get(position).getItemProduct() != null){
                    deleteService(mList.get(position).getId_carrito_compra_producto(), position);
                }else{
                    deleteService(mList.get(position).getId_carrito_compra_producto(), position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ReserveHolder extends RecyclerView.ViewHolder {

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

    public void deleteService(int id_carrito_compra_producto, final int position){
        if(NetworkHelper.isNetworkAvailable(ctx)) {
            User user = PreferencesHelper.getMyUserPref(ctx);

            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setId_usuario(user.getId_usuario());

            ShoppingCarInterface mInterface = ApiRetrofitClient.getRetrofitClient().create(ShoppingCarInterface.class);
            Call<JsonRequest> mCall = mInterface.deleteProduct(id_carrito_compra_producto);

            mCall.enqueue(new Callback<JsonRequest>() {
                @Override
                public void onResponse(Call<JsonRequest> call, Response<JsonRequest> response) {
                    //mPD.dimissPD();
                    Log.d(TAG, "Retrofit Response createShoppingCar: " + JsonPretty.getPrettyJson(response));

                    if (response.body().getStatus().getCode() == 200) {
                        Log.d(TAG, "-------> SE ELIMINO EL PRODUCTO");
                        Toast.makeText(ctx, "SE ELIMINO EL PRODUCTO", Toast.LENGTH_SHORT).show();
                        ReserveActivity.notifiyChanges();
                    } else {
                        Log.d(TAG, "SE ELIMINO EL PRODUCTO");
                        Toast.makeText(ctx, "SE ELIMINO EL PRODUCTO", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonRequest> call, Throwable t) {
                    //mPD.dimissPD();
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.server_problems), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void deleteFromRecycleView(int position){
        mList.remove(position);
        ReserveAdapter.this.notifyItemRemoved(position);

    }

}