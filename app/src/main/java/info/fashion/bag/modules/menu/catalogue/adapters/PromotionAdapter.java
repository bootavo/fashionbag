package info.fashion.bag.modules.menu.catalogue.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Promotion;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionHolder>{

    private List<Promotion> mList;
    private OnItemClickListener listener;
    private Context ctx;

    public PromotionAdapter(List<Promotion> mList, OnItemClickListener listener, Context ctx) {
        this.mList = mList;
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    public PromotionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_products, parent, false);
        return new PromotionHolder(v, mList, ctx);
    }

    @Override
    public void onBindViewHolder(PromotionHolder holder, int position) {
        holder.bind(mList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
