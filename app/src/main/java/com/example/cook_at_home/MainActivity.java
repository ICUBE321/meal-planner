package com.example.cook_at_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void viewSignup(View v){
        Intent signupIntent = new Intent(MainActivity.this, Signup.class);
        startActivity(signupIntent);
    }

    public void viewSignin(View v){
        Intent signinIntent = new Intent(MainActivity.this, Signin.class);
        startActivity(signinIntent);
    }
}