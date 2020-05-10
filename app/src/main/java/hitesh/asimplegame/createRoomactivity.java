package hitesh.asimplegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class createRoomactivity extends BaseActivity{
    EditText Roomname;
    Button cancel, confirm;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    getLevel getLevel;
    CreateRoomInfo create = new CreateRoomInfo();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    public String status = "Ready";
    public static String roomName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createroom);
        getLevel = new getLevel();
        cancel = findViewById(R.id.create_cancel);
        confirm = findViewById(R.id.create_confirm);
        Roomname = findViewById(R.id.create_roomname);


        final String level = getLevel.getLv();
//        if(level.equals("Easy")){
//            docRef = db.collection("MultiPlay").document("Level_Easy");
//        }
//
//        if(level.equals("Medium")){
//            docRef = db.collection("MultiPlay").document("Level_Medium");
//        }
//
//        if(level.equals("Hard")){
//            docRef = db.collection("MultiPlay").document("Level_Hard");
//        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createRoomactivity.this, MultiWaiting.class);
                startActivity(intent);
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                roomName = Roomname.getText().toString() + "(" + level + ")";

                if(!roomName.equals("")){
                    create.setPlayer1(user.getDisplayName());
                    create.setLevel(level);
                    create.setStatus(status);
                    create.setRoomInfo(roomName);

                    db.collection("MultiPlay").document(roomName)
                            .set(create, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    toastMessage("대기방 생성이 완료되었습니다.");
                                    hideProgressDialog();
                                    Intent intent = new Intent(createRoomactivity.this, MultiWaitingRoom.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toastMessage("대기방 생성을 완료하지 못하였습니다.\n네트워크 상태를 확인하시고 나중에 다시 시도해주세요.");
                                    hideProgressDialog();
                                }
                            });
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
