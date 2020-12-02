package com.example.cook_at_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Signin extends AppCompatActivity {
    EditText uname;
    EditText pword;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);
        uname = (EditText) findViewById(R.id.textusername);
        pword = (EditText)findViewById(R.id.textpassword);
    }

    public void usersignin(View v){
        String username = uname.getText().toString();
        String password = pword.getText().toString();

        boolean check = db.checkUsers(username, password);
        if(check){
            Toast.makeText(this, "you have been signed in", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Signin.this, HomePage.class);
            myIntent.putExtra("username", username);
            startActivity(myIntent);
        }else{
            Toast.makeText(this, "incorrect username or password", Toast.LENGTH_LONG).show();
        }
    }


}
