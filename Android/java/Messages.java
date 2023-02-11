package com.example.aquib.smartwatch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ValueEventListener {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference MessageReference = mRootReference.child("Message");
    int msgNumber;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.content_messages);
          Spinner spinner = (Spinner) findViewById(R.id.spinner);
          //problem with this if not work
        spinner.setOnItemSelectedListener( this );
        List<String> list = new ArrayList<String>();
        list.add("Come Home");
        list.add("Don't Run");
        list.add("Go To Sleep");
        list.add("Go Study");
        list.add("Dinner Is Ready");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        msgNumber = position;
        Toast.makeText(this, "YOUR SELECTION IS : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot){
        /*if (dataSnapshot.getValue(String.class) != null)
        {
            String key = dataSnapshot.getKey();
            if (key.equals("Message")) {
                  String message = dataSnapshot.getValue(String.class);
                  Message.setText(message);
            }
        }*/
    }

    public void sendMessage(View view)
    {
        MessageReference.setValue(msgNumber);
    }

    @Override
    protected void onStart(){
        super.onStart();
        MessageReference.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
