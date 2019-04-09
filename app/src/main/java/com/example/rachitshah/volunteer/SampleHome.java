package com.example.rachitshah.volunteer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SampleHome extends AppCompatActivity {

    String key, rname, desc, fddate, vname, status, key1, place, rid, address,stremail;
    ImageButton btnyes, btnno;
    DatabaseReference myref, myref2,volunteerinfo;
    FirebaseAuth mauth;
    TextView textView, resname, resloc, call, contact;
    Button pickup;


    /*
    public SampleHome(String key) {
        this.key= key;
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_home);

        Intent it = getIntent();
        key = it.getStringExtra("key");
        rname = it.getStringExtra("rname");
        fddate = it.getStringExtra("date");
        address = it.getStringExtra("Address");
        rid = it.getStringExtra("rid");
        desc = it.getStringExtra("desc");
        textView = (TextView) findViewById(R.id.ln);
        resloc = (TextView) findViewById(R.id.resloc);
        resname = (TextView) findViewById(R.id.resname);
        pickup = (Button) findViewById(R.id.pickup);
        call = (TextView) findViewById(R.id.call);
        contact = (TextView) findViewById(R.id.contact);


        resname.setText(rname);
        resloc.setText(address);

        btnyes = (ImageButton) findViewById(R.id.yes);
        btnno = (ImageButton) findViewById(R.id.no);
        textView.setText(desc);
        volunteerinfo = FirebaseDatabase.getInstance().getReference("Volunteers");
        myref = FirebaseDatabase.getInstance().getReference("FoodRequest");
        myref2 = FirebaseDatabase.getInstance().getReference("FoodRequestAcceptance");
        place = "Gulbai Tekra";

        mauth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer_log", Context.MODE_PRIVATE);
        stremail = sharedPreferences.getString("Email", "");
        volunteerinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Volunteers users = snapshot.getValue(Volunteers.class);
                    if(stremail.equals(users.getemail())){
                        vname=users.getVname();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8989460723"));
                startActivity(intent);

            }
        });

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getdata();
                status = "Accepted";

                FirebaseUser user = mauth.getCurrentUser();
                key1 = user.getUid();

                Log.e("RESAAANAME", "IS: " + rname);
                FoodRequestAcceptance foodRequestAcceptance = new FoodRequestAcceptance();
                foodRequestAcceptance.setKey(key1);
                foodRequestAcceptance.setFrid(key);
                foodRequestAcceptance.setRname(rname);
                foodRequestAcceptance.setVname(vname);
                foodRequestAcceptance.setLoc(place);
                foodRequestAcceptance.setFddate(fddate);
                foodRequestAcceptance.setAddress(address);
                foodRequestAcceptance.setRid(rid);
                foodRequestAcceptance.setStatus(status);

                myref2.child(key1).setValue(foodRequestAcceptance);

                Log.e("KEYKEY", "Key is" + key1);
                setVisibility();


            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                status = "Declined";

                FirebaseUser user = mauth.getCurrentUser();
                key1 = user.getUid();

                FoodRequestAcceptance foodRequestAcceptance = new FoodRequestAcceptance();
                foodRequestAcceptance.setKey(key1);
                foodRequestAcceptance.setFrid(key);
                foodRequestAcceptance.setRname(rname);
                foodRequestAcceptance.setVname(vname);
                foodRequestAcceptance.setLoc(place);
                foodRequestAcceptance.setFddate(fddate);
                foodRequestAcceptance.setAddress(address);
                foodRequestAcceptance.setRid(rid);
                foodRequestAcceptance.setStatus(status);

                myref2.child(key1).setValue(foodRequestAcceptance);

            }
        });

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleHome.this, Delivery.class);
                intent.putExtra("rname", rname);
                intent.putExtra("vname", vname);
                intent.putExtra("location", place);
                intent.putExtra("Request", rid);
                intent.putExtra("deldate", fddate);

                startActivity(intent);
            }
        });


    }

    private void setVisibility() {
        btnyes.setVisibility(View.GONE);
        btnno.setVisibility(View.GONE);
        call.setVisibility(View.VISIBLE);
        contact.setVisibility(View.VISIBLE);
        pickup.setVisibility(View.VISIBLE);


    }

    private void getdata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer", Context.MODE_PRIVATE);
        vname = sharedPreferences.getString("Name", "");

    }


}
