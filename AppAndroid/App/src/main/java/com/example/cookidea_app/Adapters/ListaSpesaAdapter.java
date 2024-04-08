package com.example.cookidea_app.Adapters;

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

import java.util.List;

public class ListaSpesaAdapter extends ArrayAdapter<Ingredients> {

    Context context;
    int res = 0;


    public ListaSpesaAdapter(@NonNull Context context, int resource, List<Ingredients> ingredientsList) {
        super(context, R.layout.lista_spesa_item, ingredientsList);
        this.context=context;
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View viewContext =  convertView;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewContext = inflater.inflate(res, parent, false);
        }

        Ingredients ingredient = getItem(position);
        TextView nomeIngredient = viewContext.findViewById(R.id.ingredienteItem);
        CheckBox cb = viewContext.findViewById(R.id.cbListaItem);
        assert ingredient != null;
        nomeIngredient.setText(ingredient.toString());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    nomeIngredient.setPaintFlags(nomeIngredient.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }else {
                    nomeIngredient.setPaintFlags(nomeIngredient.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

                }
            }
        });
        return viewContext;
    }
}
