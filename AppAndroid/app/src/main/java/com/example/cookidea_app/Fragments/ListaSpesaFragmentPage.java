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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Adapters.ListaSpesaAdapter;
import com.example.cookidea_app.ModelClasses.Ingredients;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaSpesaFragmentPage extends Fragment {

    // TODO: 08/04/2024 Gestire il caso in cui utente non loggato -> inflatare altro layout
    //  + quando si passa da una schermat a un altra scompare lista
    Context ctx = null;
    View rootView = null;

    LinearLayout layoutOne;
    LinearLayout layoutTwo;

    TextView oneTv;
    TextView twoTv;
    TextView threeTv;
    ArrayList<Ingredients> oneIngredients;
    ArrayList<Ingredients> twoIngredients;
    ArrayList<Ingredients> threeIngredients;

    ListView spesaDay1Lv;
    ListView spesaDay2Lv;
    ListView spesaDay3Lv;

    Call<List<Ingredients>> call1;
    Call<List<Ingredients>> call2;
    Call<List<Ingredients>> call3;
    //User user;

    public ListaSpesaFragmentPage() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!SharedPrefManager.isLoggedIn(ctx)){
            rootView = inflater.inflate(R.layout.fragment_lista_spesa_no_logout, container, false);
            return rootView;
        }else {
            rootView = inflater.inflate(R.layout.fragment_lista_spesa_page, container, false);

        User user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        //Serve?
        layoutOne = rootView.findViewById(R.id.layoutDay1);
        oneTv = rootView.findViewById(R.id.one);
        twoTv = rootView.findViewById(R.id.two);
        threeTv = rootView.findViewById(R.id.three);


        Calendar calendar = Calendar.getInstance();

        // Declare variables to store dates
        Calendar day1 = calendar;
        String day1String = formatCalendarDate(day1);
        Calendar day2 = (Calendar) calendar.clone();
        day2.add(Calendar.DAY_OF_MONTH, 1);
        String day2String = formatCalendarDate(day2);
        Calendar day3 = (Calendar) calendar.clone();
        day3.add(Calendar.DAY_OF_MONTH, 2);
        String day3String = formatCalendarDate(day3);
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
        threeTv.setText(convertWeekDay(day3.getTime()));

        oneIngredients = new ArrayList<>();
        twoIngredients = new ArrayList<>();
        threeIngredients = new ArrayList<>();

        call1 = apiService.getDailyIngredients(user.getId(),day1String);
       // call2 = apiService.getDailyIngredients(user.getId(), day2String);
         //call3 = apiService.getDailyIngredients(user.getId(), day3String);
       // makeApiCall();

        spesaDay1Lv = rootView.findViewById(R.id.oneLv);
        ListaSpesaAdapter adapter1 = new ListaSpesaAdapter(ctx, oneIngredients);
        spesaDay1Lv.setAdapter(adapter1);
        spesaDay2Lv = rootView.findViewById(R.id.twoLv);
        ListaSpesaAdapter adapter2 = new ListaSpesaAdapter(ctx, twoIngredients);
        spesaDay2Lv.setAdapter(adapter2);
        spesaDay3Lv = rootView.findViewById(R.id.threeLv);
        ListaSpesaAdapter adapter3 = new ListaSpesaAdapter(ctx, threeIngredients);
        spesaDay3Lv.setAdapter(adapter3);

        if(oneIngredients.isEmpty()) {
            call1.enqueue(new Callback<List<Ingredients>>() {
                @Override
                public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                    assert response.body() != null;
                    oneIngredients.addAll(response.body());
                    adapter1.notifyDataSetChanged();
                    Log.i("mondayIngredients", String.valueOf(oneIngredients.size()) + "Success");
                    Log.i("mondayIngredients", oneIngredients.get(0).toString());
                    Log.i("mondayIngredients", oneIngredients.get(1).toString());
                    Log.i("mondayIngredients", oneIngredients.get(2).toString());


                }

                @Override
                public void onFailure(Call<List<Ingredients>> call, Throwable t) {
                    Log.i("mondayIngredients", oneIngredients.size() + "Failure");

                }
            });

        }

/*
        call2.enqueue(new Callback<List<Ingredients>>() {
            @Override
            public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                assert response.body() != null;
                twoIngredients.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Ingredients>> call, Throwable t) {

            }
        });

 */
/*
        call3.enqueue(new Callback<List<Ingredients>>() {
            @Override
            public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                threeIngredients.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Ingredients>> call, Throwable t) {

            }
        });

*/





        return rootView;
        }


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

    public void makeApiCall(){
        //Call<List<Ingredients>> call1 = apiService.getDailyIngredients(user.getId(),day1String);
       // Call<List<Ingredients>> call2 = apiService.getDailyIngredients(user.getId(), day2String);


        call1.enqueue(new Callback<List<Ingredients>>() {
            @Override
            public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                assert response.body() != null;
                oneIngredients.addAll(response.body());

                Log.i("mondayIngredients", String.valueOf(oneIngredients.size())+"Success");
                Log.i("mondayIngredients", oneIngredients.get(0).toString());
                Log.i("mondayIngredients", oneIngredients.get(1).toString());
                Log.i("mondayIngredients", oneIngredients.get(2).toString());
                if (response.isSuccessful()){
                    call2.enqueue(new Callback<List<Ingredients>>() {
                        @Override
                        public void onResponse(Call<List<Ingredients>> call, Response<List<Ingredients>> response) {
                            assert response.body() != null;
                            twoIngredients.addAll(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<Ingredients>> call, Throwable t) {

                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<List<Ingredients>> call, Throwable t) {
                Log.i("mondayIngredients", oneIngredients.size()+"Failure");

            }
        });


    }

}