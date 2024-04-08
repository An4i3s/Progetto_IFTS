package com.example.cookidea_app.Fragments;

import static com.example.cookidea_app.Activities.CookIdeaApp.apiService;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrazioneFragment extends Fragment {

    Button dateBtn;
    Button signupBtn;
    TextView etaTv;

    String dataNascita;

    Context ctx;
    User user;
    private String name;
    private String surname;
    private String username;
    private Date birthdate;
    private String email;
    private String password;

    EditText nameEt;
    EditText surnameEt;
    EditText usernameEt;
    EditText emailEt;
    EditText passwordEt;


    public RegistrazioneFragment() {

    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registrazione, container, false);

        nameEt = rootView.findViewById(R.id.nameEditText);
        surnameEt = rootView.findViewById(R.id.surnameEditText);
        usernameEt = rootView.findViewById(R.id.regUsernameEditText);
        emailEt = rootView.findViewById(R.id.email);
        passwordEt = rootView.findViewById(R.id.passwordEditText);
        dateBtn = rootView.findViewById(R.id.btnEta);
        etaTv = rootView.findViewById(R.id.tvEta);
        signupBtn = rootView.findViewById(R.id.signupButton);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity activity = getActivity();
                DatePickerDialog fragment = new DatePickerDialog(rootView.getContext());
                fragment.getDatePicker().setMaxDate(new Date().getTime());
                if (activity != null && isAdded()) {
                    fragment.show();
                    fragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar mCalendar = Calendar.getInstance();
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, month);
                            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalendar.getTime());

                            dataNascita = selectedDate;
                            etaTv.setText(dataNascita);
                            Toast.makeText(view.getContext(), dataNascita, Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEt.getText().toString();
                surname = surnameEt.getText().toString();
                username = usernameEt.getText().toString();
                birthdate = new Date(dataNascita);
                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();

                assert (name != null && surname != null && username != null && birthdate != null && email != null);

                User userRequest = new User(name, surname, username, email, birthdate, password);
                Call<User> call = apiService.signup(userRequest);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Registrazione completata", Toast.LENGTH_SHORT).show();
                            user = response.body();
                            ((MainActivity) ctx).onLoginSuccess(user);
                            SharedPrefManager.setLoggedIn(ctx, user, true);
                            ((MainActivity)ctx).updateNavigationDrawer();
                            ((MainActivity)ctx).changeFrameByNavigationTab(R.id.homePage);
                        } else {
                            Toast.makeText(getContext(), "ERRORE DURANTE LA REGISTRAZIONE", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("Errore registrazione", "Errore API signup");

                    }
                });

            }
        });


        return rootView;
    }

}
