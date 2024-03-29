package com.example.cookidea_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfiloFragment extends Fragment {

    User user;

    TextView usernameTV;
    TextView nomeTv;
    TextView cognomeTv;
    TextView dataNascitaTv;

    EditText editNome;
    ImageButton btnNome;
    ImageButton btnConfNome;
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

        btnNome = rootView.findViewById(R.id.btnEditNome);
        btnConfNome = rootView.findViewById(R.id.btnConfNome);


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

        return rootView;
    }
}
