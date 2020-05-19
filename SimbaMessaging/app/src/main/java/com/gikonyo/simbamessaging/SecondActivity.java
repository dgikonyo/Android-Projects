package com.gikonyo.simbamessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //connect textView variable with the text view in the layout
        TextView messageTextView=findViewById(R.id.textView_message);
        //retrieve the intent from main activity
        Intent myIntent=getIntent();

        //get the message as a string using the KEY as send from the MainActivity
        String myMessage=myIntent.getStringExtra(MainActivity.EXTRA_MESSAGE_KEY);

        //display the message in the textView in the UI using the method setText()
        messageTextView.setText(myMessage);
    }
}
