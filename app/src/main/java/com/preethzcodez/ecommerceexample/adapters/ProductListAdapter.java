package com.preethzcodez.ecommerceexample.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.activities.ProductDetails;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.util.List;

/**
 * Created by Preeth on 1/4/18
 */

public class ProductListAdapter extends BaseAdapter {

    private Context context;
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.product_grid_item, null);
        holder.name = rowView.findViewById(R.id.name);
        holder.price = rowView.findViewById(R.id.price);
        holder.heart = rowView.findViewById(R.id.heart);

        holder.name.setText(productList.get(position).getName());
        holder.price.setText(productList.get(position).getPrice_range());

        // Product Item Click
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("ProductId", productList.get(position).getId());
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(0,0);
            }
        });

        if (productList.get(position).getShortlisted()) {
            holder.heart.setImageResource(R.drawable.ic_heart_grey);
        }

        // Wish List Item Click
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add / Remove Item To Wish List
                DB_Handler db_handler = new DB_Handler(context);
                SessionManager sessionManager = new SessionManager(context);
                if (!productList.get(position).getShortlisted()) {
                    holder.heart.setImageResource(R.drawable.ic_heart_grey);
                    if (db_handler.shortlistItem(productList.get(position).getId(), sessionManager.getSessionData(Constants.SESSION_EMAIL)) > 0) {
                        productList.get(position).setShortlisted(true);
                        Toast.makeText(context, "Item Added To Wish List", Toast.LENGTH_LONG).show();
                    }
                } else {
                    holder.heart.setImageResource(R.drawable.ic_heart_grey600_24dp);
                    if (db_handler.removeShortlistedItem(productList.get(position).getId(), sessionManager.getSessionData(Constants.SESSION_EMAIL))) {
                        productList.get(position).setShortlisted(false);
                        Toast.makeText(context, "Item Removed From Wish List", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return rowView;
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView name, price;
        ImageView heart;
    }
}
