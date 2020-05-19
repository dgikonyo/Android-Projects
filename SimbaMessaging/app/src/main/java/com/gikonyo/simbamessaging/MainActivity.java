package com.gikonyo.simbamessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //create a 'KEY' for the INTENT EXTRA
    public static final String EXTRA_MESSAGE_KEY="My Key";

    //create an EditText object
    private EditText messageEditText;
    //so now we need connect the messageEditText with the id for EditText in the view

    //creating LOG_TAG
    public static final String LOG_TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect messageEditText and connect to editText in layout
        messageEditText=findViewById(R.id.editTextMessage);//we created a variable and connected it
    }

    public void launchSecondActivity(View view) {
        //create an intent to start the second activity
        Intent myIntent=new Intent(this,SecondActivity.class); //'this' refers to current activity.....we are saying that we want to move from this Activity to the SecondActivity
        //start the second activity using the method start activity()
        Log.d(LOG_TAG, "Button clicked");

        //get the message from the editText as a string
        String myMessage=messageEditText.getText().toString();
        //lets put the message in the intent using the putExtra() method
        myIntent.putExtra(EXTRA_MESSAGE_KEY,myMessage);

        startActivity(myIntent);
    }
}
