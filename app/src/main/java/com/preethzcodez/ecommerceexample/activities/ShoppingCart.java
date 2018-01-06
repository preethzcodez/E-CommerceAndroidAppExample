package com.preethzcodez.ecommerceexample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.adapters.ShoppingCartListAdapter;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.pojo.Cart;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Preeth on 1/6/2018.
 */

public class ShoppingCart extends AppCompatActivity implements ShoppingCartListAdapter.UpdatePayableAmount, ShoppingCartListAdapter.MonitorListItems {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        // Get Cart Items
        SessionManager sessionManager = new SessionManager(this);
        DB_Handler db_handler = new DB_Handler(this);
        List<Cart> shoppingCart = db_handler.getCartItems(sessionManager.getSessionData(Constants.SESSION_EMAIL));

        // Fill ListView With Items
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ShoppingCartListAdapter(this, shoppingCart));

        setPayableAmount(shoppingCart);
    }

    // Calculate Payable Amount
    private void setPayableAmount(List<Cart> shoppingCart) {
        Double totalAmount = 0.0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            int itemQuantity = shoppingCart.get(i).getItemQuantity();
            Double tax = shoppingCart.get(i).getProduct().getTax().getValue();
            Double price = Double.valueOf(shoppingCart.get(i).getVariant().getPrice());
            price = (price + tax) * itemQuantity;
            totalAmount = totalAmount + price;
        }

        // Set Value
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        TextView payable = (TextView) findViewById(R.id.payableAmt);
        payable.setText("Rs."+formatter.format(totalAmount));
    }

    // update payable amount
    @Override
    public void updatePayableAmount(List<Cart> shoppingCart) {
        setPayableAmount(shoppingCart);
    }

    // finish activity if cart empty
    @Override
    public void finishActivity(List<Cart> shoppingCart) {
        try {
            if (shoppingCart.size() == 0) {
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }
}
