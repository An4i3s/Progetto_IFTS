package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.os.Bundle;

import static com.example.cookidea_app.Activities.CookIdeaApp.apiService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Adapters.ListaSpesaAdapter;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaSpesaFragmentPage extends Fragment {
    Context ctx = null;
    View rootView = null;

    LinearLayout layoutMonday;
    TextView oneTv;
    TextView twoTv;
    ArrayList<Ingredients> mondayIngredients;

    ListView spesaDay1Lv;


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



        User user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        layoutMonday = rootView.findViewById(R.id.layoutDay1);
        oneTv = rootView.findViewById(R.id.one);
        twoTv = rootView.findViewById(R.id.two);

        Calendar calendar = Calendar.getInstance();

        // Declare variables to store dates
        Calendar day1 = calendar;
        String day1String = formatCalendarDate(day1);
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

        mondayIngredients = new ArrayList<>();

        Call<List<Ingredients>> call1 = apiService.getDailyIngredients(user.getId(),day1String);


        call1.enqueue(new Callback<List<Ingredients>>() {
            @Override
            public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                assert response.body() != null;
                mondayIngredients.addAll(response.body());

                Log.i("mondayIngredients", String.valueOf(mondayIngredients.size())+"Success");
                Log.i("mondayIngredients", mondayIngredients.get(0).toString());
                Log.i("mondayIngredients", mondayIngredients.get(1).toString());
                Log.i("mondayIngredients", mondayIngredients.get(2).toString());


            }

            @Override
            public void onFailure(Call<List<Ingredients>> call, Throwable t) {
                Log.i("mondayIngredients", mondayIngredients.size()+"Failure");

            }
        });

        spesaDay1Lv = rootView.findViewById(R.id.oneLv);
        ListaSpesaAdapter adapter = new ListaSpesaAdapter(ctx, R.layout.lista_spesa_item, mondayIngredients);
        spesaDay1Lv.setAdapter(adapter);





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

    public  String formatCalendarDate(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(calendar.getTime());
        return formattedDate;
    }


}