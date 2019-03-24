package com.example.rachitshah.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText pswd, usrusr;
    TextView sup, lin, forgotpass;
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener mauthListener;
    String email, passwrd;
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(Login.this);

        mauth = FirebaseAuth.getInstance();
        mauthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               /* Intent it = new Intent(Login.this, Home.class);
                startActivity(it);*/

            }
        };


        forgotpass = (TextView) findViewById(R.id.forgot_pass);
        lin = findViewById(R.id.lin);
        usrusr = findViewById(R.id.usrusr);
        pswd = findViewById(R.id.pswrdd);
        sup = findViewById(R.id.sup);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Signup.class);
                startActivity(it);
            }
        });

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = usrusr.getText().toString().trim();
                CharSequence inputStr = email;
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputStr);
                passwrd = pswd.getText().toString();

                /*Intent it = new Intent(Login.this, Home.class);
                startActivity(it);*/


                if (email.isEmpty()) {
                    usrusr.setError("Email cannot be Empty");
                }


                if (matcher.matches()) {
                    //usrusr.setError("Email not Valid");
                    if (passwrd.isEmpty()) {
                        pswd.setError("Password cannot be Null");
                    } else {
                        signin();

                    }


                }


            }
            //Intent it = new Intent(Login.this, Main2Activity.class);
            //startActivity(it);

        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Forgotpassword.class);
                startActivity(intent);
            }
        });


    }

    private void signin() {
        mauth.signInWithEmailAndPassword(email, passwrd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(passwrd)) {
                    Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Invalid Credentials, Please try again!!", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent it = new Intent(Login.this, Home.class);
                        startActivity(it);

                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthListener);
    }


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_login);
        lin = (TextView)findViewById(R.id.lin);

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Home.class);
                startActivity(it);
            }
        });


    }*/

}
