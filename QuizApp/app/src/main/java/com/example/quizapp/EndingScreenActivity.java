package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EndingScreenActivity extends AppCompatActivity {

    private TextView tvFinalMarks , tvPercentage;
    private Button btnRetakeQuiz, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ending_screen_activity);

        tvFinalMarks = findViewById(R.id.tv_final_marks);
        tvPercentage = findViewById(R.id.tv_percentage);
        btnRetakeQuiz = findViewById(R.id.btn_retake_quiz);
        btnExit = findViewById(R.id.btn_exit);

        // Get marks from intent
        int marks = getIntent().getIntExtra("marks", 0);
        int totalMarks = 125;

        double percentage = (marks / (double) totalMarks) * 100 ;


        tvFinalMarks.setText("Marks: " + marks + "/" + totalMarks);
        tvPercentage.setText(String.format("Percentage: %.2f%%" , percentage));

        btnRetakeQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(EndingScreenActivity.this, StartingScreenActivity.class);
            startActivity(intent);
            finish(); // Close the EndingScreenActivity
        });

        btnExit.setOnClickListener(v -> {
            finishAffinity(); // Close the app
        });
    }
}
