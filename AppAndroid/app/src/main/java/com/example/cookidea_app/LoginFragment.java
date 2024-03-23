package com.example.cookidea_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginFragment extends Fragment {

    //Button btn;
    public LoginFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        Button button = rootView.findViewById(R.id.signupButton);
//        btn = getActivity().findViewById(R.id.signupButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Activity parentActivity = getActivity();
                //ERRORE:
                // java.lang.NullPointerException: Attempt to invoke virtual method
                // 'void android.widget.Button.setOnClickListener(android.view.View$OnClickListener)'
                // on a null object reference
                MainActivity activity = (MainActivity) getActivity();
                assert activity != null;
                activity.apriRegistrazione();

            }
        });

        return  rootView;

    }



}
