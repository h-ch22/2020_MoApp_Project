package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Home extends Activity {
    private Button start;
    private Button more;
    private ImageButton settings;
    private ImageButton mypage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference rankRef;
    Long[] score = new Long[10];
    String[] name = new String[10];
    String[] scoreRank = new String[10];
    private static final String TAG = "Query : ";
    TextView rank_1, rank_2, rank_3, rank_4, rank_5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toastMessage("랭킹 데이터에 연결하는 중...");
        start = findViewById(R.id.startGame);
        more = findViewById(R.id.mainRanking_more);
//        settings = findViewById(R.id.btn_settings);
        mypage = findViewById(R.id.btn_mypage);

        rank_1 = findViewById(R.id.value_mainrank_1st);
        rank_2 = findViewById(R.id.value_mainrank_2nd);
        rank_3 = findViewById(R.id.value_mainrank_3rd);
        rank_4 = findViewById(R.id.value_mainrank_4th);
        rank_5 = findViewById(R.id.value_mainrank_5th);


        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, setLevel.class);
                startActivity(intent);
                finish();
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Mypage.class);
                startActivity(intent);
                finish();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Ranking.class);
                startActivity(intent);
                finish();
            }
        });

        CollectionReference rankRef = db.collection("UserScore");
        rankRef.orderBy("highscore", com.google.firebase.firestore.Query.Direction.DESCENDING).limit(10).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot task) {
                int count = 0;
                for(QueryDocumentSnapshot doc : task){
                    Log.d(TAG, doc.getId() + "->" + doc.getLong("highscore"));
                    name[count] = doc.getId();
                    score[count] = doc.getLong("highscore");
                    scoreRank[count] = score[count].toString();
                    count++;
                }

                rank_1.setText(name[0] + " : " + scoreRank[0]);
                rank_2.setText(name[1] + " : " + scoreRank[1]);
                rank_3.setText(name[2] + " : " + scoreRank[2]);
                rank_4.setText(name[3] + " : " + scoreRank[3]);
                rank_5.setText(name[4] + " : " + scoreRank[4]);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
