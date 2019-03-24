package com.example.rachitshah.volunteer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name, email, demo;
    ImageView imageView;

    ListView listView;
    DatabaseReference myref;

    ArrayList<FoodRequest> foodrequest;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_home);
        loadBar();

        myref = FirebaseDatabase.getInstance().getReference("FoodRequest");


        listView = (ListView) findViewById(R.id.vol_home_liv);

        foodrequest = new ArrayList<>();


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("KEYKEY", "BEFORE LOOOP");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FoodRequest users = snapshot.getValue(FoodRequest.class);
                    foodrequest.add(users);

                    Log.e("KEYKEY", "KEY IS " + users.getKey());

                }


                Vol_home_request vol_home_request = new Vol_home_request(Home.this, foodrequest);
                listView.setAdapter(vol_home_request);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    private void loadBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.nav_name);
        email = (TextView) header.findViewById(R.id.nav_email);
           imageView = (ImageView) header.findViewById(R.id.header_image);

        String strname, stremail;
        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer", MODE_PRIVATE);

        /*Uri uri = Uri.parse(sharedPreferences.getString("Profile", "???"));
        imageView.setImageURI(null);
        imageView.setImageURI(uri);*/

        imageView.setImageResource(R.drawable.vv);

        strname = sharedPreferences.getString("Name", "");
        stremail = sharedPreferences.getString("Email", "");

        name.setText(strname);
        email.setText(stremail);


    }


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
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {


        } else if (id == R.id.recent) {

            Intent intent = new Intent(Home.this, Recent.class);
            startActivity(intent);


        } else if (id == R.id.edit_profile) {

            Intent intent = new Intent(Home.this, vol_profile.class);
            startActivity(intent);

        } else if (id == R.id.feedback) {

            Intent intent = new Intent(Home.this, vol_feedback.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
