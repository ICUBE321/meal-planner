package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.homeImage);
        String path = "https://www.shutterturf.com/blog/wp-content/uploads/2019/04/Depositphotos_102587674_xl-2015.jpg";
        Picasso.get().load(path).into(imageView);
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