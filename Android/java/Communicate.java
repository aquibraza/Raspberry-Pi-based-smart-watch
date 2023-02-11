package com.example.aquib.smartwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Communicate extends AppCompatActivity implements ValueEventListener {
    private EditText HeadingInput;
    private TextView HeadingText, FontText;
    Button submit;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mHeadingReference = mRootReference.child("heading");
    private DatabaseReference mFontReference = mRootReference.child("fontcolor");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        HeadingText = (TextView)findViewById(R.id.headingText);
        FontText = (TextView)findViewById(R.id.fontText);
        HeadingInput = (EditText)findViewById(R.id.headingInput);
        submit = (Button)findViewById(R.id.submitHeading);

//        submit.setOnClickListener(new Communicate.startSubmit());
    }

    public void submitHeading(View view)
    {
        String heading = HeadingInput.getText().toString();
        mHeadingReference.setValue(heading);
        HeadingInput.setText("");

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot){
        if (dataSnapshot.getValue(String.class) != null)
        {
            String key = dataSnapshot.getKey();
            if (key.equals("heading")) {
                String heading = dataSnapshot.getValue(String.class);
                HeadingText.setText(heading);
            }
            else if (key.equals("fontcolor")) {
                String font = dataSnapshot.getValue(String.class);
                FontText.setText(font);
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mHeadingReference.addValueEventListener(this);
        mFontReference.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

   /* public class startSubmit implements View.OnClickListener{

        public void onClick(View view){
   //         submitHeading(view);
        }
    }*/
}
