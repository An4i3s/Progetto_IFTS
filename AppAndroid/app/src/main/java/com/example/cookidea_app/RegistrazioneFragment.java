package com.example.cookidea_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RegistrazioneFragment extends Fragment {

    Button dateBtn;
    TextView etaTv;

    String dataNascita;

    public RegistrazioneFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  rootView = inflater.inflate(R.layout.fragment_registrazione, container, false);

        dateBtn = rootView.findViewById(R.id.btnEta);
        etaTv = rootView.findViewById(R.id.tvEta);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity activity = getActivity();
                DatePickerDialog fragment = new DatePickerDialog(rootView.getContext());
                if (activity!=null && isAdded()){
                    fragment.show();
                    fragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar mCalendar = Calendar.getInstance();
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, month);
                            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            //  String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                           String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
                            //String selectedDate = DateFormat.getDateInstance().format(mCalendar.getTime());
                            //String x = formatDate(mCalendar.getTime());
                            dataNascita = selectedDate;
                            etaTv.setText(dataNascita);
                            Toast.makeText(view.getContext(),dataNascita, Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        return  rootView;
    }

}
