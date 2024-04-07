package com.example.cookidea_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cookidea_app.ModelClasses.Meal;

import java.util.List;

public class MealArrayAdapter extends ArrayAdapter<Meal> {

    Context ctx = null;

    public MealArrayAdapter(Context ctx, List<Meal> listMeals) {
        super(ctx, android.R.layout.simple_spinner_item, listMeals);
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        Meal meal = getItem(position);

        if (meal != null) {
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(meal.getMealName());
        }

        return convertView;
    }


}
