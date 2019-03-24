package com.example.rachitshah.volunteer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class vol_profile extends AppCompatActivity {

    String path;
    Uri imageUri;
    ImageView img;
    ImageButton save;
    private static final int PICK_IMAGE = 100;
    EditText email, add, pswd, phone, name;
    DatabaseReference myref;
    String vemail, vaddress, vphone, vname, key;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_vol_profile);

        myref = FirebaseDatabase.getInstance().getReference("Volunteer");
        name = (EditText) findViewById(R.id.prof_name);
        email = (EditText) findViewById(R.id.prof_email);
        add = (EditText) findViewById(R.id.prof_address);
        pswd = (EditText) findViewById(R.id.prof_pswd);
        phone = (EditText) findViewById(R.id.prof_phone);
        img = (ImageView) findViewById(R.id.profile);
        save = (ImageButton) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.bcbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getdata();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean notnull;
                String vnames, vemails, vadd, vphones;

                vnames = name.getText().toString().trim();
                vemails = email.getText().toString().trim();
                vadd = add.getText().toString().trim();
                vphones = phone.getText().toString().trim();


                notnull = isnotnull();
                if (notnull) {
                    myref.child(key).child("vname").setValue(vnames);
                    myref.child(key).child("email").setValue(vemails);
                    myref.child(key).child("vaddress").setValue(vadd);
                    myref.child(key).child("mob").setValue(vphones);


                    savedata();
                }

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);

            }
        });

/*
        pswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });*/

        pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Dialog dialogbox = new Dialog();
                dialogbox.show(getSupportFragmentManager(), "Custom Dialog");
                savedata();*/

                final Dialog dialog = new Dialog(vol_profile.this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("Change Password");


                EditText old = (EditText) dialog.findViewById(R.id.text);
                EditText newpass = (EditText) dialog.findViewById(R.id.text);
                EditText renew = (EditText) dialog.findViewById(R.id.text);



                Button dialogButton = (Button) dialog.findViewById(R.id.change_btn);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*getdata();
                        String s1, s2, s3;
                        s1 = old.getText().toString();
                        s2 = newpass.getText().toString();
                        s3 = repass.getText().toString();

                        if(rpassword.compareTo(s1)==0){

                            if(s2.length()==0) {
                                Toast toast = makeText(getActivity(), "Password cannot be empty", LENGTH_SHORT);
                                toast.show();

                            }
                            else {
                                if (s2.compareTo(s3) == 0) {
                                    myref.child(key1).child("passwrd").setValue(s2);
                                    Toast toast = makeText(getActivity(), "Password updated", LENGTH_SHORT);
                                    toast.show();


                                }
                                else{
                                    Toast toast = makeText(getActivity(), "Password should match", LENGTH_SHORT);
                                    toast.show();

                                }
*/


                            }
                });

                dialog.show();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == vol_profile.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            img.setImageURI(imageUri);
            path = String.valueOf(imageUri);
        }
    }

    private void savedata() {

        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("Volunteer", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", name.getText().toString());
        editor.putString("Email", email.getText().toString());
        editor.putString("Phone", phone.getText().toString());
        editor.putString("Address", add.getText().toString());
        editor.putString("Password", pswd.getText().toString());
        editor.putString("Profile", path);
        editor.commit();

        Toast toast = Toast.makeText(vol_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT);
        toast.show();

    }

    private void getdata() {

        SharedPreferences sharedPreferences = getSharedPreferences("Volunteer", MODE_PRIVATE);
        String path = sharedPreferences.getString("Profile", "");

        img.setImageURI(null);
        img.setImageURI(Uri.parse(path));

        key = sharedPreferences.getString("Key", "");
        vname = sharedPreferences.getString("Name", "");
        vemail = sharedPreferences.getString("Email", "");

        vaddress = sharedPreferences.getString("Address", "");
        vphone = sharedPreferences.getString("Phone", "");

        String x;
        x = sharedPreferences.getString("Password", "");
        pswd.setText(x);
        name.setText(vname);
        email.setText(vemail);
        add.setText(vaddress);
        phone.setText(vphone);
    }

    private Boolean isnotnull() {
        boolean notnull = false;
        String n, e, p, ph, addr, licno, gen, bd;
        n = name.getText().toString();
        e = email.getText().toString();
        p = pswd.getText().toString();
        ph = phone.getText().toString();
        addr = add.getText().toString();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = e;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        Boolean check = true;
        if (n.length() == 0) {
            name.setError("Name cannot be Empty");
            check = false;
        }


        if (!matcher.matches()) {
            email.setError("Email not Valid");
            check = false;
        }
        if (p.length() == 0) {
            pswd.setError("Password cannot be Empty");
            check = false;
        }
        if (ph.length() != 10) {
            phone.setError("Phone cannot be Empty");
            check = false;
        }
        if (addr.length() == 0) {
            add.setError("Address cannot be Empty");
            check = false;
        }
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }


}
