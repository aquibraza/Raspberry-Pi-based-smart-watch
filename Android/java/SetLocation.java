package com.example.aquib.smartwatch;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polygon;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;
import android.content.Context;
import android.widget.Toast;


import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.Geofence;



public class SetLocation extends FragmentActivity implements OnMapReadyCallback, OnMapLongClickListener, ValueEventListener {
    private String ll;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference LatitudeReference = mRootReference.child("Latitude");
    private DatabaseReference LongitudeReference = mRootReference.child("Longitude");
    private DatabaseReference P1LatReference = mRootReference.child("P1Lat");
    private DatabaseReference P1LongReference = mRootReference.child("P1Long");
    private DatabaseReference P2LatReference = mRootReference.child("P2Lat");
    private DatabaseReference P2LongReference = mRootReference.child("P2Long");
    private DatabaseReference P3LatReference = mRootReference.child("P3Lat");
    private DatabaseReference P3LongReference = mRootReference.child("P3Long");
    private DatabaseReference P4LatReference = mRootReference.child("P4Lat");
    private DatabaseReference P4LongReference = mRootReference.child("P4Long");


    private GoogleMap mMap;
    int corner=0;
    String bound1, bound2, bound3, bound4;
    double latitude;
    double longitude;
    LatLng point1;
    LatLng point2;
    LatLng point3;
    LatLng point4;
    LatLng currentLoc;
    boolean kidEscaped;

//    private GeofencingClient mGeofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currentLoc = new LatLng(39.8, 32.7);
        mMap.addMarker(new MarkerOptions().position(currentLoc).title("Marker in Bilkent"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));

        CameraPosition INIT =
                new CameraPosition.Builder()
                        .target(currentLoc)
                        .zoom(17.5F)
         //               .bearing(300F) // orientation
        //                .tilt(50F) // viewing angle
                        .build();
        // use GooggleMap mMap to move camera into position
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));

        mMap.setOnMapLongClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        LatitudeReference.addValueEventListener(this);
        LongitudeReference.addValueEventListener(this);
        P1LatReference.addValueEventListener(this);
        P1LongReference.addValueEventListener(this);
        P2LatReference.addValueEventListener(this);
        P2LongReference.addValueEventListener(this);
        P3LatReference.addValueEventListener(this);
        P3LongReference.addValueEventListener(this);
        P4LatReference.addValueEventListener(this);
        P4LongReference.addValueEventListener(this);
    }

    @Override
    public void onMapLongClick(LatLng point) {
        if(corner >= 4)
            return;

        mMap.addMarker(new MarkerOptions().position(point).title("M1"));
        updateCorner(point);
    }

    public void updateCorner(LatLng point) {
        if (corner == 0)
            point1 = point;
        if (corner == 1)
            point2 = point;
        if (corner == 2)
            point3 = point;
        if (corner == 3){
            point4 = point;
            // Instantiates a new Polygon object and adds points to define a rectangle
            PolygonOptions rectOptions = new PolygonOptions()
                    .add(point1,point2,point3,point4,point1);

            // Get back the mutable Polygon
            Polygon polygon = mMap.addPolygon(rectOptions);
            rectOptions.fillColor(Color.argb(128, 255, 0, 0));

            //Create Array List for points of polygon
            ArrayList<LatLng> vertices = new ArrayList<>();
            vertices.add(point1);
            vertices.add(point2);
            vertices.add(point3);
            vertices.add(point4);
            kidEscaped = isPointInPolygon(currentLoc, vertices);
            Toast.makeText(this, "Location Status is " + kidEscaped, Toast.LENGTH_SHORT).show();


            //Store the points in a text file
            File file = getFileStreamPath("bound.txt");
            try {
                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();
                FileOutputStream writer = openFileOutput(file.getName(), Context.MODE_APPEND);
                writer.write(String.format("%.8f", point1.latitude).getBytes());
                writer.write(String.format("%n%.8f",point1.longitude).getBytes());
                writer.write(String.format("%n%.8f", point2.latitude).getBytes());
                writer.write(String.format("%n%.8f",point2.longitude).getBytes());
                writer.write(String.format("%n%.8f", point3.latitude).getBytes());
                writer.write(String.format("%n%.8f",point3.longitude).getBytes());
                writer.write(String.format("%n%.8f", point4.latitude).getBytes());
                writer.write(String.format("%n%.8f",point4.longitude).getBytes());
                writer.flush();
                Log.d("Out", "File created");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
/*
            try {
                InputStream inputStream = openFileInput("bound.txt");

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    String str;

                    while ((str = bufferedReader.readLine()) != null) {
                        Log.d("Out", str);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }
        corner++;
    }



    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue(String.class) != null) {
            String key = dataSnapshot.getKey();
            if (key.equals("Longitude")) {
                String longitudex = dataSnapshot.getValue(String.class);
                longitude = Double.parseDouble(longitudex);
            } else if (key.equals("Latitude")) {
                String latitudex = dataSnapshot.getValue(String.class);
                latitude = Double.parseDouble(latitudex);
            }

            if(key.equals("Latitude") || key.equals("Longitude")) {
                currentLoc = new LatLng(latitude, longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(currentLoc).title("Your Child's Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));

                CameraPosition INIT =
                        new CameraPosition.Builder()
                                .target(currentLoc)
                                .zoom(17.5F)
                //                .bearing(300F) // orientation
               //                 .tilt(50F) // viewing angle
                                .build();
                // use GooggleMap mMap to move camera into position
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));
            }
        }
    }

    private boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
