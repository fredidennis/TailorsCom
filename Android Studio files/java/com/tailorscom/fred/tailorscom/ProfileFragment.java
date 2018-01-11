package com.tailorscom.fred.tailorscom;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tailorscom.fred.tailorscom.galleryUpload_package.UploadGalleryPicMainActivity;
import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;
import com.tailorscom.fred.tailorscom.models.User;
import com.tailorscom.fred.tailorscom.profilePicUpload_package.UploadProfilePicMainActivity;
import com.tailorscom.fred.tailorscom.tailorGallery_package.TailorGalleryActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView tv_name, tv_email, tv_message;
    private String name, emial;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password, btn_logout,
            btn_modify_profile, btn_modify_profile_pic,
            btn_modify_gallery_pic, btn_manage_gallery;
    private EditText et_old_password, et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private ImageView img_prof_pic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getPreferences(0);
        tv_name.setText("Welcome : " + pref.getString(Constants.NAME, ""));
        tv_email.setText(pref.getString(Constants.EMAIL, ""));
        String getProfPicURL = Constants.BASE_URL + "/TailorsCom-login-register/getProfileImage.php?id=" + pref.getString(Constants.NAME, "");
        Picasso.with(getActivity()).load(getProfPicURL).into(img_prof_pic);


    }

    private void initViews(View view) {

        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        btn_manage_gallery = (AppCompatButton) view.findViewById(R.id.btn_manage_gallery);
        btn_modify_gallery_pic = (AppCompatButton) view.findViewById(R.id.btn_modify_gallery_pic);
        btn_modify_profile = (AppCompatButton) view.findViewById(R.id.btn_modify_profile);
        btn_change_password = (AppCompatButton) view.findViewById(R.id.btn_chg_password);
        btn_modify_profile_pic = (AppCompatButton) view.findViewById(R.id.btn_modify_profile_pic);
        btn_logout = (AppCompatButton) view.findViewById(R.id.btn_logout);
        img_prof_pic = (ImageView) view.findViewById(R.id.img_prof_pic);
        btn_modify_gallery_pic.setOnClickListener(this);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_modify_profile.setOnClickListener(this);
        btn_modify_profile_pic.setOnClickListener(this);
        btn_manage_gallery.setOnClickListener(this);

    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText) view.findViewById(R.id.et_old_password);
        et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if (!old_password.isEmpty() && !new_password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    changePasswordProcess(pref.getString(Constants.EMAIL, ""), old_password, new_password);

                } else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_modify_profile:
                goToModProfile();
                break;
            case R.id.btn_modify_gallery_pic:
                emial = pref.getString(Constants.EMAIL, "");
                Constants sendEmail = new Constants();
                sendEmail.setSHAREDEMAIL(emial);

                name = pref.getString(Constants.NAME, "");
                Constants sendName1 = new Constants();
                sendName1.setSHAREDNAME(name);
                goToUploadGallery();
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_modify_profile_pic:

                name = pref.getString(Constants.NAME, "");
                Constants sendName = new Constants();
                sendName.setSHAREDNAME(name);
                goToUploadProfPic();
                break;
            case R.id.btn_manage_gallery:
                Constants action = new Constants();
                String my_name = pref.getString(Constants.NAME,"");
                action.setSHAREDNAME(my_name);
                Intent m = new Intent(getActivity(), TailorGalleryActivity.class);
                startActivity(m);
        }
    }

    private void goToUploadGallery() {
        Intent i = new Intent(getActivity().getApplicationContext(), UploadGalleryPicMainActivity.class);
        startActivity(i);
    }

    private void goToUploadProfPic() {
        //SharedPreferences.Editor editor = pref.edit();
        //editor.putBoolean(Constants.IS_LOGGED_IN,true);
        //editor.putString(Constants.EMAIL,"");
        //editor.putString(Constants.NAME,"");
        //editor.putString(Constants.UNIQUE_ID,"");
        //editor.apply();

        //Bundle bundle = null;
        Intent i = new Intent(getActivity().getApplicationContext(), UploadProfilePicMainActivity.class);
        //bundle.putString("NAME", ""+pref.getString(Constants.NAME,"")+"");
        //i.putExtra("NAME", ""+pref.getString(Constants.NAME,"")+"");
        startActivity(i);
    }

    private void goToModProfile() {
        ProfileModFragment profileMod = new ProfileModFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, profileMod);
        ft.commit();
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, false);
        editor.putString(Constants.EMAIL, "");
        editor.putString(Constants.NAME, "");
        editor.putString(Constants.UNIQUE_ID, "");
        editor.putString(Constants.IMAGE_ID, "");
        editor.apply();
        goToLogin();
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login);
        ft.commit();
    }

    private void changePasswordProcess(String email, String old_password, String new_password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if (resp.getResult().equals(Constants.SUCCESS)) {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                } else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, "failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());


            }
        });
    }

    public void onBackPressed() {
        Toast.makeText(getActivity().getApplicationContext(), "You are now logged out.\n Come back soon", Toast.LENGTH_SHORT).show();
        LoginFragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login);
        ft.commit();
    }

}
