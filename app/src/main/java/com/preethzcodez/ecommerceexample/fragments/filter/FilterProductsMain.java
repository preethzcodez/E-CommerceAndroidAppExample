package com.preethzcodez.ecommerceexample.fragments.filter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.adapters.CategoryListAdapter;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.pojo.Category;

import java.util.List;

/**
 * Created by Preeth on 1/6/2018.
 */

public class FilterProductsMain extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);

        // load products
        DB_Handler db_handler = new DB_Handler(getActivity());
        List<Category> categoryList = db_handler.getCategoryList();

        // fill listview with data
        ListView listView=(ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new CategoryListAdapter(getActivity(), categoryList));

        return view;
    }
}
