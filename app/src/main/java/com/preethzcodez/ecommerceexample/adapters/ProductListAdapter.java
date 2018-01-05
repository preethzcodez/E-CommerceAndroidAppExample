package com.preethzcodez.ecommerceexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.activities.ProductDetails;
import com.preethzcodez.ecommerceexample.pojo.Product;

import java.util.List;

/**
 * Created by Preeth on 1/4/18
 */

public class ProductListAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    private List<Product> productList;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.product_grid_item, null);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.img = (ImageView) rowView.findViewById(R.id.image);

        holder.name.setText(productList.get(position).getName());
        //holder.img.setImageResource(imageId[position]);

        // Product Item Click
        holder.itemLay = (RelativeLayout) rowView.findViewById(R.id.itemLay);
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("ProductId",productList.get(position).getId());
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView name, price;
        ImageView img, wishIcon;
    }
}
