package com.example.caloriediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mintay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mintay);

        Button button9 = findViewById(R.id.button9);

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mintay.this, MainScreen.class);
                startActivity(intent);
            }
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.black)); //изменение цвета statusbar

    }
}