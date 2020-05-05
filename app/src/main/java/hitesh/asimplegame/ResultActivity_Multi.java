package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ResultActivity_Multi extends QuestionActivity {
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseFirestore FirestoreDB = FirebaseFirestore.getInstance();
    CreateRoomInfo info = new CreateRoomInfo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Send-Result Process";
    Map<String, Long> UserScore = new HashMap<>();
    //	SendtoResult sendresult = new SendtoResult();
    public static String user1, user2, score_player1, score_player2;
    public static int score_user1, score_user2, sectime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_multiplay);

        TextView VictoryPoint = (TextView) findViewById(R.id.VictoryPoint);
        TextView LosePoint = (TextView) findViewById(R.id.LosePoint);
        TextView title_TimeMulti = (TextView) findViewById(R.id.title_TimeMulti);
        TextView victoryuserName = findViewById(R.id.victoryuserName);
        TextView loserName = findViewById(R.id.loserName);

//        Bundle b = getIntent().getExtras();
        String time = QuestionActivity_Multi.hms;
        String convertTime = time;
        String[] units = time.split(":");
        int minutes = Integer.parseInt(units[1]);
        int seconds = Integer.parseInt(units[2]);
        sectime = 60 * minutes + seconds;

        getScore();

        if(time == null){
            title_TimeMulti.setText("00:00:00");
        }

        else{
            title_TimeMulti.setText("" + time);
        }

        if(score_user1 >= score_user2){
            VictoryPoint.setText("" + score_user1 + "점");
            LosePoint.setText("" + score_user2 + "점");
            victoryuserName.setText("" + user1);
            loserName.setText("" + user2);
        }

        else{
            VictoryPoint.setText("" + score_user2 + "점");
            LosePoint.setText("" + score_user1 + "점");
            victoryuserName.setText("" + user2);
            loserName.setText("" + user1);
        }
    }

    private void getScore(){
        String currentRoom = info.getRoomInfo();
        DocumentReference docRef = FirestoreDB.collection("MultiPlay").document(currentRoom);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    if(document != null){
                        user1 = document.getString("player1");
                        user2 = document.getString("player2");

                        score_player1 = document.getString("player1_score");
                        score_player2 = document.getString("player2_score");

                        if(score_player1 != null && score_player2 != null){
                            score_user1 = Integer.parseInt(score_player1) + sectime;
                            score_user2 = Integer.parseInt(score_player2) + sectime;
                        }

                        if(score_player1 == null){
                            score_user1 = sectime;
                            score_user2 = Integer.parseInt(score_player2) + sectime;
                        }

                        if(score_player2 == null){
                            score_user2 = sectime;
                            score_user1 = Integer.parseInt(score_player1) + sectime;
                        }

                        if(score_player1 == null && score_player2 == null){
                            score_user1 = sectime;
                            score_user2 = sectime;
                        }
                    }
                }
            }
        });
    }

    public void playagain(View o) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}