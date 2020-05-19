package com.gikonyo.userinteraction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

//1.implement an interface(View) for handling calendar click event..(View.onClickListener) to allow us to handle onclick events on our calendar picker dialog
//7. implement interface(AdapterView.onItemSelectedListener)-we want to know the item selected in the spinner.
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //2.declare and EditText variable that is the focus of calendar dialog
    private EditText birthday;

    //5.declare the variables to hold the selected date
    private int mYear;
    private int mMonth;
    private int mDay;

    //declare a variable for holding the item selected on the spinner
    private String mSpinnerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //3.connect this edit text variable with the one specified in the layout for receiving the date value
        birthday=findViewById(R.id.birthday);

        //4.connect the edit text variable with an OnClickListener
        birthday.setOnClickListener(this); //this refers to the context which is the main activity

        //7.1 declare a spinner variable and connect it with the spinner view in the layout
        Spinner phoneSpinner=findViewById(R.id.phone_spinner);

        //7.2 set an onItemSelectedListener on the spinner object/a=variable created
        if(phoneSpinner!=null){
            phoneSpinner.setOnItemSelectedListener(this);
        }
        //7.3 create an array adapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.spinner_label,android.R.layout.simple_spinner_item);

        //7.4 specify the layout for the drop down menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //7.5 now we attach the spinner to the adapter
        if(phoneSpinner !=null){
            phoneSpinner.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View v) {//so we are going to implement the events on this method
        //6.0this method allows us to get the instance of the current date
        //6.1ensure the focus is on this edittext

        if(v==birthday){
            //get current instance of calendar
            //6.2declare a calendar to get the current selected date
            final Calendar theDate=Calendar.getInstance();
            mYear=theDate.get(Calendar.YEAR);
            mMonth=theDate.get(Calendar.MONTH);
            mDay=theDate.get(Calendar.DAY_OF_MONTH);

            //6.3 Declare a date picker dialog to pick selected date
            DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //6.4 set the date on the edit text variable
                    birthday.setText(dayOfMonth+"-"+(month+1+"-"+year));

                }
            },mYear,mMonth,mDay);
                //6.5show the date picker dialog
            datePickerDialog.show();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        //now we want to know what item has been selected from spinner
        //declare a variable for holding the item selected on the spinner
        mSpinnerLabel = adapterView.getItemAtPosition(position).toString();
        Toast myToast = Toast.makeText(this, "Selected phone as : "+mSpinnerLabel, Toast.LENGTH_SHORT);
        myToast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast toast = Toast.makeText(this, "Nothing was selected", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showToast(View view) {
        Toast check = Toast.makeText(this, "Please Accept Terms & Conditions", Toast.LENGTH_SHORT);
        check.show();
    }

    public void createAccount(View view) {
        //Compare passwords
        //Throw error exceptions
        //Get data entered on edittext and save it in a database
        //Add an intent and open main activity
        //Throw a toast
        Toast submit = Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT);
        submit.show();
    }
}
