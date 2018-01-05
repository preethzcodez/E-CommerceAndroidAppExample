package com.preethzcodez.ecommerceexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.preethzcodez.ecommerceexample.MainActivity;
import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.fragments.Products;
import com.preethzcodez.ecommerceexample.fragments.Subcategories;
import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Preeth on 1/4/2018.
 */

public class SubcategoryGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<Category> subCategoryList;
    DB_Handler db_handler;

    public SubcategoryGridAdapter(Context context, List<Category> subCategoryList) {
        this.context = context;
        this.subCategoryList = subCategoryList;
        db_handler = new DB_Handler(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return subCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.categories_grid_item, null);
        holder.category = (TextView) rowView.findViewById(R.id.name);

        holder.category.setText(subCategoryList.get(position).getName());
        //holder.img.setImageResource(imageId[position]);

        holder.gridItemLayout = (RelativeLayout) rowView.findViewById(R.id.gridItemLayouut);
        holder.gridItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = subCategoryList.get(position).getId();

                // get subcategories by id
                List<Category> childCategories = db_handler.getSubcategoryList(id);

                // initialize bundle and fragment manager
                Bundle bundle = new Bundle();
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                // Check If Subcategories Are There Else Call Products List
                if (childCategories.size() > 0) {

                    // add bundle arguments
                    bundle.putSerializable(Constants.CAT_KEY, (Serializable) childCategories);

                    Subcategories subcategories = new Subcategories();
                    subcategories.setArguments(bundle);

                    ft.replace(R.id.content, subcategories);
                    ft.commit();
                } else {
                    // get products list by category
                    List<Product> productsList = db_handler.getProductsListByCategory(id);

                    // add bundle arguments
                    bundle.putSerializable(Constants.PDT_KEY, (Serializable) productsList);

                    Products products = new Products();
                    products.setArguments(bundle);

                    ft.replace(R.id.content, products);
                    ft.commit();
                }
            }
        });

        return rowView;
    }

    public class Holder {
        TextView category;
        RelativeLayout gridItemLayout;
    }
}
