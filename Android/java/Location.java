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
import com.example.aquib.smartwatch.SetLocation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Location extends FragmentActivity implements OnMapReadyCallback, ValueEventListener {

    private GoogleMap mMap;
    double latitude, longitude;
    double latitude1, longitude1, latitude2, longitude2, latitude3, longitude3, latitude4, longitude4;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference LatitudeReference = mRootReference.child("Latitude");
    private DatabaseReference LongitudeReference = mRootReference.child("Longitude");

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

        // Add a marker in Location and move the camera
        LatLng currentLoc = new LatLng(39.8, 32.7);
    //    LatLng currentLoc = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(currentLoc).title("Marker in Bilkent"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));

        CameraPosition INIT =
                new CameraPosition.Builder()
                        .target(currentLoc)
                        .zoom(17.5F)
                        .bearing(300F) // orientation
                        .tilt(50F) // viewing angle
                        .build();
        // use GoogleMap mMap to move camera into position
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));

/*
        try {
            InputStream inputStream = openFileInput("bound.txt");

            if (inputStream != null) {
                Log.d("fileTest", "File has been Found");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                latitude1 = Double.parseDouble(bufferedReader.readLine());
                longitude1 = Double.parseDouble(bufferedReader.readLine());
                latitude2 = Double.parseDouble(bufferedReader.readLine());
                longitude2 = Double.parseDouble(bufferedReader.readLine());
                latitude3 = Double.parseDouble(bufferedReader.readLine());
                longitude3 = Double.parseDouble(bufferedReader.readLine());
                latitude4 = Double.parseDouble(bufferedReader.readLine());
                longitude4 = Double.parseDouble(bufferedReader.readLine());

            }
            else
                Log.d("fileTest", "File Not Found");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fileTest", "File Doesn't Exist");
        }

        LatLng point1 = new LatLng(latitude1, longitude1);
        mMap.addMarker(new MarkerOptions().position(point1).title("1"));
        LatLng point2 = new LatLng(latitude2, longitude2);
        mMap.addMarker(new MarkerOptions().position(point2).title("2"));
        LatLng point3 = new LatLng(latitude3, longitude3);
        mMap.addMarker(new MarkerOptions().position(point3).title("3"));
        LatLng point4 = new LatLng(latitude4, longitude4);
        mMap.addMarker(new MarkerOptions().position(point4).title("4"));
        Log.d("fileRead", String.valueOf(point1));
        Log.d("fileRead", String.valueOf(point2));
        Log.d("fileRead", String.valueOf(point3));
        Log.d("fileRead", String.valueOf(point4));
        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptions = new PolygonOptions()
                .add(point1, point2, point3, point4, point1);

        // Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptions);
        rectOptions.fillColor(Color.argb(128, 255, 119, 0));*/
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot){
        if (dataSnapshot.getValue(String.class) != null)
        {
            String key = dataSnapshot.getKey();
            if (key.equals("Longitude")) {
                String longitudex = dataSnapshot.getValue(String.class);
                longitude = Double.parseDouble(longitudex);
            }
            else if (key.equals("Latitude")) {
                String latitudex = dataSnapshot.getValue(String.class);
                latitude = Double.parseDouble(latitudex);
            }

            if(key.equals("Latitude") || key.equals("Longitude")) {
                LatLng currentLoc = new LatLng(latitude, longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(currentLoc).title("Your Child's Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));

                CameraPosition INIT =
                        new CameraPosition.Builder()
                                .target(currentLoc)
                                .zoom(17.5F)
                                .bearing(300F) // orientation
                                .tilt(50F) // viewing angle
                                .build();
                // use GooggleMap mMap to move camera into position
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));
            }


        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        LatitudeReference.addValueEventListener(this);
        LongitudeReference.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

}
