package com.preethzcodez.ecommerceexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.preethzcodez.ecommerceexample.fragments.Categories;
import com.preethzcodez.ecommerceexample.fragments.Products;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize bottom navigation view
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(0);
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
                    ft.replace(R.id.content, new Products());
                    ft.commit();
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

}
