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

public class SignUp extends BaseActivity {
    private static final String TAG = "Sign-Up Process";
    private FirebaseAuth mAuth;

    private EditText SignUp_email;
    private EditText SignUp_password;
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
    DocumentReference docRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUp_email = (EditText)findViewById(R.id.Signup_email);
        SignUp_password = (EditText)findViewById(R.id.Signup_password);
        SignUp_confirm = (Button)findViewById(R.id.Signup_confirm);
        NicknameField = (EditText) findViewById(R.id.Signup_NicknameField);
        Male = findViewById(R.id.register_male);
        Female = findViewById(R.id.register_female);
        PhoneField = (EditText) findViewById (R.id.Signup_PhoneField);

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
                String email = SignUp_email.getText().toString();
                String password = SignUp_password.getText().toString();
                String phone = PhoneField.getText().toString();
                final String nickname = NicknameField.getText().toString();

                if(!email.equals("") && !password.equals("") && !nickname.equals("") && !phone.equals("")){
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
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Send User Data to Server was not completed.");
                                    toastMessage("사용자 데이터를 서버로 전송하지 못하였습니다.\n나중에 마이페이지에서 다시시도하십시오.");
                                }
                            });

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(nickname).build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>(){
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Log.d(TAG, "User profile updated.");
                                                        }
                                                    }
                                                });

                                        hideProgressDialog();
                                        toastMessage("회원가입 완료");
                                        Intent intent = new Intent(SignUp.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else{
                                        hideProgressDialog();
                                        toastMessage("회원가입에 실패하였습니다.\n나중에 다시시도하시거나 관리자에게 문의하십시오.");
                                        return;
                                    }
                                }
                            });
                }

                else{
                    toastMessage("모든 필드를 채워주세요.");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}