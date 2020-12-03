package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    TextView mealView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        mealView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealName");
        mealView.setText(mealName);

    }
}