package com.example.rachitshah.volunteer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    String key, rname, desc, fddate, vname, status, key1, place,rid,address;
    ImageButton btnyes, btnno;
    DatabaseReference myref, myref2;
    FirebaseAuth mauth;
    TextView textView,resname,resloc;

    /*public SampleHome(String key) {
        this.key= key;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_home);

        Intent it = getIntent();
        String key = it.getStringExtra("key");
        rname = it.getStringExtra("rname");
        fddate = it.getStringExtra("date");
        address = it.getStringExtra("Address");
        rid = it.getStringExtra("rid");
        desc = it.getStringExtra("desc");
        textView= (TextView)findViewById(R.id.ln);
        resloc= (TextView)findViewById(R.id.resloc);
        resname= (TextView)findViewById(R.id.resname);

        resname.setText(rname);
        resloc.setText(address);

        btnyes = (ImageButton) findViewById(R.id.yes);
        btnno = (ImageButton) findViewById(R.id.no);
        textView.setText(desc);
        myref = FirebaseDatabase.getInstance().getReference("FoodRequest");
        myref2 = FirebaseDatabase.getInstance().getReference("FoodRequestAcceptance");
        place = "Gulbai Tekra";

        mauth = FirebaseAuth.getInstance();

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getdata();
                status = "Accepted";

                FirebaseUser user = mauth.getCurrentUser();
                key1 = user.getUid();

                Log.e("RESAAANAME","IS: "+ rname);
                FoodRequestAcceptance foodRequestAcceptance = new FoodRequestAcceptance();
                foodRequestAcceptance.setKey(key1);
                foodRequestAcceptance.setRname(rname);
                foodRequestAcceptance.setVname(vname);
                foodRequestAcceptance.setLoc(place);
                foodRequestAcceptance.setFddate(fddate);
                foodRequestAcceptance.setAddress(address);
                foodRequestAcceptance.setRid(rid);
                foodRequestAcceptance.setStatus(status);

                myref2.child(key1).setValue(foodRequestAcceptance);

                Log.e("KEYKEY","Key is"+key1);
                Intent intent = new Intent(SampleHome.this, Delivery.class);

                intent.putExtra("rname",rname);
                intent.putExtra("vname",vname);
                intent.putExtra("location",place);
                intent.putExtra("Request",rid);
                intent.putExtra("deldate",fddate);
                startActivity(intent);

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


    }

    private void getdata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer", Context.MODE_PRIVATE);
        vname = sharedPreferences.getString("Name", "");

    }
}
