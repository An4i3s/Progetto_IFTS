package com.example.cookidea_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuDateSpinnerAdapter extends ArrayAdapter<Date> {
    Context ctx = null;

    public MenuDateSpinnerAdapter(Context ctx, List<Date> dateList) {
        super(ctx, android.R.layout.simple_spinner_item, dateList);
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Date date = getItem(position);
        String weekDay = convertWeekDay(date);

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(weekDay);


        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Date date = getItem(position);
        String weekDay = convertWeekDay(date);

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(weekDay);


        return convertView;
    }



    String convertWeekDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek){
            case 1:
                return "Domenica";
            case 2:
                return "Lunedì";
            case 3:
                return "Martedì";
            case 4:
                return "Mercoledì";
            case 5:
                return "Giovedì";
            case 6:
                return "Venerdì";
            case 7:
                return "Sabato";
            default:
                return "Error";
        }
    }
}
