package com.example.caloriediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DB;

public class Registr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        Button button2 = findViewById(R.id.button2);
        EditText login = findViewById(R.id.Login);
        EditText pass = findViewById(R.id.Password);
        EditText email = findViewById(R.id.EmailAddress);
        EditText phone = findViewById(R.id.Phone);
        DB db = new DB(this);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black)); //изменение цвета statusbar

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username = login.getText().toString();
                    String password = pass.getText().toString();
                    String emailad = email.getText().toString();
                    String phonenum = phone.getText().toString();

                    if (username.isEmpty()) {
                        Toast.makeText(Registr.this, "Пожалуйста, введите логин", Toast.LENGTH_SHORT).show();
                        login.requestFocus();
                        return;
                    }

                    if (password.isEmpty()) {
                        Toast.makeText(Registr.this, "Пожалуйста, введите пароль", Toast.LENGTH_SHORT).show();
                        pass.requestFocus();
                        return;
                    }

                    if (emailad.isEmpty()) {
                        Toast.makeText(Registr.this, "Пожалуйста, введите email", Toast.LENGTH_SHORT).show();
                        email.requestFocus();
                        return;
                    }

                    if (phonenum.length() > 11) {
                        Toast.makeText(Registr.this, "Номер телефона должен содержать 11 символов", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean isInserted = db.insertData(username, password, emailad, phonenum);
                    if (isInserted) {
                        Toast.makeText(Registr.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registr.this, MainScreen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Registr.this, "Неуспешная регистрация", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Registr.this, "Произошла ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}