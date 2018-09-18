package info.fashion.bag.modules.menu.catalogue.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.fashion.bag.R;
import info.fashion.bag.listeners.OnItemClickListener;
import info.fashion.bag.models.Product;

import java.util.List;

/**
 * Created by gtufinof on 3/12/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private List<Product> mListProducts;
    private OnItemClickListener listener;
    private Context ctx;
    private int type;

    public ProductAdapter(List<Product> mListProducts, OnItemClickListener listener, Context ctx, int type) {
        this.mListProducts = mListProducts;
        this.listener = listener;
        this.ctx = ctx;
        this.type = type;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(type == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_products_home, parent, false);
        }else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_products, parent, false);
        }
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
