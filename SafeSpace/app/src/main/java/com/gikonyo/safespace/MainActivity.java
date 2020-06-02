package com.gikonyo.safespace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//here we use this activity to show our data from the db

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter theAdapter;

    final String[] from= new String[] {DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC
    };

    final int [] to=new int[]{R.id.id, R.id.title,R.id.desc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        //now we instantiate the objects we have created
        dbManager=new DBManager(this);//here we are passing the context of the dbManager
        dbManager.open();
        Cursor cursor=dbManager.fetch();

        listView=findViewById(R.id.space_list_view);
        listView.setEmptyView(findViewById(R.id.empty));


        theAdapter=new SimpleCursorAdapter(this,R.layout.activity_view_record_, cursor,
                from, to, 0  );

        theAdapter.notifyDataSetChanged();
        listView.setAdapter(theAdapter);

        //adding onClickListener for list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewid) {
                TextView idTextView=view.findViewById(R.id.id);
                TextView titleTextView=view.findViewById(R.id.title);
                TextView descriptionTextView=view.findViewById(R.id.desc);

                String id=idTextView.getText().toString();
                String title=titleTextView.getText().toString();
                String desc=descriptionTextView.getText().toString();

                Intent modify_intent=new Intent(getApplicationContext(),ModifyMainActivity.class);
                modify_intent.putExtra("title",title);
                modify_intent.putExtra("desc",desc);
                modify_intent.putExtra("id",id);

                startActivity(modify_intent);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//will display all menu items when user clicks
        int id=item.getItemId();
        if(id==R.id.add_record){
            Intent add_menu=new Intent(this,AddStuffActivity.class);
            startActivity(add_menu);
        }
        return super.onOptionsItemSelected(item);
    }
}