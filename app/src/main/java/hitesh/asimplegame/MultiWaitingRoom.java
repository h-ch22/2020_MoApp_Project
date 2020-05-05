package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MultiWaitingRoom extends MultiWaiting {
    public static CreateRoomInfo create = new CreateRoomInfo();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    TextView user1, user2;
    Button start;
    String userName1, userName2, level;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String TAG = "Check Data";
    DatabaseReference dataRef;
    public static ListenerRegistration listenerReg;
    public static String roomName = create.getRoomInfo();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiwaiting);

        user1 = findViewById(R.id.nickname_user1);
        user2 = findViewById(R.id.nickname_user2);
        start = findViewById(R.id.multi_startGame);
        DocumentReference docRef = db.collection("MultiPlay").document(roomName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document != null){
                        user1.setText(document.getString("player1"));
                        user2.setText(document.getString("player2"));
                    }
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user1.getText().toString().equals("") || user2.getText().toString().equals("")){
                    toastMessage("모든 유저가 입장한 후 게임시작이 가능합니다.");
                }

                else{
                    String status = "Start";
                    mFirestore.collection("MultiPlay").document(roomName)
                            .update("status", status)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(MultiWaitingRoom.this, QuestionActivity_Multi.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        final DocumentReference docRef = db.collection("MultiPlay").document(roomName);
        listenerReg = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "Listen Failed.", e);
                    return;
                }

                if(documentSnapshot != null && documentSnapshot.exists()){
                    Log.d(TAG, "Current data : " + documentSnapshot.getData());
                    user1.setText(documentSnapshot.getString("player1"));
                    user2.setText(documentSnapshot.getString("player2"));

                    if(Objects.equals(documentSnapshot.getString("status"), "Start")){
                        Intent intent = new Intent(MultiWaitingRoom.this, QuestionActivity_Multi.class);
                        startActivity(intent);
                        MultiWaitingRoom.this.finish();
                    }
                }

                else{
                    Log.d(TAG, "Current data : null");
                }
            }
        });

    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();

        if(listenerReg != null){
            listenerReg.remove();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
