package com.preethzcodez.ecommerceexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.pojo.Product;

import java.util.List;

/**
 * Created by Preeth on 1/4/18
 */

public class ProductListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Product> productList;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
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

        holder.name.setText(productList.get(position).getName());
        //holder.img.setImageResource(imageId[position]);

        return rowView;
    }

    public class Holder {
        TextView name, price;
        ImageView img, wishIcon;
    }
}
