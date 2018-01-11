package com.tailorscom.fred.tailorscom;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;
import com.tailorscom.fred.tailorscom.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RegisterFragment extends Fragment implements View.OnClickListener{

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_confirm_password,et_name;
    private TextView tv_login;
    private Spinner et_location;
    private TextView et_contact;
    private ProgressBar progress;
    private Spinner Speciality;
    private Spinner Gender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        Gender = (Spinner) view.findViewById(R.id.et_gender);
        Speciality = (Spinner)view.findViewById(R.id.et_speciality);

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_name = (EditText)view.findViewById(R.id.et_name);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_confirm_password = (EditText) view.findViewById(R.id.et_confirm_password);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_location = (Spinner) view.findViewById(R.id.et_location);
        et_contact = (EditText) view.findViewById(R.id.et_contact);

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String confirm_password = et_confirm_password.getText().toString();
                String password = et_password.getText().toString();
                String gen = Gender.getSelectedItem().toString();
                String speciality = Speciality.getSelectedItem().toString();
                String location = et_location.getSelectedItem().toString();
                String contact = et_contact.getText().toString();


                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty() && !location.isEmpty() && !contact.isEmpty()) {
                    if (password.equals(confirm_password)) {
                        Constants emailPassed = new Constants();
                        emailPassed.setSHAREDEMAIL(email);
                        progress.setVisibility(View.VISIBLE);
                        registerProcess(name, email, password, gen, speciality, location, contact);
                    } else {
                        Snackbar.make(getView(), "Sorry but the passwords you entered did not match", Snackbar.LENGTH_LONG).show();
                    }

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }


    private void registerProcess(String name, String email, String password, String gen, String speciality, String location, String contact){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gen);
        user.setSpeciality(speciality);
        user.setLocation(location);
        user.setContact(contact);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void goToLogin(){

        LoginFragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
}
