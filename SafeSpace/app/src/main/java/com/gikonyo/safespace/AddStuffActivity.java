package com.gikonyo.safespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddStuffActivity extends Activity implements View.OnClickListener {


    //here we declare our widgets
    private Button addToDoBtn;
    private EditText descEditText;
    private EditText subjectEditText;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Something");
        setContentView(R.layout.activity_add_stuff);


        //now we instantiate our widgets
        subjectEditText=findViewById(R.id.subject_edittext);
        descEditText=findViewById(R.id.description_edittext);
        addToDoBtn=findViewById(R.id.add_record);

        dbManager=new DBManager(this);
        dbManager.open();
        addToDoBtn.setOnClickListener(this);//we are setting a listener to this button

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_record:
                final String name=subjectEditText.getText().toString();//we are getting the string values from edit text and saving to a string
                final String desc=descEditText.getText().toString();

                dbManager.insert(name,desc);//so now we insert into the database

                Intent main =new Intent(AddStuffActivity.this, CountryListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //so here, after the data is saved,we will be redircted to the listactivity that contains all the records

                startActivity(main);
                break;

        }
    }
}