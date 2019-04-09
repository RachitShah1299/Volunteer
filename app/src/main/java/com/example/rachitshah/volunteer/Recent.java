package com.example.rachitshah.volunteer;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Recent extends AppCompatActivity {
    ListView listView;
    DatabaseReference myref,myref2;
    ArrayList<FoodRequestAcceptance> foodRequestAcceptances;
    ImageView back;
    String vemail,volname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen*/

        setContentView(R.layout.activity_recent);

        myref = FirebaseDatabase.getInstance().getReference("FoodRequestAcceptance");
        myref2 = FirebaseDatabase.getInstance().getReference("Volunteers");
        listView = (ListView) findViewById(R.id.Rec_liv);
        foodRequestAcceptances = new ArrayList<>();
        back= (ImageView)findViewById(R.id.bcbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*SharedPreferences sharedPreferences = getSharedPreferences("Volunteer_log", MODE_PRIVATE);
        vemail = sharedPreferences.getString("Email","");
*/
        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer_log", MODE_PRIVATE);
        vemail = sharedPreferences.getString("Email","");
        Log.e("Emailvol","Email is"+vemail);

        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Volunteers users = snapshot.getValue(Volunteers.class);
                    if(vemail.equals(users.getemail())){
                        volname= users.getVname();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FoodRequestAcceptance users = snapshot.getValue(FoodRequestAcceptance.class);
                    Log.e("Emailus","Email is"+ vemail);
                    Log.e("Volname","Vollname"+volname);
                    if(volname.equals(users.getVname())){
                        foodRequestAcceptances.add(users);
                    }


                }

                RecentDisplay recentDisplay = new RecentDisplay(Recent.this, foodRequestAcceptances);
                listView.setAdapter(recentDisplay);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
