package com.example.aquib.smartwatch;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Buzzer implements ValueEventListener {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
  //  private DatabaseReference BuzzReference = mRootReference.child("Buzzer");


    @Override
    public void onDataChange(DataSnapshot dataSnapshot){
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

}
