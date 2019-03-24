package com.example.rachitshah.volunteer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class Delivery extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    DatabaseReference myref;
    private GoogleMap mMap;
    FirebaseAuth mauth;
    String lattitude, longitude, key;
    String resname, volname, deldate, delloc, delrid;
    Button button;
    TextView textView;
    double lati, longi;
    String address;
    int b, e;
    String f_add;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        textView = (TextView) findViewById(R.id.address);
        button = (Button) findViewById(R.id.deliver_btn);

     //   getPermission();


        myref = FirebaseDatabase.getInstance().getReference("Delivery");
        mauth = FirebaseAuth.getInstance();

        Intent it = getIntent();

        resname = it.getStringExtra("rname");
        volname = it.getStringExtra("vname");
        deldate = it.getStringExtra("deldate");
        delloc = it.getStringExtra("location");
        delrid = it.getStringExtra("Request");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status = "Delivered";
                FirebaseUser user = mauth.getCurrentUser();
                key = user.getUid();

                DeliveryStore deliveryStore = new DeliveryStore();

                Log.e("Latitude", "Is: " + lattitude);

                deliveryStore.setKey(key);
                deliveryStore.setLatitude(lattitude);
                deliveryStore.setLongitude(longitude);
                deliveryStore.setDelplace(delloc);
                deliveryStore.setDeldate(deldate);
                deliveryStore.setRname(resname);
                deliveryStore.setVname(volname);
                deliveryStore.setDelstatus(status);
                deliveryStore.setDelrid(delrid);

                myref.child(key).setValue(deliveryStore);

            }
        });
    }

/*    private void getPermission() {
        if (ContextCompat.checkSelfPermission(Delivery.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Delivery.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            Toast.makeText(Delivery.this, "Permission ", Toast.LENGTH_SHORT).show();
            startActivity(getIntent());

        } else {

            getLocation();

        }


    }*/


    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lati = location.getLatitude();
            longi = location.getLongitude();
            getAddress();

        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    private void getAddress() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Geocoder geo = new Geocoder(Delivery.this);
            List<Address> l;
            try {

                l = geo.getFromLocation(lati, longi, 1);
                address = l.get(0).toString();
                b = address.indexOf("\"");
                e = address.lastIndexOf("\"");
                f_add = address.substring(b + 1, e);
                Toast.makeText(Delivery.this, f_add, Toast.LENGTH_SHORT).show();
                textView.setText(f_add);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Delivery.this, "Unable to Fetch Current Location", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng gt = new LatLng(23.028459, 72.546240);
        mMap.addMarker(new MarkerOptions().position(gt).title("Gulbai Tekra"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gt, 10f));

        LatLng ctm = new LatLng(22.993432, 72.630708);
        mMap.addMarker(new MarkerOptions().position(ctm).title("CTM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gt, 10f));


        LatLng sal = new LatLng(23.049787, 72.524592);
        mMap.addMarker(new MarkerOptions().position(sal).title("Sal Hospital"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sal, 10f));


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(Delivery.this);
            mMap.setOnMyLocationClickListener(this);

        }
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {

        getLocation();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
