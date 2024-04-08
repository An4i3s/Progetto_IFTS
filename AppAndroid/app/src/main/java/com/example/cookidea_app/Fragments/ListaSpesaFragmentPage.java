package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.R;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ListaSpesaFragmentPage extends Fragment {
    Context ctx = null;
    View rootView = null;

    LinearLayout layoutMonday;
    TextView oneTv;
    TextView twoTv;

    public ListaSpesaFragmentPage() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lista_spesa_page, container, false);

        layoutMonday = rootView.findViewById(R.id.layoutDay1);
        oneTv = rootView.findViewById(R.id.one);
        twoTv = rootView.findViewById(R.id.two);

        Calendar calendar = Calendar.getInstance();

        // Declare variables to store dates
        Calendar day1 = calendar;
        Calendar day2 = (Calendar) calendar.clone();
        day2.add(Calendar.DAY_OF_MONTH, 1);
        Calendar day3 = (Calendar) calendar.clone();
        day3.add(Calendar.DAY_OF_MONTH, 2);
        Calendar day4 = (Calendar) calendar.clone();
        day4.add(Calendar.DAY_OF_MONTH, 3);
        Calendar day5 = (Calendar) calendar.clone();
        day5.add(Calendar.DAY_OF_MONTH, 4);
        Calendar day6 = (Calendar) calendar.clone();
        day6.add(Calendar.DAY_OF_MONTH, 5);
        Calendar day7 = (Calendar) calendar.clone();
        day7.add(Calendar.DAY_OF_MONTH, 6);

        oneTv.setText(convertWeekDay(day1.getTime()));
        twoTv.setText(convertWeekDay(day2.getTime()));


        ArrayList<Ingredients> mondayIngredients = new ArrayList<>();
        // TODO: 08/04/2024 Creare tante Checkbox quando sono gli ingredienti presenti in quel giorno
        //  - trovare index ingredienti
        //  -  DB: MenuSettimale> dove data = Lunedì (da trasformare dal backend)
        //          > da li si recupera il idPiatto
        //          > da id Piatto si recuperano i dati da ricettario
        //          > ingredienti
        // ==> FARE ENDPOINT GET  MENU SETTIMANALE BY DATE (OLTRE CHE X id) E POI RICHIAMARE X OGNI GIORNO
        //  - Creare un array di ingredienti
        //  - in base a Array.lenght generare i checkbox

        //   - in base a quello creare checkbox

        //add checkboxes
        for (int i = 0; i < 6; i++) {
            CheckBox cb = new CheckBox(ctx);
            //Qui set Text deve essere uguale a ingrediente, bisonga Loopare mondayIngredientsArrayList
            cb.setText("Dynamic Checkbox " + i);
            cb.setId(i + 6);
            layoutMonday.addView(cb);
        }

        return rootView;
    }

    public String convertWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
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