package info.fashion.bag.modules.menu.reserve.register_order;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import info.fashion.bag.R;
import info.fashion.bag.models.Districts;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<Districts> listBanco;

    public SpinnerAdapter(Context context, List<Districts> listBanco) {
        this.context = context;
        this.listBanco = listBanco;
    }

    @Override
    public int getCount() {
        return listBanco.size();
    }

    @Override
    public Object getItem(int position) {
        return listBanco.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listBanco.get(position).getDistrict_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_HORIZONTAL);
        txt.setText(listBanco.get(position).getDistrict());
        txt.setTextColor(ContextCompat.getColor(context, R.color.black));
        txt.setBackgroundResource(R.drawable.spinner_item_border);
        txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return  txt;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_HORIZONTAL);
        txt.setText(listBanco.get(position).getDistrict());
        txt.setTextColor(ContextCompat.getColor(context, R.color.black));
        txt.setBackgroundResource(R.drawable.spinner_item_border);
        txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return  txt;
    }
}
