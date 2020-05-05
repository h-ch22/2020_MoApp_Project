package hitesh.asimplegame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class MultiWaiting extends BaseActivity {
    private static final String TAG = "MultiRoom";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    getLevel getLevel;
    String[] Roomlist = new String[10];
    Button MultiRoom1, createRoom, MultiRoom2, MultiRoom3, MultiRoom4;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    CreateRoomInfo create = new CreateRoomInfo();
    public static String selRoom;
    int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiroomlist);
        getLevel = new getLevel();
        MultiRoom1 = (Button) findViewById(R.id.multiRoom1);
        MultiRoom2 = (Button) findViewById(R.id.multiRoom2);
        MultiRoom3 = (Button) findViewById(R.id.multiRoom3);
        MultiRoom4 = (Button) findViewById(R.id.multiRoom4);

        createRoom = findViewById(R.id.createRoombtn);

        String level = getLevel.getLv();

        db.collection("MultiPlay").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Roomlist[count] = doc.getId();
                        count++;
                        MultiRoom1.setText(Roomlist[0]);
                        MultiRoom2.setText(Roomlist[1]);
                        MultiRoom3.setText(Roomlist[2]);
                        MultiRoom4.setText(Roomlist[3]);
                    }
                }

                else{
                    Log.d(TAG, "get Failed : " , task.getException());
                }
            }
        });

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiWaiting.this, createRoomactivity.class);
                startActivity(intent);
                finish();
            }
        });

        MultiRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                DocumentReference docRef = mFirestore.collection("MultiPlay").document(MultiRoom1.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        if(document.exists()){
                            if(document.get("player1") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom1.getText().toString())
                                        .update("player1", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer1(user.getDisplayName());
                                                selRoom = MultiRoom1.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            if(document.get("player2") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom1.getText().toString())
                                        .update("player2", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer2(user.getDisplayName());
                                                selRoom = MultiRoom1.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            else{
                                toastMessage("모든 플레이어가 입장한 상태입니다. 다른 방을 선택해주세요.");
                                hideProgressDialog();
                            }
                        }
                    }
                });
            }
        });

        MultiRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                DocumentReference docRef = mFirestore.collection("MultiPlay").document(MultiRoom2.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        if(document.exists()){
                            if(document.get("player1") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom2.getText().toString())
                                        .update("player1", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer1(user.getDisplayName());
                                                selRoom = MultiRoom2.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            if(document.get("player2") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom2.getText().toString())
                                        .update("player2", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer2(user.getDisplayName());
                                                selRoom = MultiRoom2.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            else{
                                toastMessage("모든 플레이어가 입장한 상태입니다. 다른 방을 선택해주세요.");
                                hideProgressDialog();
                            }
                        }
                    }
                });

            }
        });

        MultiRoom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                DocumentReference docRef = mFirestore.collection("MultiPlay").document(MultiRoom3.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        if(document.exists()){
                            if(document.get("player1") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom3.getText().toString())
                                        .update("player1", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer1(user.getDisplayName());
                                                selRoom = MultiRoom3.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            if(document.get("player2") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom3.getText().toString())
                                        .update("player2", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer2(user.getDisplayName());
                                                selRoom = MultiRoom3.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            else{
                                toastMessage("모든 플레이어가 입장한 상태입니다. 다른 방을 선택해주세요.");
                                hideProgressDialog();
                            }
                        }
                    }
                });

            }
        });

        MultiRoom4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                DocumentReference docRef = mFirestore.collection("MultiPlay").document(MultiRoom4.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        if(document.exists()){
                            if(document.get("player1") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom4.getText().toString())
                                        .update("player1", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer1(user.getDisplayName());
                                                selRoom = MultiRoom4.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            if(document.get("player2") == null){
                                mFirestore.collection("MultiPlay").document(MultiRoom4.getText().toString())
                                        .update("player2", user.getDisplayName())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                create.setPlayer2(user.getDisplayName());
                                                selRoom = MultiRoom4.getText().toString();
                                                create.setRoomInfo(selRoom);
                                                Intent intent = new Intent(MultiWaiting.this, MultiWaitingRoom.class);
                                                startActivity(intent);
                                            }
                                        });
                            }

                            else{
                                toastMessage("모든 플레이어가 입장한 상태입니다. 다른 방을 선택해주세요.");
                                hideProgressDialog();
                            }
                        }
                    }
                });

            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
