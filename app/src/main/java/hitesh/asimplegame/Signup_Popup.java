package hitesh.asimplegame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Signup_Popup extends BaseActivity{
    private static final String TAG = "Sign-Up Process";
    private FirebaseAuth mAuth;
    private EditText NicknameField;
    private EditText PhoneField;
    private Button SignUp_confirm;
    private ImageButton Male, Female;
    private String Gender;
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, SendtoServer> UserInfo = new HashMap<>();
    SendtoServer send = new SendtoServer();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_signup);

        SignUp_confirm = (Button)findViewById(R.id.Popup_confirm);
        NicknameField = (EditText) findViewById(R.id.Popup_NicknameField);
        Male = findViewById(R.id.Popup_male);
        Female = findViewById(R.id.Popup_female);
        PhoneField = (EditText) findViewById (R.id.Popup_PhoneField);

        mAuth = FirebaseAuth.getInstance();

        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gender = "male";
            }
        });

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gender = "female";
            }
        });

        SignUp_confirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String email = user.getEmail();
                String phone = PhoneField.getText().toString();
                final String nickname = NicknameField.getText().toString();

                if(!email.equals("") && !nickname.equals("") && !phone.equals("")){
                    showProgressDialog();
                    send.setEmail(email);
                    send.setPhone(phone);
                    send.setGender(Gender);

                    UserInfo.put(nickname, send);

                    db.collection("Users").document("UsersInfo")
                            .set(UserInfo, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>(){
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Send User Data to Server was Successfully completed");
                                    toastMessage("사용자 데이터를 서버에 정상적으로 저장하였습니다.");
                                    Intent intent = new Intent(Signup_Popup.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Send User Data to Server was not completed.");
                                    toastMessage("사용자 데이터를 서버로 전송하지 못하였습니다.\n나중에 마이페이지에서 다시시도하십시오.");
                                }
                            });

                }

                else{
                    toastMessage("모든 필드를 채워주세요.");
                }
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
