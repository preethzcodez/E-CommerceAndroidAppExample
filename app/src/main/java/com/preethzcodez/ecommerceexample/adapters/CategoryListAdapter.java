package com.preethzcodez.ecommerceexample.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.pojo.Category;
import com.preethzcodez.ecommerceexample.utils.ExpandableHeightGridView;

import java.util.List;

/**
 * Created by Preeth on 1/4/2018
 */

public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> categoryList;
    private DB_Handler db_handler;

    public CategoryListAdapter(Context context, List<Category> categoryList)
    {
        this.context = context;
        this.categoryList = categoryList;
        db_handler = new DB_Handler(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.categories_list_item, null);
        holder.category = rowView.findViewById(R.id.category);
        holder.category.setText(categoryList.get(position).getName());

        List<Category> subCategoryList = db_handler.getSubcategoryList(categoryList.get(position).getId());

        // fill gridview with data
        holder.expandableHeightGridView= rowView.findViewById(R.id.subcategories);
        holder.expandableHeightGridView.setAdapter(new SubcategoryGridAdapter(context, subCategoryList));
        holder.expandableHeightGridView.setExpanded(true);

        return rowView;
    }

    public class Holder {
        TextView category;
        ExpandableHeightGridView expandableHeightGridView;
    }
}
