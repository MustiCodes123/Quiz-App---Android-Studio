package com.example.quizapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

   private TextView tvQuestion, tvTimer , tv_marks , tv_question_index ;
   private RadioGroup rgOptions;
   private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
   private Button btnPrev, btnNext, btnEndExam , btn_save_and_check ;

   private int currentQuestionIndex = 0;
   private int marks = 0;
   private List<Question> questions;
   private CountDownTimer timer;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // Initialize views
      tvQuestion = findViewById(R.id.tv_question);
      rgOptions = findViewById(R.id.rg_options);
      rbOption1 = findViewById(R.id.rb_option1);
      rbOption2 = findViewById(R.id.rb_option2);
      rbOption3 = findViewById(R.id.rb_option3);
      rbOption4 = findViewById(R.id.rb_option4);
      btnNext = findViewById(R.id.btn_next);
      btnPrev = findViewById(R.id.btn_prev);
      btnEndExam = findViewById(R.id.btn_end_exam);
      tvTimer = findViewById(R.id.tv_timer);
      btn_save_and_check = findViewById(R.id.btn_save_and_check);
      tv_marks = findViewById(R.id.tv_marks);
      tv_question_index = findViewById(R.id.tv_question_index);
      // Load questions
      questions = loadQuestions();
      if (questions == null || questions.isEmpty()) return;

      displayQuestion(currentQuestionIndex);

      // Set button listeners
      btnNext.setOnClickListener(v -> nextQuestion());
      btnPrev.setOnClickListener(v -> prevQuestion());
      btnEndExam.setOnClickListener(v -> endExam());
      btn_save_and_check.setOnClickListener(v -> ShowAnswer());

      startTimer(5); // 5-minute timer
   }

   private void ShowAnswer() {

         int selectedId = rgOptions.getCheckedRadioButtonId();

         // Get the selected RadioButton
         RadioButton selectedRadioButton = findViewById(selectedId);


         int correctAnswerIndex = questions.get(currentQuestionIndex).getCorrectAnswerIndex();
         int selectedOptionIndex = rgOptions.indexOfChild(selectedRadioButton);

         // Disable the RadioGroup to prevent further interaction
         for (int i = 0; i < rgOptions.getChildCount(); i++) {
            rgOptions.getChildAt(i).setEnabled(false);
         }

         // Check if the answer is correct
         if (selectedOptionIndex == correctAnswerIndex) {
            selectedRadioButton.setBackgroundColor(getResources().getColor(R.color.green));
           // selectedRadioButton.setBackgroundResource(R.drawable.correct_background);  // add tick image
            // Increase marks
            marks += 5;
         }

         //if no option selected
         else if(selectedRadioButton == null){
            rgOptions.getChildAt(correctAnswerIndex).setBackgroundColor(getResources().getColor(R.color.green));
            // Disable the RadioGroup to prevent further interaction
            for (int i = 0; i < rgOptions.getChildCount(); i++) {
               rgOptions.getChildAt(i).setEnabled(false);
            }
            marks -= 1;

         }
         else
         {
            selectedRadioButton.setBackgroundColor(getResources().getColor(R.color.red));
            //selectedRadioButton.setBackgroundResource(R.drawable.incorrect_background); //add cross image
            // Decrease marks
            marks -= 1;
            // Show the correct answer
            switch (correctAnswerIndex) {
               case 0:
                  if (rbOption1 != null) rbOption1.setBackgroundColor(getResources().getColor(R.color.green));
                  break;
               case 1:
                  if (rbOption2 != null) rbOption2.setBackgroundColor(getResources().getColor(R.color.green));
                  break;
               case 2:
                  if (rbOption3 != null) rbOption3.setBackgroundColor(getResources().getColor(R.color.green));
                  break;
               case 3:
                  if (rbOption4 != null) rbOption4.setBackgroundColor(getResources().getColor(R.color.green));
                  break;
            }
         }

         // Update the score TextView if you have one
         tv_marks.setText("Marks: " + marks + "/125");

   SaveColorAndInteractability();
   }
