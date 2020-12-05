package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    DatabaseHelper db;
    EditText fname;
    EditText lname;
    EditText uname;
    EditText pword;
    EditText email;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        db = new DatabaseHelper(Signup.this, null, null, 1);
//        boolean result = deleteDatabase("cookathome.db");
//        Log.d("databaseStatus", "" +result);
        fname = (EditText) findViewById(R.id.txtfname);
        lname = (EditText) findViewById(R.id.txtlname);
        uname = (EditText) findViewById(R.id.txtuname);
        pword = (EditText) findViewById(R.id.txtpword);
        email = (EditText) findViewById(R.id.txtemail);


    }

    public void addNewUser(View v){

        String username = uname.getText().toString();
        String firstname = fname.getText().toString();
        String lastname = lname.getText().toString();
        String emailStr = email.getText().toString();
        String password = pword.getText().toString();


        boolean newUser = db.validateUsername(username);
        if(!newUser){
            Toast.makeText(this, "invalid username", Toast.LENGTH_SHORT).show();
        }

            boolean addNew = db.addUsers(firstname, lastname, username, password, emailStr);
        if(addNew){
            Toast.makeText(this, "successfully registered", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Signup.this, FirstPage.class);
            myIntent.putExtra("username", username);
            startActivity(myIntent);
        }
        else{
            Toast.makeText(this, "unable to register", Toast.LENGTH_LONG).show();
        }

    }
}
