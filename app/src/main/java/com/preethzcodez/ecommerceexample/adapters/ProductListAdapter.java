package com.preethzcodez.ecommerceexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;

/**
 * Created by Preeth on 1/4/18
 */

public class ProductListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    String[] prgmNameList;

    public ProductListAdapter(Context context, String[] prgmNameList) {
        this.prgmNameList = prgmNameList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return prgmNameList.length;
    }

    @Override
    public Object getItem(int i) {
        return prgmNameList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.product_grid_item, null);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.img = (ImageView) rowView.findViewById(R.id.image);

        holder.name.setText(prgmNameList[position]);
        //holder.img.setImageResource(imageId[position]);

        return rowView;
    }

    public class Holder {
        TextView name, price;
        ImageView img, wishIcon;
    }
}
