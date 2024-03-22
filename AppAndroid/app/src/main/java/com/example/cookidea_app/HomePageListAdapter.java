package com.example.cookidea_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class HomePageListAdapter extends ArrayAdapter<String> {

    Context context;
    static private final String[] categoryName = {"Antipasti", "Primi", "Secondi", "Dessert"};



    public HomePageListAdapter(Context context) {
        super(context, R.layout.home_page_cateogry_list_layout, categoryName);
        this.context = context;
    }

    private static class CategoryViewHolder{
        TextView textViewVH;
        ImageView imageViewVH;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder categoryViewHolder;
        final View result;

        if (convertView == null) {

            categoryViewHolder = new CategoryViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.home_page_cateogry_list_layout, parent, false);
            categoryViewHolder.textViewVH = (TextView) convertView.findViewById(R.id.categoryName);
            categoryViewHolder.imageViewVH = (ImageView) convertView.findViewById(R.id.categoryImage);

            result=convertView;

            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
            result=convertView;
        }

        categoryViewHolder.textViewVH.setText(categoryName[position]);
        categoryViewHolder.imageViewVH.setImageResource(categoryImage[position]);

        return result;
    }
}
