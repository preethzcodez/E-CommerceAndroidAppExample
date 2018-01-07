package com.preethzcodez.ecommerceexample.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.preethzcodez.ecommerceexample.MainActivity;
import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.adapters.ShoppingCartListAdapter;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.pojo.Cart;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Preeth on 1/6/2018
 */

public class ShoppingCart extends AppCompatActivity implements ShoppingCartListAdapter.UpdatePayableAmount, ShoppingCartListAdapter.MonitorListItems {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        // Set Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set Title
        TextView titleToolbar = findViewById(R.id.titleToolbar);
        titleToolbar.setText(R.string.shopping_cart);

        // Hide Cart Icon
        ImageView cart = findViewById(R.id.cart);
        cart.setVisibility(View.GONE);

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Get Cart Items
        final SessionManager sessionManager = new SessionManager(this);
        final DB_Handler db_handler = new DB_Handler(this);
        final List<Cart> shoppingCart = db_handler.getCartItems(sessionManager.getSessionData(Constants.SESSION_EMAIL));

        // Fill ListView With Items
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new ShoppingCartListAdapter(this, shoppingCart));

        setPayableAmount(shoppingCart);

        // Order Button Click
        Button placeOrder = findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete from cart and place order
                db_handler.deleteCartItems();
                db_handler.insertOrderHistory(shoppingCart,sessionManager.getSessionData(Constants.SESSION_EMAIL));
                Toast.makeText(getApplicationContext(),"Order Placed Successfully",Toast.LENGTH_LONG).show();

                // Call Main Activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

    // Calculate Payable Amount
    @SuppressLint("SetTextI18n")
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
        TextView payable = findViewById(R.id.payableAmt);
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
                overridePendingTransition(0,0);
                finish();
            }
        } catch (Exception e) {
            overridePendingTransition(0,0);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
