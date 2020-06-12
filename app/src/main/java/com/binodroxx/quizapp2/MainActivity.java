package com.binodroxx.quizapp2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextQuestion;
    private Button btnTrue;
    private Button btnWrong;
   private int mQuestionIndex;
   private int mQuizQuestion ;
   private ProgressBar mProgressBar;
   private TextView mQuizStatsTextView;
   private final String SCORE_KEY= "SCORE";
   private final String INDEX_KEY= "INDEX";

   private int mUserScore;

    private QuizModel[] questionCollection =new QuizModel[]{
      new QuizModel(R.string.q1,true),
      new QuizModel(R.string.q2,false),
      new QuizModel(R.string.q3,true),
      new QuizModel(R.string.q4,true),
      new QuizModel(R.string.q5,false),
      new QuizModel(R.string.q6,false),
      new QuizModel(R.string.q7,true),
      new QuizModel(R.string.q8,false),
      new QuizModel(R.string.q9, false),
      new QuizModel(R.string.q10,true)
    };

    final int USER_PROGRESS= (int) Math.ceil(100.0/questionCollection.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            mUserScore=savedInstanceState.getInt(SCORE_KEY);
            mQuestionIndex=savedInstanceState.getInt(INDEX_KEY);
        }else{
            mUserScore=0;
            mQuestionIndex=0;

        }

        Toast.makeText(getApplicationContext(),"OnCreate method is called",Toast.LENGTH_LONG).show();
        mTextQuestion=findViewById(R.id.txtQuestion);

        btnTrue =findViewById(R.id.btnTrue);
        mProgressBar=findViewById(R.id.quizPB);
        mQuizStatsTextView =findViewById(R.id.txtQuizStats);
        mQuizStatsTextView.setText(mUserScore+"");

        QuizModel q1 =questionCollection[mQuestionIndex];
        mQuizQuestion =q1.getQuestion();
        mTextQuestion.setText(questionCollection[mQuestionIndex].getQuestion());

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(true);
                      changeQuestionOnButtonClick();

            }
        });
        btnWrong =findViewById(R.id.btnWrong);
        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(false);
               changeQuestionOnButtonClick();


            }
        });


    }
    private void changeQuestionOnButtonClick(){

        mQuestionIndex=(mQuestionIndex+1)%10;
        if(mQuestionIndex==0){

            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("The Quiz is finished");
            quizAlert.setMessage("your score is: "+mUserScore);
            quizAlert.setPositiveButton("Finish The Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }
        mQuizQuestion = questionCollection[mQuestionIndex].getQuestion();
    mTextQuestion.setText(mQuizQuestion);

    mProgressBar.incrementProgressBy(USER_PROGRESS);
    mQuizStatsTextView.setText(mUserScore+"");
    }

    private void evaluateUserAnswer(boolean userGuess){

        boolean currentQuestionAnswer = questionCollection[mQuestionIndex].isAnswer();
        if(currentQuestionAnswer==userGuess)
        {
            Toast.makeText(getApplicationContext(),R.string.correct_toast_message,Toast.LENGTH_SHORT).show();
            mUserScore=mUserScore+1;
        }
        else
        {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast_message,Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(),"OnStart method is called",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(),"OnResume method is called",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText(getApplicationContext(),"OnPause Method is called",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText( this, "OnSTop method is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy method is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

         outState.putInt(SCORE_KEY,mUserScore);
         outState.putInt(INDEX_KEY,mQuestionIndex);
    }
}
