package com.preethzcodez.ecommerceexample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.adapters.ProductListAdapter;
import com.preethzcodez.ecommerceexample.adapters.SubcategoryGridAdapter;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.pojo.Product;
import com.preethzcodez.ecommerceexample.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preeth on 1/5/18
 */

public class Subcategories extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_list, container, false);

        // get data
        Bundle args = getArguments();
        List<Category> childCategories = (List<Category>) args.getSerializable(Constants.CAT_KEY);

        // fill gridview with data
        GridView gv=(GridView) view.findViewById(R.id.productsGrid);
        gv.setNumColumns(3);
        gv.setAdapter(new SubcategoryGridAdapter(getActivity(), childCategories));

        return view;

    }
}
