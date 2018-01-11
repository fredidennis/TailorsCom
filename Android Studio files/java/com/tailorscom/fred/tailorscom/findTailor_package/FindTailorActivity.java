package com.tailorscom.fred.tailorscom.findTailor_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tailorscom.fred.tailorscom.R;

public class FindTailorActivity extends AppCompatActivity {

    private AppCompatButton btn_find, btn_find_a_all;
    private ProgressBar progress;
    private Spinner byGender, bySpeciality, byLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tailor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btn_find = (AppCompatButton) findViewById(R.id.btn_find);
        btn_find_a_all = (AppCompatButton) findViewById(R.id.btn_find_all);
        progress = (ProgressBar) findViewById(R.id.progress);

        byGender = (Spinner) findViewById(R.id.et_by_gender);
        bySpeciality = (Spinner) findViewById(R.id.et_bt_speciality);
        byLocation = (Spinner) findViewById(R.id.et_by_location);

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speciality = bySpeciality.getSelectedItem().toString();
                String gender = byGender.getSelectedItem().toString();
                String location = byLocation.getSelectedItem().toString();

                if(!speciality.isEmpty() && !gender.isEmpty() && !location.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    findProcess(speciality,gender,location);

                } else {
                    Toast.makeText(FindTailorActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_find_a_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFindAll();
            }
        });
    }

    private void goToFindAll() {
        Intent f = new Intent(FindTailorActivity.this, FindAllTailorsActivity.class);
        startActivity(f);
    }

    private void findProcess(String speciality, String gender, String location) {

        FindVariables set = new FindVariables();
        set.setGenderSelected(gender);
        set.setLocationSelected(location);
        set.setSpecialitySelected(speciality);
        progress.setVisibility(View.INVISIBLE);
        goToResult();


    }

    private void goToResult() {
        Intent r = new Intent(this, FindTailorFeedActivity.class);
        startActivity(r);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }
}
