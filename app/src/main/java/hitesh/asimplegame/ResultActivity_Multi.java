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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_multiplay);

        TextView VictoryPoint = (TextView) findViewById(R.id.VictoryPoint);
        TextView LosePoint = (TextView) findViewById(R.id.LosePoint);
        TextView title_TimeMulti = (TextView) findViewById(R.id.title_TimeMulti);
        TextView victoryuserName = findViewById(R.id.victoryuserName);
        TextView loserName = findViewById(R.id.loserName);

        Bundle b = getIntent().getExtras();
        String time = b.getString("time");
        String user1 = b.getString("player1");
        String user2 = b.getString("player2");


        if(time == null){
            title_TimeMulti.setText("00:00:00");
        }

        else{
            title_TimeMulti.setText("" + time);
        }

        String convertTime = time;
        String[] units = time.split(":");
        int minutes = Integer.parseInt(units[1]);
        int seconds = Integer.parseInt(units[2]);
        int sectime = 60 * minutes + seconds;

        int score_user1 = b.getInt("playerscore1") + sectime;
        int score_user2 = b.getInt("playerscore2") + sectime;

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

////		sendresult.setScore(score);
////		sendresult.setLevel("null");
//        Long sendScore = Long.valueOf(score);
//        UserScore.put("score", sendScore);
//
//        db.collection("UserScore").document(name).set(UserScore, SetOptions.merge())
//                .addOnSuccessListener(new OnSuccessListener<Void>(){
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "Send User Data to Server was Successfully completed");
//                        toastMessage("사용자 데이터를 서버에 정상적으로 저장하였습니다.");
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "Send User Data to Server was not completed.");
//                        toastMessage("사용자 데이터를 서버로 전송하지 못하였습니다.");
//                    }
//                });

    }

    public void playagain(View o) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}