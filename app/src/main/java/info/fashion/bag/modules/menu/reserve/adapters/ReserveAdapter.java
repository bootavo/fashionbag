package info.fashion.bag.modules.menu.reserve.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Products;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ReserveAdapter extends RecyclerView.Adapter<ReserveHolder> {

    private List<Products> mListProducts;
    private OnItemClickListener listener;
    private Context ctx;

    public ReserveAdapter(List<Products> mListProducts, OnItemClickListener listener, Context ctx) {
        this.mListProducts = mListProducts;
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    public ReserveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reserve, parent, false);
        return new ReserveHolder(v, mListProducts, ctx);
    }

    @Override
    public void onBindViewHolder(ReserveHolder holder, int position) {
        holder.bind(mListProducts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mListProducts.size();
    }
}
