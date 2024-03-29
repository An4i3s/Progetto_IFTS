package com.example.cookidea_app.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfiloFragment extends Fragment {

    User user;

    TextView usernameTV;
    TextView nomeTv;
    TextView cognomeTv;
    TextView dataNascitaTv;


    EditText editNome;
    EditText editCognome;
    ImageButton btnNome;
    ImageButton btnConfNome;

    ImageButton btnCognome;
    ImageButton btnConfCognome;

    ImageButton btnDataNascita;
    SharedPreferences sharedPreferences;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    CookIdeaApiEndpointInterface endpointInterface = retrofit.create(CookIdeaApiEndpointInterface.class);

    Context ctx;


    public ProfiloFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profilo, container, false);


         user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();
        usernameTV = rootView.findViewById(R.id.usernameTV);
        usernameTV.setText(user.getUsername());

        nomeTv = rootView.findViewById(R.id.nomeUtente);
        nomeTv.setText(user.getName());
        cognomeTv = rootView.findViewById(R.id.cognomeUtente);
        cognomeTv.setText(user.getSurname());
        dataNascitaTv = rootView.findViewById(R.id.dataNascita);
        dataNascitaTv.setText(user.getDate2());

        editNome = rootView.findViewById(R.id.editNome);
        editCognome = rootView.findViewById(R.id.editCognome);

        btnNome = rootView.findViewById(R.id.btnEditNome);
        btnConfNome = rootView.findViewById(R.id.btnConfNome);
        btnCognome = rootView.findViewById(R.id.btnEditCognome);
        btnConfCognome = rootView.findViewById(R.id.btnConfCognome);
        btnDataNascita = rootView.findViewById(R.id.btnDataNascita);



        btnNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeTv.setVisibility(View.GONE);
                editNome.setVisibility(View.VISIBLE);
                btnNome.setVisibility(View.GONE);
                btnConfNome.setVisibility(View.VISIBLE);
                editNome.setText(nomeTv.getText().toString());
            }
        });

        btnCognome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cognomeTv.setVisibility(View.GONE);
                editCognome.setVisibility(View.VISIBLE);
                btnCognome.setVisibility(View.GONE);
                btnConfCognome.setVisibility(View.VISIBLE);
                editCognome.setText(cognomeTv.getText().toString());
            }
        });

        btnDataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog fragmentDate = new DatePickerDialog(rootView.getContext());
                fragmentDate.show();
                fragmentDate.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar mCalendar = Calendar.getInstance();
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalendar.getTime());
                        dataNascitaTv.setText(selectedDate);
                    }
                });
            }
        });


        // TODO: 29/03/2024 Implemnetare collegamento a API per update dati utenti in DB 
        btnConfNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeTv.setText(editNome.getText());
                nomeTv.setVisibility(View.VISIBLE);
                btnNome.setVisibility(View.VISIBLE);
                editNome.setVisibility(View.GONE);
                btnConfNome.setVisibility(View.GONE);
            }
        });

        btnConfCognome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cognomeTv.setText(editCognome.getText());
                cognomeTv.setVisibility(View.VISIBLE);
                btnCognome.setVisibility(View.VISIBLE);
                editCognome.setVisibility(View.GONE);
                btnConfCognome.setVisibility(View.GONE);
            }
        });

        return rootView;
    }
}
