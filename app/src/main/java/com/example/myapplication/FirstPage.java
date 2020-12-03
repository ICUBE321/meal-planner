package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FirstPage extends AppCompatActivity {
    Button al1, al2, al3, al4, al5, al6, al7, al8, al9, al10, al11;
    ArrayList<String> allergyList = new ArrayList<>();
    String username;
    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        db = new DatabaseHelper(FirstPage.this, null, null, 1);
        al1 = (Button) findViewById(R.id.al1);
        al2 = (Button) findViewById(R.id.al2);
        al3 = (Button) findViewById(R.id.al3);
        al4 = (Button) findViewById(R.id.al4);
        al5 = (Button) findViewById(R.id.al5);
        al6 = (Button) findViewById(R.id.al6);
        al7 = (Button) findViewById(R.id.al7);
        al8 = (Button) findViewById(R.id.al8);
        al9 = (Button) findViewById(R.id.al9);
        al10 = (Button) findViewById(R.id.al10);
        al11 = (Button) findViewById(R.id.al11);

        al1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al1.getText().toString();
                allergyList.add(btnText);
            }
        });

        al2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al2.getText().toString();
                allergyList.add(btnText);
            }
        });

        al3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al3.getText().toString();
                allergyList.add(btnText);
            }
        });

        al4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al4.getText().toString();
                allergyList.add(btnText);
            }
        });

        al5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al5.getText().toString();
                allergyList.add(btnText);
            }
        });

        al6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al6.getText().toString();
                allergyList.add(btnText);
            }
        });

        al7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al7.getText().toString();
                allergyList.add(btnText);
            }
        });

        al8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al8.getText().toString();
                allergyList.add(btnText);
            }
        });

        al9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al9.getText().toString();
                allergyList.add(btnText);
            }
        });

        al10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al10.getText().toString();
                allergyList.add(btnText);
            }
        });

        al11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = al11.getText().toString();
                allergyList.add(btnText);
            }
        });
    }


    public void btnAnimate(View v){



    }
    public void addbtnAllergies(View v){
        username = getIntent().getStringExtra("username");
        boolean addAllergy = db.addAllergies(username, allergyList);
        if(addAllergy){
            Toast.makeText(FirstPage.this, "allergies added", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(FirstPage.this, Meals.class);
            startActivity(myIntent);
        }
        else{
            Toast.makeText(FirstPage.this, "unable to add allergies", Toast.LENGTH_LONG).show();
        }

    }
}
