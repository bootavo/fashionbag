package com.tarjetic.store.modules.menu.catalogue.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.tarjetic.store.R;
import com.tarjetic.store.listeners.OnItemClickListener;
import com.tarjetic.store.models.Promotion;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionHolder>{

    private List<Promotion> mList;
    private OnItemClickListener listener;
    private Context ctx;
    private int type;

    public PromotionAdapter(List<Promotion> mList, OnItemClickListener listener, Context ctx, int type) {
        this.mList = mList;
        this.listener = listener;
        this.ctx = ctx;
        this.type = type;
    }

    @Override
    public PromotionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(type == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_products_home, parent, false);
        }else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_products, parent, false);
        }
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
