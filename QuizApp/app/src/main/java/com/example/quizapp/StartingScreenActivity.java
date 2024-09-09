package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class StartingScreenActivity extends AppCompatActivity {

    private Button btnStartQuiz , btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen_activity);

        btnStartQuiz = findViewById(R.id.btn_start_quiz);
        btnQuit = findViewById(R.id.btn_quit);

        btnQuit.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
        btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(StartingScreenActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
