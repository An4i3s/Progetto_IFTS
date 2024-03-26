package com.example.cookidea_app.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.R;

import java.text.DateFormat;
import java.util.Calendar;

public class RegistrazioneFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    Button dateBtn;
    TextView etaTv;

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
                DatePickerDialogFragment fragment = new DatePickerDialogFragment();
                if (activity!=null && isAdded()){
                   /* getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.loginPage, fragment)
                            .commit();

                    */
                    fragment.show(getActivity().getSupportFragmentManager(), "Date Pick");


                }

            }
        });

        return  rootView;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        // TODO: 25/03/2024 Provato con LocalDate ma non funziona a sotto livello 26 (min app è 24)
       // LocalDate localDate = new LocalDate(year,month,dayOfMonth);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        etaTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             etaTv.setText(selectedDate);

            }

            @Override
            public void afterTextChanged(Editable s) {
                etaTv.setText(selectedDate);

            }
        });
    }
}
