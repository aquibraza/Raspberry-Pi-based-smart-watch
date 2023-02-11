package com.example.aquib.smartwatch;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TempPulse extends AppCompatActivity implements ValueEventListener {

    private TextView TempReading, PulseReading;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference TempReference = mRootReference.child("Temperature");
    private DatabaseReference PulseReference = mRootReference.child("Pulse");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_temp_pulse);
        TempReading = (TextView) findViewById(R.id.tempReading);
        PulseReading = (TextView) findViewById(R.id.pulseReading);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot){
        if (dataSnapshot.getValue(String.class) != null)
        {
            String key = dataSnapshot.getKey();
            if (key.equals("Temperature")) {
                String temperature = dataSnapshot.getValue(String.class);
                temperature= temperature.substring(0,4);
                temperature = "Temperature - " + temperature + (char)0x00B0;
                TempReading.setText(temperature);

            }
            else if (key.equals("Pulse")) {
                String pulse = dataSnapshot.getValue(String.class);
                pulse = "Pulse - " + pulse + " BPM";
                PulseReading.setText(pulse);
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        TempReference.addValueEventListener(this);
        PulseReference.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
