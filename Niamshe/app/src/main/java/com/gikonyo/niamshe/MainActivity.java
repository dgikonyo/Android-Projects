package com.gikonyo.niamshe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int seconds=0;
    private boolean running;
    private boolean wasRunning; //to record whether the stop watch was running before the onStop() method was called

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState !=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");

        }
        runTimer();//we want this method to start when the application begins
    }
    public void onClickStart(View view){
        running=true;
    }
    public void onClickStop(View view){
        running=false;
    }
    public void onClickReset(View view){
        running=false;
        seconds=0;//set the seconds to zero and stop the watch
    }

    private void runTimer(){
        final TextView timeView=(TextView)findViewById(R.id.time_view);
        final Handler handler=new Handler();

        handler.post(new Runnable() {//calling in the post() method passes in a new Runnable that contains the code we ant to execute
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;

                String time=String.format("%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);

                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);//the code in the runnable will be run again after 1 second or 1000 milliseconds
            }
        });



    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    /*
    @Override
    protected void onStop(){//if called, it will stop the stop watch
        super.onStop();
        running=false;
    }

    @Override
    protected  void onStart(){ // to continue running of the stop watch
        super.onStart();
        if (wasRunning){
            running=true;
        }
    }
*/
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }
}
