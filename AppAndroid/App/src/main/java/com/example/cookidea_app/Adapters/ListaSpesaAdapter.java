package com.example.cookidea_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaSpesaAdapter extends ArrayAdapter<Ingredients> {

    Context context;
    HashMap<Ingredients, Boolean> checkedArray = new HashMap<>();


    public ListaSpesaAdapter(@NonNull Context context, List<Ingredients> ingredientsList) {
        super(context, R.layout.lista_spesa_item, ingredientsList);
        this.context=context;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lista_spesa_item, parent, false);

        Ingredients ingredient = getItem(position);
        TextView nomeIngredient = convertView.findViewById(R.id.ingredienteItem);
        CheckBox cb = convertView.findViewById(R.id.cbListaItem);
        cb.setChecked(Boolean.TRUE.equals(checkedArray.get(ingredient)));
        assert ingredient != null;
        nomeIngredient.setText(ingredient.toString());

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    nomeIngredient.setPaintFlags(nomeIngredient.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    checkedArray.put(ingredient, true);

                }else {
                    nomeIngredient.setPaintFlags(nomeIngredient.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    checkedArray.put(ingredient, false);
                }
            }
        });
        return convertView;
    }
}
