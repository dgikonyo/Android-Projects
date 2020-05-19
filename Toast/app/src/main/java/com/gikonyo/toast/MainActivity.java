package com.gikonyo.toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private TextView textViewYangu;
    private int ongezaCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewYangu=findViewById(R.id.show_count);//connecting the method to the view

    }

    public void showToast(View view) {
        Toast toastYangu=Toast.makeText(this,R.string.toast_message,Toast.LENGTH_SHORT);
        toastYangu.show();
;    }

    public void countUp(View view) {
        ongezaCount++;
        if(textViewYangu!=null){
            textViewYangu.setText(Integer.toString(ongezaCount));
        }
    }
}
