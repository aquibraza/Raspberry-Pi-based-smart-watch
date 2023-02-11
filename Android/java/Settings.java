package com.example.aquib.smartwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    Button set_location, about;
    int notification =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_settings);

        set_location = (Button)findViewById(R.id.changeLocation);
        about = (Button)findViewById(R.id.AboutMsg);

        set_location.setOnClickListener(new Settings.startSetLocation());
        about.setOnClickListener(new Settings.about());
    }

    class startSetLocation implements View.OnClickListener{

        public void onClick(View v){
            Intent in = new Intent(Settings.this, SetLocation.class);
            startActivity(in);

        }
    }

    class about implements View.OnClickListener{
        public void onClick(View v) {
            Toast.makeText(Settings.this, "This has been one hell of a Ride - Group 8 Desi Kings", Toast.LENGTH_SHORT).show();
         /*   if (notification == 0){
                notification = 1;
           //     Toast.makeText(this, "Notifications are ON", Toast.LENGTH_SHORT).show();
            }
            else {
                notification = 0;
                //      Toast.makeText(this, "Notifications are ON", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

}
