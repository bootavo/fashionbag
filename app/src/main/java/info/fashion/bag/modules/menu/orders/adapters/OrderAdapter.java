package info.fashion.bag.modules.menu.orders.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Order;

/**
 * Created by gtufinof on 3/12/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    private List<Order> mList;
    private OnItemClickListener listener;
    private Context ctx;

    public OrderAdapter(List<Order> mListProducts, OnItemClickListener listener, Context ctx) {
        this.mList = mListProducts;
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_orders, parent, false);
        return new OrderHolder(v, mList, ctx);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        holder.bind(mList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
