package com.example.rachitshah.volunteer;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Permission_check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);


        if (ContextCompat.checkSelfPermission(Permission_check.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            /*if (ActivityCompat.shouldShowRequestPermissionRationale(Permission_check.this, android.Manifest.permission.READ_CONTACTS)) {

                Toast.makeText(Permission_check.this, "if Permitted", Toast.LENGTH_SHORT);
            } else {*/
            Toast.makeText(Permission_check.this, "Not Permitted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(Permission_check.this, new String[]{android.Manifest.permission.READ_CONTACTS}, 200);
        //}

        } else {
            Toast.makeText(Permission_check.this, "Permitted", Toast.LENGTH_SHORT).show();

        }

    }
}

