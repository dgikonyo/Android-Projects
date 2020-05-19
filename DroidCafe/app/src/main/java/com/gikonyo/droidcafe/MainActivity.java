package com.gikonyo.droidcafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String mOrderMessage;
    public static final String EXTRA_ORDER_KEY="MY KEY FOR ORDER MESSAGE";
    private String url="https://www.cjs.co.ke/mcjs/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implement an explicit intent for opening the order activity
                Intent myIntent=new Intent(MainActivity.this,OrderActivity.class);//so we are sending this intent from the mainactivity to the order activity
                myIntent.putExtra(EXTRA_ORDER_KEY,mOrderMessage);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//inflates action menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/


        //create a switch case block to handle the clicks on the items in the Menu

        switch(item.getItemId()){
            case R.id.action_order:
                Intent myIntent=new Intent(MainActivity.this,OrderActivity.class);//so we are sending this intent from the mainactivity to the order activity
                myIntent.putExtra(EXTRA_ORDER_KEY,mOrderMessage);
                startActivity(myIntent);
                return true;

            case R.id.action_call:
                //implement an implicit intent that calls your cafe number
                Uri myNumber=Uri.parse("tel:0703241750");
                Intent theIntent=new Intent(Intent.ACTION_DIAL,myNumber);
                startActivity(theIntent);
                return true;

            case R.id.action_about_us:
                Uri theWebsite=Uri.parse(url);
                Intent webIntent=new Intent(Intent.ACTION_VIEW,theWebsite);
                startActivity(webIntent);
                return true;
            case R.id.action_location:
                Uri theLocation=Uri.parse("google.streetview:cbll=-1.283147,36.818416");
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,theLocation);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
    //method for displaying toast messages

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();//will get context of what user has pressed
    }

    public void showDonutMessage(View view) {
        //displayToast(getString(R.string.donut_order));
        mOrderMessage=getString(R.string.donut_order);
        displayToast(mOrderMessage);
    }

    public void showIceCreamOrderMessage(View view) {
        //displayToast(getString(R.string.iceCream_order));
        //defines what happens when user clicks the image for Ice Cream, so it will send us a message

        mOrderMessage=getString(R.string.iceCream_order);
        displayToast(mOrderMessage);
    }

    public void showFroyoOrderMessage(View view) {
       // displayToast(getString(R.string.froyo_order));
        mOrderMessage=getString(R.string.froyo_order);
        displayToast(mOrderMessage);
    }

}
