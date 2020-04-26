package hitesh.asimplegame;

/**
 * Created by H on 7/12/2015.
 */


import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class QuestionActivity extends Activity {
    private static final String TAG = QuestionActivity.class.getSimpleName();

    private List<Question> questionList;
    private int score = 0;
    private int questionID = 0;
    private Question currentQ;
    private TextView txtQuestion, times, scored;
    private Button button1, button2, button3, button4, button5;
    public static int counter = 0;
    public String hms;
    CounterClass timer = new CounterClass(60000, 1000);
    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = true;
        setContentView(R.layout.activity_main);

        QuizDBOpenHelper db = new QuizDBOpenHelper(this);  // my question bank class
        questionList = db.getAllQuestions();  // this will fetch all quetonall questions
        currentQ = questionList.get(questionID); // the current question

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        // the textview in which the question will be displayed

        // the three buttons,
        // the idea is to set the text of three buttons with the options from question bank
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);

        // the textview in which score will be displayed
        scored = (TextView) findViewById(R.id.score);
        // the timer
        times = (TextView) findViewById(R.id.timers);

        // method which will set the things up for our game
        setQuestionView();
        times.setText("02:00:00");

        // A timer of 60 seconds to play for, with an interval of 1 second (1000 milliseconds)
        timer.start();

        // button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // passing the button text to other method
                // to check whether the anser is correct or not
                // same for all three buttons
                try {
                    getAnswer(button1.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAnswer(button2.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAnswer(button3.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAnswer(button4.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAnswer(button5.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAnswer(String AnswerString) throws InterruptedException {
        if (currentQ.getANSWER().equals(AnswerString)) {
            score++;
            scored.setText("" + score);
        }

        else {

        }

        if (questionID < 20) {
            // if questions are not over then do this
            currentQ = questionList.get(questionID);
            setQuestionView();
        } else {
            // if over do this
            timer.cancel();
            active = false;
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            b.putString("time", hms);
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }
    }

    public class CounterClass extends CountDownTimer
    {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            times.setText("Time is up");
            times.setTextColor(Color.RED);
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            b.putString("time", hms);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;

            hms = String.format( "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));

            Log.d(TAG, "current time: " + hms);
            times.setText(hms);

            if(millis <= 10000){
                times.setTextColor(Color.RED);
            }

            if(active = false){
                timer.cancel();
            }
        }
    }

    private void setQuestionView() {
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button1.setBackgroundResource(R.drawable.btn_rounded);
        button2.setText(currentQ.getOPTB());
        button2.setBackgroundResource(R.drawable.btn_rounded);
        button3.setText(currentQ.getOPTC());
        button3.setBackgroundResource(R.drawable.btn_rounded);
        button4.setText(currentQ.getOPTD());
        button4.setBackgroundResource(R.drawable.btn_rounded);
        button5.setText(currentQ.getOPTE());
        button5.setBackgroundResource(R.drawable.btn_rounded);

        questionID++;
    }


}
