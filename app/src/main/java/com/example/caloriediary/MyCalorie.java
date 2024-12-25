package com.example.caloriediary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Database.DB;

public class MyCalorie extends AppCompatActivity {
    DB db;
    private int userId;
    private String selectedDate;
    private int dailyCalorieGoal = 0; // Цель по калориям
    private int totalCalories = 0; // Общие калории

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calorie);

        Button button4 = findViewById(R.id.button4);
        db = new DB(this); // Инициализация базы данных
        CalendarView calendarView = findViewById(R.id.calendarView);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        EditText editTextCalorieGoal = findViewById(R.id.editTextCalorieGoal); // Поле для ввода цели по калориям
        TextView textViewCalorieCounter = findViewById(R.id.textViewCalorieCounter); // Поле для отображения счетчика калорий

        getWindow().setStatusBarColor(getResources().getColor(R.color.black)); // Изменение цвета statusbar

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Форматирование даты
        });

        fab.setOnClickListener(view -> showInputDialog());

        button4.setOnClickListener(v -> {
            // Получение цели по калориям
            String goal = editTextCalorieGoal.getText().toString();
            if (!goal.isEmpty()) {
                dailyCalorieGoal = Integer.parseInt(goal);
                Toast.makeText(MyCalorie.this, "Цель по калориям установлена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите данные о еде");

        // Создание макета для ввода данных
        View view = getLayoutInflater().inflate(R.layout.dialog_input, null);
        EditText editTextFoodName = view.findViewById(R.id.editTextFoodName);
        EditText editTextGrams = view.findViewById(R.id.editTextGrams);
        EditText editTextCalories = view.findViewById(R.id.editTextCalories);

        builder.setView(view);

        // Кнопка "Сохранить"
        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String foodName = editTextFoodName.getText().toString();
            String grams = editTextGrams.getText().toString();
            String calories = editTextCalories.getText().toString();

            // Вставка данных в базу данных
            int calorieValue = Integer.parseInt(calories);
            db.insertFoodData(userId, selectedDate, foodName, Integer.parseInt(grams), calorieValue);
            totalCalories += calorieValue; // Обновление общего количества калорий

            // Обновление счетчика калорий
            updateCalorieCounter();
        });

        // Кнопка "Отмена"
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        // Показать диалоговое окно
        builder.show();
    }

    private void updateCalorieCounter() {
        TextView textViewCalorieCounter = findViewById(R.id.textViewCalorieCounter);
        textViewCalorieCounter.setText("Текущие калории: " + totalCalories);

        // Проверка на превышение цели
        if (totalCalories > dailyCalorieGoal) {
            Toast.makeText(this, "Вы превысили цель по калориям!", Toast.LENGTH_SHORT).show();
        } else {
            int remainingCalories = dailyCalorieGoal - totalCalories;
            Toast.makeText(this, "Осталось набрать: " + remainingCalories + " калорий", Toast.LENGTH_SHORT).show();
        }
    }
}