private void SaveColorAndInteractability(){

   Question currentQuestion = questions.get(currentQuestionIndex);
   int[] colors = new int[4];
   boolean[] interactability = new boolean[4];
   for(int i = 0 ; i< 4 ; i++){

      RadioButton rb = (RadioButton) rgOptions.getChildAt(i);
      colors[i] = ((ColorDrawable) rb.getBackground()).getColor();
      interactability[i] = rb.isEnabled();
   }
   btn_save_and_check.setEnabled(false); // make the save button uninteractable
   currentQuestion.setSaveInteractability(false);
   currentQuestion.setOptionColors(colors);
   currentQuestion.setOptionInteractibility(interactability);
}


   private void saveSelectedOption() {

      // saving the last made changes i.e. last option selected in that question but not saved
      int selectedId = rgOptions.getCheckedRadioButtonId();
      int selectedOptionIndex = -1;

      if (selectedId == R.id.rb_option1) {
         selectedOptionIndex = 0;
      } else if (selectedId == R.id.rb_option2) {
         selectedOptionIndex = 1;
      } else if (selectedId == R.id.rb_option3) {
         selectedOptionIndex = 2;
      } else if (selectedId == R.id.rb_option4) {
         selectedOptionIndex = 3;
      }

      if (selectedOptionIndex >= 0) {
         Question currentQuestion = questions.get(currentQuestionIndex);
         currentQuestion.setSelectedOption(selectedOptionIndex);
      }
   }

   private List<Question> loadQuestions() {
      List<Question> questionList = new ArrayList<>();

      String[] questionArray = getResources().getStringArray(R.array.questions);
      String[] optionsArray = getResources().getStringArray(R.array.options);

      int optionsIndex = 0;

      for (int i = 0; i < questionArray.length; i++) {
         String[] options = new String[4];
         System.arraycopy(optionsArray, optionsIndex, options, 0, 4); // Copy 4 options
         int correctAnswerIndex = Integer.parseInt(optionsArray[optionsIndex + 4]); // Correct answer index
         questionList.add(new Question(questionArray[i], options, correctAnswerIndex - 1));
         optionsIndex += 5; // Move to next question options
      }

      return questionList;
   }

   private void displayQuestion(int index) {
      if (index < 0 || index >= questions.size()) return;


      Question currentQuestion = questions.get(index);

      // update question number
      int questionNumber = index + 1; // Convert index to 1-based index
      tv_question_index.setText(questionNumber + "/" + questions.size());

      tvQuestion.setText(currentQuestion.getQuestionText());
      rbOption1.setText(currentQuestion.getOptions()[0]);
      rbOption2.setText(currentQuestion.getOptions()[1]);
      rbOption3.setText(currentQuestion.getOptions()[2]);
      rbOption4.setText(currentQuestion.getOptions()[3]);

      rgOptions.clearCheck(); // Clear previous selection

      //restore color and interactability

      int[] colors = currentQuestion.getOptionColors();
      boolean[] interactability = currentQuestion.getOptionInteractibility();
      boolean saveInteractability = currentQuestion.getSaveInteractability();
      btn_save_and_check.setEnabled(saveInteractability);
      for (int i = 0; i < 4; i++) {
         RadioButton rb = (RadioButton) rgOptions.getChildAt(i);
         rb.setBackgroundColor(colors[i]);
         rb.setEnabled(interactability[i]);
      }

      int selectedOption = currentQuestion.getSelectedOption();
      if(selectedOption >= 0){
         switch (selectedOption){
            case 0:
               rbOption1.setChecked(true);
               break;
            case 1:
               rbOption2.setChecked(true);
               break;
            case 2:
               rbOption3.setChecked(true);
               break;
            case 3:
               rbOption4.setChecked(true);
               break;
         }
      }

   }


   private void nextQuestion() {
      saveSelectedOption();
      if (currentQuestionIndex < questions.size() - 1) {
         currentQuestionIndex++;
         displayQuestion(currentQuestionIndex);
      }
   }

   private void prevQuestion() {
      saveSelectedOption();
      if (currentQuestionIndex > 0) {
         currentQuestionIndex--;
         displayQuestion(currentQuestionIndex);
      }
   }



   private void startTimer(int minutes) {
      timer = new CountDownTimer(minutes * 60 * 1000, 1000) {
         @Override
         public void onTick(long millisUntilFinished) {
            long minutesLeft = (millisUntilFinished / 1000) / 60;
            long secondsLeft = (millisUntilFinished / 1000) % 60;
            tvTimer.setText(String.format("Time: %02d:%02d", minutesLeft, secondsLeft));
         }

         @Override
         public void onFinish() {
            endExam();
         }
      };
      timer.start();
   }

   private void endExam() {
      Intent intent = new Intent(MainActivity.this, EndingScreenActivity.class);
      intent.putExtra("marks", marks); // Pass the marks to the EndingScreenActivity
      startActivity(intent);
      finish(); // Close the MainActivity
   }
}
