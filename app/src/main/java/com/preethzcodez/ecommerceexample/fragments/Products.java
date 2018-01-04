package com.preethzcodez.ecommerceexample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.adapters.ProductListAdapter;

/**
 * Created by Preeth on 1/3/2018.
 */

public class Products extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_list, container, false);

        // load products
        String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
        GridView gv=(GridView) view.findViewById(R.id.productsGrid);
        gv.setAdapter(new ProductListAdapter(getActivity(), prgmNameList));

        return view;

    }
}
