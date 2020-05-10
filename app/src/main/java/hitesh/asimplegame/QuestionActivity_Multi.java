package hitesh.asimplegame;

/**
 * Created by H on 7/12/2015.
 */


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class QuestionActivity_Multi extends MultiWaiting {
    private static final String TAG = "InGame_Multi";

    private List<Question> questionList;
    public static int score_user1, score_user2 = 0;
    public static String player1, player2;
    private int questionID = 0;
    private Question currentQ;
    private TextView txtQuestion, times, scored_user1, scored_user2, scoreTitle_player1, scoreTitle_player2;
    private Button button1, button2, button3, button4, button5;
    public static int counter = 0;
    public static String hms;
    CounterClass timer = new CounterClass(60000, 1000);
    static boolean active = false;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private final FirebaseFirestore FirestoreDB = FirebaseFirestore.getInstance();
    MultiWaiting multi = new MultiWaiting();
    CreateRoomInfo info = new CreateRoomInfo();
    public String roomName = info.getRoomInfo();
    private String Strscore_user1 = Integer.toString(score_user1);
    private String Strscore_user2 = Integer.toString(score_user2);
    getLevel getlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = true;
        setContentView(R.layout.activity_ingame_multiplay);

        getlevel = new getLevel();

        if(getlevel.getLv() == "Easy") {
            QuizDBOpenHelper db = new QuizDBOpenHelper(this);  // my question bank class
            questionList = db.getAllQuestions();  // this will fetch all quetonall questions
        }else if(getlevel.getLv() == "Medium"){
            QuizDBOpenHelper_Medium db = new QuizDBOpenHelper_Medium(this);  // my question bank class
            questionList = db.getAllQuestions();  // this will fetch all quetonall questions
        }else if(getlevel.getLv() == "Hard"){
            QuizDBOpenHelper_Hard db = new QuizDBOpenHelper_Hard(this);  // my question bank class
            questionList = db.getAllQuestions();  // this will fetch all quetonall questions
        }else{
            QuizDBOpenHelper_Hard db = new QuizDBOpenHelper_Hard(this);  // my question bank class
            questionList = db.getAllQuestions();  // this will fetch all quetonall questions
        }

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
        scored_user1 = (TextView) findViewById(R.id.score_player1);
        scored_user2 = (TextView) findViewById(R.id.score_player2);
        scoreTitle_player1 = (TextView) findViewById(R.id.scoreTitle_player1);
        scoreTitle_player2 = (TextView) findViewById(R.id.scoreTitle_player2);
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

        String currentRoom = info.getRoomInfo();
        DocumentReference docRef = FirestoreDB.collection("MultiPlay").document(currentRoom);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    if(document != null){
                        player1 = document.getString("player1");
                        player2 = document.getString("player2");

                        scoreTitle_player1.setText(player1);
                        scoreTitle_player2.setText(player2);
                    }
                }
            }
        });

    }

    public void getAnswer(String AnswerString) throws InterruptedException {
        if (currentQ.getANSWER().equals(AnswerString)) {
            String currentRoom = info.getRoomInfo();
            DocumentReference docRef = FirestoreDB.collection("MultiPlay").document(currentRoom);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(user.getDisplayName().equals(documentSnapshot.getString("player1"))){
                        score_user1++;
                        scored_user1.setText("" + score_user1);
                        String scoreUser1 = Integer.toString(score_user1);
                        mFirestore.collection("MultiPlay").document(roomName)
                                .update("player1_score", scoreUser1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }

                    if(user.getDisplayName().equals(documentSnapshot.getString("player2"))){
                        score_user2++;
                        scored_user2.setText("" + score_user2);
                        String scoreUser2 = Integer.toString(score_user2);
                        mFirestore.collection("MultiPlay").document(roomName)
                                .update("player2_score", scoreUser2)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                }
            });

        }

        else {

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
            Intent intent = new Intent(QuestionActivity_Multi.this, ResultActivity_Multi.class);
            Bundle b = new Bundle();
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

    protected void onStart() {
        super.onStart();

        DocumentReference docRef = FirestoreDB.collection("MultiPlay").document(roomName);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "Listen Failed.", e);
                    return;
                }

                if(documentSnapshot != null && documentSnapshot.exists()){
                    Log.d(TAG, "Current data : " + documentSnapshot.getData());
                    String userScore1 = documentSnapshot.getString("player1_score");
                    String userScore2 = documentSnapshot.getString("player2_score");
                    scored_user1.setText("" + userScore1);
                    scored_user2.setText("" + userScore2);

                    if (questionID < 20) {
                        // if questions are not over then do this
                        currentQ = questionList.get(questionID);
                        setQuestionView();
                    }

                    else {
                        // if over do this
                        timer.cancel();
                        active = false;

                        String currentRoom = info.getRoomInfo();
                        DocumentReference docRef = FirestoreDB.collection("MultiPlay").document(currentRoom);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                mFirestore.collection("MultiPlay").document(roomName)
                                        .update("status", "End")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        });

                        Intent intent = new Intent(QuestionActivity_Multi.this, ResultActivity_Multi.class);
                        Bundle b = new Bundle();
                        b.putString("time", hms);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }

                    if(Objects.equals(documentSnapshot.getString("status"), "End")){
                        Intent intent = new Intent(QuestionActivity_Multi.this, ResultActivity_Multi.class);
                        startActivity(intent);
                        finish();
                    }


                }

                else{
                    Log.d(TAG, "Current data : null");
                }
            }
        });
    }
}
