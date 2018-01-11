package com.tailorscom.fred.tailorscom;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.tailorscom.fred.tailorscom.findTailor_package.FindTailorActivity;
import com.tailorscom.fred.tailorscom.galleryFeed_package.GalleryFeed;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //pref = getPreferences(0);
        //initFragment();


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.getDrawerElevation();
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //private void initFragment(){
     //   android.app.Fragment fragment;
     //   if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
     //       fragment = new android.app.Fragment();
     //   }else {
     //       fragment = new LoginFragment();
    //    }
     //   FragmentTransaction ft = getFragmentManager().beginTransaction();
      //  ft.replace(R.id.fragment_frame,fragment);
      //  ft.commit();
   // }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),
                    "A brief summary has been recorded and sent. THANK YOU FOR UR FEEDBACK", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_login) {
            //LoginFragment fragment = new LoginFragment();
            //android.support.v4.app.FragmentTransaction fragmentTransaction =
            //        getSupportFragmentManager().beginTransaction().replace(R.id.cont_main,
            //        fragment);
            //fragmentTransaction.commit();
            LoginFragment login = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, login);
            ft.commit();
        }else if(id == R.id.nav_register){
            RegisterFragment register = new RegisterFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, register);
            ft.commit();
        } else if (id == R.id.nav_gallery) {
            Intent z = new Intent(MainActivity.this, GalleryFeed.class);
            startActivity(z);
        } else if (id == R.id.nav_find_a_tailor) {
            Intent f = new Intent(MainActivity.this, FindTailorActivity.class);
            startActivity(f);

        } else if (id == R.id.nav_faq) {
            AboutUsFragment aboutus = new AboutUsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, aboutus);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
