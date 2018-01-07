package com.preethzcodez.ecommerceexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.preethzcodez.ecommerceexample.activities.ShoppingCart;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.fragments.Account;
import com.preethzcodez.ecommerceexample.fragments.Categories;
import com.preethzcodez.ecommerceexample.fragments.Products;
import com.preethzcodez.ecommerceexample.fragments.WishList;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    DB_Handler db_handler;
    SessionManager sessionManager;
    Toolbar toolbar;
    TextView titleToolbar;
    int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db_handler = new DB_Handler(this);
        sessionManager = new SessionManager(this);

        // Set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set Title
        titleToolbar = (TextView) findViewById(R.id.titleToolbar);
        titleToolbar.setText("Home");

        // initialize bottom navigation view
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        callProductsFragment();
        setToolbarIconsClickListeners();
    }

    // Set Toolbar Icons Click Listeners
    private void setToolbarIconsClickListeners() {
        ImageView cart = (ImageView) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartCount > 0) {
                    startActivity(new Intent(getApplicationContext(), ShoppingCart.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Cart Is Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * BottomNavigationView Listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_home: // Home
                    callProductsFragment();
                    titleToolbar.setText("Home");
                    return true;

                case R.id.nav_categories: // Categories
                    ft.replace(R.id.content, new Categories());
                    ft.commit();
                    titleToolbar.setText("Categories");
                    return true;

                case R.id.nav_shortlist: // Wish List
                    ft.replace(R.id.content, new WishList());
                    ft.commit();
                    titleToolbar.setText("Wish List");
                    return true;

                case R.id.nav_account: // User Account
                    ft.replace(R.id.content, new Account());
                    ft.commit();
                    titleToolbar.setText("Account");
                    return true;
            }
            return false;
        }
    };

    // call products fragment
    private void callProductsFragment() {

        Bundle args = new Bundle();
        args.putInt(Constants.CAT_ID_KEY, 0);

        Products products = new Products();
        products.setArguments(args);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, products);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update Cart Count
        cartCount = db_handler.getCartItemCount(sessionManager.getSessionData(Constants.SESSION_EMAIL));
        TextView count = (TextView) findViewById(R.id.count);
        if (cartCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(cartCount));
        } else {
            count.setVisibility(View.GONE);
        }
    }
}
