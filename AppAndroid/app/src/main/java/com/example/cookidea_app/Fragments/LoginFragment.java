package com.example.cookidea_app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookidea_app.Activities.MainActivity;
import com.example.cookidea_app.Backend.CookIdeaApiEndpointInterface;
import com.example.cookidea_app.Backend.LoginRequest;
import com.example.cookidea_app.ModelClasses.User;
import com.example.cookidea_app.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    EditText username;
    EditText password;
    Button loginBtn;

   Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();


    // TODO: 26/03/2024 Creare Endpoint Login Utente:
    //  -> istanza di Retrofit (con (GsonConverterFactory.create(gson))
    //  -> richiamare anche BASE URL?
    //  ->
    public LoginFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        username = rootView.findViewById(R.id.usernameEditText);
        password = rootView.findViewById(R.id.passwordEditText);
        loginBtn = rootView.findViewById(R.id.signinButton);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookIdeaApiEndpointInterface apiInterface = retrofit.create(CookIdeaApiEndpointInterface.class);
                //Call<User> call = apiInterface.login(username.getText().toString(), password.getText().toString());
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(username.getText().toString());
                Log.i("userLogin",username.getText().toString());
                loginRequest.setPassword(password.getText().toString());
                Log.i("userLogin",password.getText().toString());
                Call<User> call = apiInterface.login(loginRequest);


                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Utente Trovato", Toast.LENGTH_LONG).show();
                            User user = response.body();
                        }else {
                            Toast.makeText(getContext(), "Utente non Trovato", Toast.LENGTH_LONG).show();

                        }
                    }



                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("Errore login", "Errore API login");
                    }
                });
            }
        });





        Button button = rootView.findViewById(R.id.signupButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                assert activity != null;
                activity.apriRegistrazione();

            }
        });

        return  rootView;

    }



}
