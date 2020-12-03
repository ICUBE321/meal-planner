package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity {
    DatabaseHelper db;
    TextView txtUsername;
    ListView listAllergy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        db = new DatabaseHelper(this, null, null, 1);
        txtUsername = (TextView) findViewById(R.id.txtusername);
        listAllergy = (ListView) findViewById(R.id.allergyList);
        String username = getIntent().getStringExtra("username");
        txtUsername.setText(username);
        ArrayList<String> dbList = new ArrayList<String>();
        Cursor data = db.getAllergy(username);
        if(data.getCount() == 0){
            Toast.makeText(ProfilePage.this, "database is empty", Toast.LENGTH_LONG).show();
        }
        else{
            while(data.moveToNext()){
                dbList.add(data.getString(1));
                ListAdapter l = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dbList);
                listAllergy.setAdapter(l);
            }
        }

    }
}
