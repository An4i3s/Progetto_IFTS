package com.example.cookidea_app.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {


    public DatePickerDialogFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        Activity activity = getActivity();
        FragmentActivity fragmentActivity = getActivity();


        if (activity!=null && isAdded()){
            return new DatePickerDialog(activity, (DatePickerDialog.OnDateSetListener)fragmentActivity, year, month, dayOfMonth);

        }
        return null;
    }



}
