package com.preethzcodez.ecommerceexample.adapters;

import android.annotation.SuppressLint;
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
import com.preethzcodez.ecommerceexample.pojo.Cart;
import com.preethzcodez.ecommerceexample.utils.Util;

import java.util.List;

/**
 * Created by Preeth on 1/7/2018
 */

public class ShoppingCartListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Cart> shoppingCart;

    // interface to update payable amount
    public interface UpdatePayableAmount {
        void updatePayableAmount(List<Cart> shoppingCart);
    }

    // inteface to finish activity if cart empty
    public interface MonitorListItems {
        void finishActivity(List<Cart> shoppingCart);
    }

    public ShoppingCartListAdapter(Context context, List<Cart> shoppingCart) {
        this.context = context;
        this.shoppingCart = shoppingCart;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shoppingCart.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.shoppingcart_item, null);
        holder.title = rowView.findViewById(R.id.title);
        holder.size = rowView.findViewById(R.id.size);
        holder.color = rowView.findViewById(R.id.color);
        holder.price = rowView.findViewById(R.id.price);
        holder.tax = rowView.findViewById(R.id.tax);
        holder.qty = rowView.findViewById(R.id.quantityValue);
        holder.remove = rowView.findViewById(R.id.remove);
        holder.minus = rowView.findViewById(R.id.minus);
        holder.plus = rowView.findViewById(R.id.plus);

        holder.title.setText(shoppingCart.get(position).getProduct().getName());
        holder.color.setText("Color: " + shoppingCart.get(position).getVariant().getColor());

        String size = String.valueOf(shoppingCart.get(position).getVariant().getSize());
        try {
            if (size != null && !size.equalsIgnoreCase("null") && !size.equalsIgnoreCase("0.0")) {
                holder.size.setText("Size: " + size);
            } else {
                holder.size.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            holder.size.setVisibility(View.GONE);
        }

        // Calculate Price Value
        final int[] quantity = {shoppingCart.get(position).getItemQuantity()};
        String taxName = shoppingCart.get(position).getProduct().getTax().getName();
        final Double taxValue = shoppingCart.get(position).getProduct().getTax().getValue();
        final Double priceValue = Double.valueOf(shoppingCart.get(position).getVariant().getPrice());

        holder.qty.setText(String.valueOf(quantity[0]));
        holder.price.setText("Rs." + Util.formatDouble(calculatePrice(taxValue, priceValue, quantity[0])));
        holder.tax.setText("(" + taxName + ": Rs." + taxValue + ")");


        // Product Item Click
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("ProductId", shoppingCart.get(position).getProduct().getId());
                context.startActivity(intent);
            }
        });

        // Remove Item
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Delete Item From DB
                DB_Handler db_handler = new DB_Handler(context);
                if (db_handler.deleteCartItem(shoppingCart.get(position).getId())) {
                    shoppingCart.remove(position);
                    notifyDataSetChanged();

                    if (context instanceof UpdatePayableAmount) {
                        ((UpdatePayableAmount) context).updatePayableAmount(shoppingCart);
                    }

                    if (context instanceof MonitorListItems) {
                        ((MonitorListItems) context).finishActivity(shoppingCart);
                    }
                } else {
                    Toast.makeText(context, "error deleting item", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Quantity Decrement Listener
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity[0] != 1) {
                    quantity[0]--;
                    updateQuantity(quantity[0],position); // update in DB
                    shoppingCart.get(position).setItemQuantity(quantity[0]);
                    holder.qty.setText(String.valueOf(quantity[0]));
                    holder.price.setText("Rs." + Util.formatDouble(calculatePrice(taxValue, priceValue, quantity[0])));

                    if (context instanceof UpdatePayableAmount) {
                        ((UpdatePayableAmount) context).updatePayableAmount(shoppingCart);
                    }
                }
            }
        });

        // Quantity Increment Listener
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity[0]++;
                updateQuantity(quantity[0],position); // update in DB
                shoppingCart.get(position).setItemQuantity(quantity[0]);
                holder.qty.setText(String.valueOf(quantity[0]));
                holder.price.setText("Rs." + Util.formatDouble(calculatePrice(taxValue, priceValue, quantity[0])));

                if (context instanceof UpdatePayableAmount) {
                    ((UpdatePayableAmount) context).updatePayableAmount(shoppingCart);
                }
            }
        });

        return rowView;
    }

    private Double calculatePrice(Double taxValue, Double priceValue, int quantity) {
        return (taxValue + priceValue) * quantity;
    }

    // Update Quantity In DB
    private void updateQuantity(int quantity, int position) {
        DB_Handler db_handler = new DB_Handler(context);
        db_handler.updateItemQuantity(quantity, shoppingCart.get(position).getId());
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView title, price, size, color, tax, qty;
        ImageView remove, minus, plus;
    }
}
