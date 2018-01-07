package com.preethzcodez.ecommerceexample.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.MainActivity;
import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.fragments.Products;
import com.preethzcodez.ecommerceexample.fragments.Subcategories;
import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Preeth on 1/4/2018
 */

public class SubcategoryGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> subCategoryList;
    private DB_Handler db_handler;

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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.categories_grid_item, null);
        holder.category = rowView.findViewById(R.id.name);
        holder.category.setText(subCategoryList.get(position).getName());

        holder.gridItemLayout = rowView.findViewById(R.id.gridItemLayouut);
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
                    bundle.putString(Constants.TITLE,subCategoryList.get(position).getName());
                    bundle.putSerializable(Constants.CAT_KEY, (Serializable) childCategories);

                    Subcategories subcategories = new Subcategories();
                    subcategories.setArguments(bundle);

                    ft.replace(R.id.content, subcategories, Constants.FRAG_SUBCAT);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    // add bundle arguments
                    bundle.putInt(Constants.CAT_ID_KEY, id);
                    bundle.putString(Constants.TITLE,subCategoryList.get(position).getName());

                    Products products = new Products();
                    products.setArguments(bundle);

                    ft.replace(R.id.content, products, Constants.FRAG_PDT);
                    ft.addToBackStack(null);
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
