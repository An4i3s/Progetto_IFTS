package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ServiceCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cookidea_app.Activities.CookIdeaApp;
import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Activities.SharedPrefManager;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;

public class PasswordFragment extends Fragment {

    // TODO: 04/04/2024 Java deve gestire controlli su password (uguale alla vecchia e ripetuto 2 volte la nuova)

    EditText vecchiaPassET;
    EditText nuovaPassET;
    EditText confNuovaPassET;

    TextView errorePassVecchia;
    TextView errorePassNuova;

    TextView erroreConferma;

    Button btnConferma;

    Context ctx;

   User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_password, container, false);

        vecchiaPassET = rootview.findViewById(R.id.vecchiaPassEt);
        vecchiaPassET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        errorePassVecchia = rootview.findViewById(R.id.erroreVecchiaPass);
        nuovaPassET = rootview.findViewById(R.id.nuovaPass1);
        nuovaPassET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        confNuovaPassET = rootview.findViewById(R.id.nuovaPass2);
        confNuovaPassET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        errorePassNuova = rootview.findViewById(R.id.erroreNuovaPass);
        btnConferma = rootview.findViewById(R.id.btnConfermaPass);

        erroreConferma = rootview.findViewById(R.id.erroreConferma);


        user = ((CookIdeaApp)((MainActivity)ctx).getApplication()).getLoggedUser();


        vecchiaPassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isOldPassTrue();
            }
        });

        confNuovaPassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passCorretta();
            }
        });


        btnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOldPassTrue() && passCorretta()){
                    // TODO: 04/04/2024 Implemnta endpoint update Password
                    erroreConferma.setVisibility(View.GONE);
                }else {
                    erroreConferma.setVisibility(View.VISIBLE);
                }
            }
        });
        return rootview;
    }

    public boolean isOldPassTrue(){
        String userPass = user.getPassword();
        String userInput = vecchiaPassET.getText().toString();
        if (userInput.equals(userPass)){
            errorePassVecchia.setVisibility(View.GONE);
            return true;
        }else {
            errorePassVecchia.setVisibility(View.VISIBLE);
            return false;
        }
    }

    public boolean passCorretta(){
        String pass1 = nuovaPassET.getText().toString();
        String pass2 = confNuovaPassET.getText().toString();
        if (pass1.equals(pass2)){
            errorePassNuova.setVisibility(View.GONE);
            return true;
        }else {
            errorePassNuova.setVisibility(View.VISIBLE);
            return false;
        }
    }


}