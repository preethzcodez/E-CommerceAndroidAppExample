package com.preethzcodez.ecommerceexample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.adapters.MyOrdersAdapter;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.pojo.Cart;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.util.List;

/**
 * Created by Preeth on 1/7/2018
 */

public class MyOrders extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorders);

        // Set Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set Title
        TextView titleToolbar = findViewById(R.id.titleToolbar);
        titleToolbar.setText(R.string.my_orders);

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Hide Cart Icon
        ImageView cart = findViewById(R.id.cart);
        cart.setVisibility(View.GONE);

        // Get Orders From DB
        DB_Handler db_handler = new DB_Handler(this);
        SessionManager sessionManager = new SessionManager(this);
        List<Cart> orderHistory = db_handler.getOrders(sessionManager.getSessionData(Constants.SESSION_EMAIL));

        // Fill ListView
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new MyOrdersAdapter(this,orderHistory));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
