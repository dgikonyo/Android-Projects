package com.gikonyo.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //get the intent sent from the main activity
        Intent OrderIntent=getIntent();
        String displayMessage=OrderIntent.getStringExtra(MainActivity.EXTRA_ORDER_KEY);//now we have the message from the MainActivity
        //create a textView variable to connect with textview in the layout
        TextView orderDisplay=findViewById(R.id.display_order);

        orderDisplay.setText(displayMessage);//so the message retrieved from MainActivity will be displayed here


    }

    public void onRadioButtonClicked(View view) {
        //the method will handle checked radio buttons and define what to do with checked radio buttons
        //create a boolean method and use the method checked to determine if a radio button is checked
        boolean checked=((RadioButton)view).isChecked();

        switch(view.getId()){
            case R.id.same_day:
                if(checked){
                    displayToast(getString(R.string.same_day_as_messenger_service));
                }
                break;
            case R.id.next_day:
                if(checked){
                    displayToast(getString(R.string.next_day_ground_delivery));
                }
                break;
            case R.id.pickup:
                if(checked){
                    displayToast(getString(R.string.pickup));
                }
                break;

            default:
                //some other action
                break;
        }


    }
    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
