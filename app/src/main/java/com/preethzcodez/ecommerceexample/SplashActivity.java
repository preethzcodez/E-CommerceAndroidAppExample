package com.preethzcodez.ecommerceexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.pojo.ProductRank;
import com.preethzcodez.ecommerceexample.pojo.Ranking;
import com.preethzcodez.ecommerceexample.pojo.ResponseJSON;
import com.preethzcodez.ecommerceexample.pojo.Variant;
import com.preethzcodez.ecommerceexample.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerceexample.webservice.RetrofitInterface;

import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Preeth on 1/3/2018.
 */

public class SplashActivity extends AppCompatActivity {

    DB_Handler db_handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize DB Handler
        db_handler = new DB_Handler(this);

        // Fetch Data From URL Only If Local DB Is Empty
        if (db_handler.getItemCount() == 0) {
            fetchData();
        } else {
            loadNextActivity();
        }
    }

    // Fetch Data From URL
    private void fetchData() {
        // Initialize Retrofit
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder(this);
        OkHttpClient httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.fetchData();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {
                        processData(response.body());
                    } else {
                        // Show Error In Snack Bar
                    }
                } catch (Exception e) {
                    // Show Error In Snack Bar

                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // Process JSON and Save In Local DB
    private void processData(ResponseJSON responseJSON) {
        try {

            // Get Categories
            List<Category> categoryList = responseJSON.getCategories();
            for (int i = 0; i < categoryList.size(); i++) {

                int CategoryID = responseJSON.getCategories().get(i).getId();
                String CategoryName = responseJSON.getCategories().get(i).getName();

                // insert category into local DB
                db_handler.insertCategories(CategoryID, CategoryName);

                // Get Products
                List<Product> productList = responseJSON.getCategories().get(i).getProducts();
                for (int j = 0; j < productList.size(); j++) {
                    int ProductID = productList.get(j).getId();
                    String ProductName = productList.get(j).getName();
                    String Date = productList.get(j).getDateAdded();
                    String TaxName = productList.get(j).getTax().getName();
                    Double TaxValue = productList.get(j).getTax().getValue();

                    // insert products into local DB
                    db_handler.insertProducts(ProductID, ProductName, Date, TaxName, TaxValue);

                    // Get Variants
                    List<Variant> variantList = productList.get(j).getVariants();
                    for (int p = 0; p < variantList.size(); p++) {
                        int VariantID = variantList.get(p).getId();
                        String Size = null;
                        String Color = variantList.get(p).getColor();
                        String Price = String.valueOf(variantList.get(p).getPrice());

                        try {
                            // Size May Produce NullPointerException
                            Size = variantList.get(p).getSize().toString();
                        } catch (NullPointerException ignore) {
                        }

                        // insert variants into local DB
                        db_handler.insertVariants(VariantID, Size, Color, Price, ProductID);
                    }
                }

                // Get Child Categories
                List<Integer> childCategories = categoryList.get(i).getChildCategories();
                for (int k = 0; k < childCategories.size(); k++) {
                    int SubcategoryID = childCategories.get(k);

                    // insert childs into subcategory mapping
                    db_handler.insertChildCategoryMapping(CategoryID, SubcategoryID);
                }
            }

            // Get Rankings
            List<Ranking> rankingList = responseJSON.getRankings();
            for (int i = 0; i < rankingList.size(); i++) {
                // Get Products Rank List
                List<ProductRank> productRankList = rankingList.get(i).getProducts();
                for (int j = 0; j < productRankList.size(); j++) {
                    switch (j) {
                        case 0: // Most Viewed Products
                            int viewCount = productRankList.get(j).getViewCount();

                            // update product table
                            db_handler.updateCounts(DB_Handler.VIEW_COUNT, viewCount);
                            break;

                        case 1: // Most Ordered Products
                            int orderCount = productRankList.get(j).getOrderCount();

                            // update product table
                            db_handler.updateCounts(DB_Handler.ORDER_COUNT, orderCount);
                            break;

                        case 2: // Most Shared Products
                            int shareCount = productRankList.get(j).getShares();

                            // update product table
                            db_handler.updateCounts(DB_Handler.SHARE_COUNT, shareCount);
                            break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load Next Activity
    private void loadNextActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
