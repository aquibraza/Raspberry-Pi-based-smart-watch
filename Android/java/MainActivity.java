package com.example.aquib.smartwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements ValueEventListener {

    Button location, temp_pulse, settings, buzzer, message;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference BuzzReference = mRootReference.child("Buzzer");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        location = (Button)findViewById(R.id.locationFind);
        temp_pulse = (Button)findViewById(R.id.tempPulseDisplay);
        settings = (Button)findViewById(R.id.settingsAdjust);
        buzzer = (Button)findViewById(R.id.buzzerRing);
        message = (Button)findViewById(R.id.messageSend);

        location.setOnClickListener(new startLocation());
        temp_pulse.setOnClickListener(new startTempPulse());
        settings.setOnClickListener(new startSettings());
     //   buzzer.setOnClickListener(new startBuzzer());
        message.setOnClickListener(new startMessages());

        startService(new Intent(this, MyService.class));
    }

    public void buzzerRing(View view)
    {
        Toast.makeText(this, "BUZZZING !!!", Toast.LENGTH_SHORT).show();
        BuzzReference.setValue(1);
    }

    @Override
    protected void onStart(){
        super.onStart();
        BuzzReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    class startLocation implements View.OnClickListener{

        public void onClick(View v){
            Intent in = new Intent(MainActivity.this, Location.class);
            startActivity(in);

        }
    }

    class startTempPulse implements View.OnClickListener{
        public void onClick(View v){
            Intent in = new Intent(MainActivity.this, TempPulse.class);
            startActivity(in);

        }
    }

    class startSettings implements View.OnClickListener{
        public void onClick(View v){
            Intent in = new Intent(MainActivity.this, Settings.class);
            startActivity(in);

        }
    }
    class startMessages implements View.OnClickListener{
        public void onClick(View v){
            Intent in = new Intent(MainActivity.this, Messages.class);
            startActivity(in);

        }
    }
}
