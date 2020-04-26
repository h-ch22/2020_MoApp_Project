package hitesh.asimplegame;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Ranking extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mMessagesRef;
    private Query mMessagesQuery;
    private ValueEventListener mMessagesListener;
    private ChildEventListener mMessagesQueryListener;
    private EditText rank_1, rank_2, rank_3, rank_4, rank_5, rank_6, rank_7, rank_8, rank_9, rank_10;
    private static final String TAG = "Query : ";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference rankRef;
    Long[] score = new Long[10];
    String[] name = new String[10];
    String[] scoreRank = new String[10];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        toastMessage("랭킹 데이터에 연결하는 중...");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        rank_1 = (EditText) findViewById(R.id.value_mainrank_1st);
        rank_1.setText(scoreRank[0]);
        rank_2 = findViewById(R.id.value_mainrank_2nd);
        rank_2.setText(scoreRank[1]);
        rank_3 = findViewById(R.id.value_mainrank_3rd);
        rank_4 = findViewById(R.id.value_mainrank_4th);
        rank_5 = findViewById(R.id.value_mainrank_5th);
        rank_6 = findViewById(R.id.value_rank_6th);
        rank_7 = findViewById(R.id.value_rank_7th);
        rank_8 = findViewById(R.id.value_rank_8th);
        rank_9 = findViewById(R.id.value_rank_9th);
        rank_10 = findViewById(R.id.value_rank_10th);
    }

    public void Query(){
        CollectionReference rankRef = db.collection("UserScore");
        rankRef.orderBy("score", Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot task) {
                int count = 0;
                    for(QueryDocumentSnapshot doc : task){
                        Log.d(TAG, doc.getId() + "->" + doc.getLong("score"));
                        name[count] = doc.getId();
                        score[count] = doc.getLong("score");
                        scoreRank[count] = score[count].toString();
                        count++;
                    }

                rank_1.setText(name[0] + " : " + scoreRank[0]);
                rank_2.setText(name[1] + " : " + scoreRank[1]);
                rank_3.setText(name[2] + " : " + scoreRank[2]);
                rank_4.setText(name[3] + " : " + scoreRank[3]);
                rank_5.setText(name[4] + " : " + scoreRank[4]);
                rank_6.setText(name[5] + " : " + scoreRank[5]);
                rank_7.setText(name[6] + " : " + scoreRank[6]);
                rank_8.setText(name[7] + " : " + scoreRank[7]);
                rank_9.setText(name[8] + " : " + scoreRank[8]);
                rank_10.setText(name[9] + " : " + scoreRank[9]);
            }
        });
    }

    public void onStart() {
        super.onStart();
        Query();
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
