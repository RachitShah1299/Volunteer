package com.example.rachitshah.volunteer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class vol_feedback extends AppCompatActivity {
    ImageView back;
    EditText message;
    DatabaseReference myref2;
    FirebaseAuth mauth;
    String vname,vemail,vphone, msg, key1, key;
    Button submit, cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_vol_feedback);

        myref2 = FirebaseDatabase.getInstance().getReference("Vol Feedback");
        mauth = FirebaseAuth.getInstance();

        submit = (Button) findViewById(R.id.fed_submit);
        cancel = (Button) findViewById(R.id.fed_cancel);
        message = (EditText) findViewById(R.id.fed_msg);

        back = (ImageView) findViewById(R.id.bcbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                FirebaseUser user = mauth.getCurrentUser();
                key = user.getUid();

                msg = message.getText().toString().trim();


                Feedback feedback = new Feedback();
                feedback.setKey(key);
                feedback.setRname(vname);
                feedback.setRemail(vemail);
                feedback.setMob(vphone);
                feedback.setMsg(msg);

                myref2.child(key).setValue(feedback);
                finish();
            }
        });


    }

    private void getData() {

        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer", Context.MODE_PRIVATE);
        key1 = sharedPreferences.getString("Key", "");

        vname = sharedPreferences.getString("Name", "");
        vemail = sharedPreferences.getString("Email", "");
        vphone = sharedPreferences.getString("Phone", "");





    }

}
