package com.gikonyo.safespace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyMainActivity extends Activity implements View.OnClickListener {
//this activity will update data inserted in our application


    //now we declare our widgets
        private EditText theTitleText;
        private Button updateButton, deleteButton;
        private EditText descText;

        private long _id;
        private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify Record");
        setContentView(R.layout.activity_modify_main);


        dbManager=new DBManager(this);
        dbManager.open();

        theTitleText=findViewById(R.id.subject_edittext);
        descText=findViewById(R.id.description_edittext);

        updateButton=findViewById(R.id.btn_update);
        deleteButton=findViewById(R.id.btn_delete);

        Intent intent=getIntent();// will pass the exact id and title
        String id=intent.getStringExtra("id");
        String name=intent.getStringExtra("title");
        String desc=intent.getStringExtra("desc");

        _id=Long.parseLong(id);

        theTitleText.setText(name);
        descText.setText(desc);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:
                String title=theTitleText.getText().toString();
                //so here when the update button is clicked, the program will go to the String title and get the updated data from title
                String desc=descText.getText().toString();
                dbManager.update(_id,title,desc);
                this.returnToHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnToHome();
                break;

        }
    }

    public void returnToHome(){
        Intent home_intent=new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}