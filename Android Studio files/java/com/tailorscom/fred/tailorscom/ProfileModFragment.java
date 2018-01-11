package com.tailorscom.fred.tailorscom;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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



import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;
import com.tailorscom.fred.tailorscom.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileModFragment extends Fragment implements View.OnClickListener{

    private AppCompatButton btn_save_chng,btn_loginAgain;
    private EditText et_old_name,et_name,et_email, et_chng_contact, service_rate, et_chng_desc;
    private Spinner et_speciality, et_location;
    //private ProgressBar progress;
    private SharedPreferences pref;
    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_mod,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getPreferences(0);
        et_old_name.setText(""+pref.getString(Constants.NAME,""));
        et_email.setText(""+pref.getString(Constants.EMAIL,""));

    }

    private void initViews(View view) {
        et_old_name = (EditText) view.findViewById(R.id.et_oldname);
        et_name = (EditText) view.findViewById(R.id.et_chng_name);
        et_email = (EditText) view.findViewById(R.id.et_chng_email);
        et_location =(Spinner) view.findViewById(R.id.et_chng_location);
        et_chng_contact =(EditText) view.findViewById(R.id.et_chng_contact);
        service_rate = (EditText) view.findViewById(R.id.et_chng_service_rate);
        et_chng_desc = (EditText) view.findViewById(R.id.et_chng_desc);

        et_speciality =(Spinner) view.findViewById(R.id.et_chng_speciality);

        //progress = (ProgressBar)view.findViewById(R.id.chng_progress);

        btn_save_chng = (AppCompatButton) view.findViewById(R.id.btn_save_chng);
        btn_loginAgain = (AppCompatButton) view.findViewById(R.id.btn_login_again);

        btn_save_chng.setOnClickListener(this);
        btn_loginAgain.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_save_chng:
                String oldname = et_old_name.getText().toString();
                String newname = et_name.getText().toString();
                String newemail = et_email.getText().toString();
                String newlocation = et_location.getSelectedItem().toString();
                String newspeciality = et_speciality.getSelectedItem().toString();
                String newContact = et_chng_contact.getText().toString();
                String newRate = service_rate.getText().toString();
                String newDesc = et_chng_desc.getText().toString();


                if(!oldname.isEmpty() && !newname.isEmpty() && !newemail.isEmpty() && !newlocation.isEmpty() && !newspeciality.isEmpty()) {

                    //progress.setVisibility(View.VISIBLE);
                    loading = ProgressDialog.show(getActivity(),"Updating Profile","please wait...",true,true);
                    editProcess(oldname, newname, newemail, newlocation, newspeciality, newContact, newRate, newDesc);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_login_again:
                logOut();
                break;
        }
    }

    private void editProcess(String oldname, String newname, String newemail, String newlocation,
                             String newspeciality, String newContact, String newRate, String newDesc) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setNewName(newname);
        user.setOldName(oldname);
        user.setNewEmail(newemail);
        user.setNewSpeciality(newspeciality);
        user.setNewLocation(newlocation);
        user.setNewContact(newContact);
        user.setNewRate(newRate);
        user.setNewDesc(newDesc);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.EDIT_PROFILE_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {


                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage()+"\nPLEASE LOGIN AGAIN", Snackbar.LENGTH_LONG).show();
                loading.dismiss();
                //progress.setVisibility(View.INVISIBLE);
                logOut();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                //progress.setVisibility(View.INVISIBLE);
                loading.dismiss();
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void logOut(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,true);
        editor.putString(Constants.EMAIL, et_email.getText().toString());
        editor.apply();
        goToLogin();
    }

    private void goToLogin() {
        LoginFragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.getActivity().onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
