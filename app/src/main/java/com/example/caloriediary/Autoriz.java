package com.example.caloriediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import Database.DB;

public class Autoriz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoriz);

        Button button2 = findViewById(R.id.button2);
        TextView textView = findViewById(R.id.textView);
        EditText login = findViewById(R.id.Login);
        EditText pass = findViewById(R.id.Password);
        DB myDB = new DB(this);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black)); //изменение цвета statusbar

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username = login.getText().toString();
                    String password = pass.getText().toString();

                    if (myDB.checkUser(username, password)) {
                        Intent intent = new Intent(Autoriz.this, MainScreen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Autoriz.this, "У Вас нет аккаунта", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Autoriz.this, "Произошла ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Autoriz.this, Registr.class);
                startActivity(intent);
            }
        });

    }
}