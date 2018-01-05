package com.preethzcodez.ecommerceexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.fragments.Categories;
import com.preethzcodez.ecommerceexample.fragments.Products;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    DB_Handler db_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db_handler = new DB_Handler(this);

        // initialize bottom navigation view
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        callProductsFragment();
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
                    return true;

                case R.id.nav_categories: // Categories
                    ft.replace(R.id.content, new Categories());
                    ft.commit();
                    return true;

                case R.id.nav_shortlist: // Wish List
                    return true;

                case R.id.nav_account: // User Account
                    return true;
            }
            return false;
        }
    };

    // call products fragment
    private void callProductsFragment() {
        // get product list
        List<Product> productList = db_handler.getProductsList();

        Bundle args = new Bundle();
        args.putSerializable(Constants.PDT_KEY, (Serializable) productList);

        Products products = new Products();
        products.setArguments(args);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, products);
        ft.commit();
    }
}
