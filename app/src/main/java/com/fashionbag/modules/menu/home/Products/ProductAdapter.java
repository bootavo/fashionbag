package com.fashionbag.modules.menu.home.Products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fashionbag.R;
import com.fashionbag.interfaces.OnItemClickListener;
import com.fashionbag.models.Products;

import java.util.List;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private List<Products> mListProducts;
    private OnItemClickListener listener;
    private Context ctx;

    public ProductAdapter(List<Products> mListProducts, OnItemClickListener listener, Context ctx) {
        this.mListProducts = mListProducts;
        this.listener = listener;
        this.ctx = ctx;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_products, parent, false);
        return new ProductHolder(v, mListProducts, ctx);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        holder.bind(mListProducts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mListProducts.size();
    }
}
