package com.example.aquib.smartwatch;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyService extends Service {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference TempReference = mRootReference.child("Temperature");
    private DatabaseReference PulseReference = mRootReference.child("Pulse");
    private ValueEventListener handler;
    String pulse = "128";
    int notificationId1 = 11;
    int notificationId2 = 12;
    int notificationId3 = 13;
    String temperature = "28.0";
    NotificationCompat.Builder mBuilder, mBuilder2, mBuilder3;
    double latitude, longitude;
    double latitude1, longitude1, latitude2, longitude2, latitude3, longitude3, latitude4, longitude4;
    private DatabaseReference LatitudeReference = mRootReference.child("Latitude");
    private DatabaseReference LongitudeReference = mRootReference.child("Longitude");

    LatLng currentLoc, point1, point2, point3, point4;
    ArrayList<LatLng> vertices;
    boolean kidEscaped;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();

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
        point1 = new LatLng(latitude1, longitude1);
        point2 = new LatLng(latitude2, longitude2);
        point3 = new LatLng(latitude3, longitude3);
        point4 = new LatLng(latitude4, longitude4);

        vertices = new ArrayList<>();
        vertices.add(point1);
        vertices.add(point2);
        vertices.add(point3);
        vertices.add(point4);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, 0);
        mBuilder = new NotificationCompat.Builder(this, null)
                .setSmallIcon(R.drawable.ic_launcherx)
                .setContentTitle("Warning!")
                .setContentText("Your child has removed the watch")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[0])
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        mBuilder2 = new NotificationCompat.Builder(this, null)
                .setSmallIcon(R.drawable.ic_launcherx)
                .setContentTitle("Alert!")
                .setContentText("Your child's Temperature is very high")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[0])
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        mBuilder3 = new NotificationCompat.Builder(this, null)
                .setSmallIcon(R.drawable.ic_launcherx)
                .setContentTitle("Alert!")
                .setContentText("Your child is out of Boundary")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[0])
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);



        handler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(String.class) != null) {
                    String key = dataSnapshot.getKey();
                    if (key.equals("Temperature")) {
                        temperature = dataSnapshot.getValue(String.class);
                        if (Double.parseDouble(temperature) <= 27.0) {
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyService.this);
                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(notificationId1, mBuilder.build());
                        }
                        if (Double.parseDouble(temperature) >= 40.0) {
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyService.this);
                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(notificationId2, mBuilder2.build());
                        }
                    }
                  /*  else if (key.equals("Pulse")) {
                        pulse = dataSnapshot.getValue(String.class);
                        if (Double.parseDouble(pulse) >= 220) {
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyService.this);
                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(notificationId2, mBuilder2.build());
                        }
                    }*/

                    else if (key.equals("Longitude")) {
                        String longitudex = dataSnapshot.getValue(String.class);
                        longitude = Double.parseDouble(longitudex);
                    }
                    else if (key.equals("Latitude")) {
                        String latitudex = dataSnapshot.getValue(String.class);
                        latitude = Double.parseDouble(latitudex);
                    }

                    if(key.equals("Latitude") || key.equals("Longitude")) {
                        currentLoc = new LatLng(latitude, longitude);
                        kidEscaped = isPointInPolygon(currentLoc, vertices);
                        if(kidEscaped == false) {
                            Toast.makeText(MyService.this, "Location Status is " + kidEscaped, Toast.LENGTH_SHORT).show();
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyService.this);
                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(notificationId3, mBuilder3.build());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        TempReference.addValueEventListener(handler);
        PulseReference.addValueEventListener(handler);
        LatitudeReference.addValueEventListener(handler);
        LongitudeReference.addValueEventListener(handler);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

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

}
