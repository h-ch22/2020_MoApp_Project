package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends Activity {
    private EditText ResetPassword;
    private Button Reset, Cancel;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        ResetPassword = (EditText) findViewById(R.id.resetpassword_email);
        Reset = findViewById(R.id.resetpassword_send);
        Cancel = findViewById(R.id.resetpassword_cancel);
        mAuth = FirebaseAuth.getInstance();

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ResetPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "E-mail을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ResetPassword.this, "비밀번호 재설정링크를 입력한 E-mail로 전송하였습니다.", Toast.LENGTH_SHORT).show();
                                }

                                else{
                                    Toast.makeText(ResetPassword.this, "E-mail을 재확인해주세요.\n문제가 계속 발생할 경우 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
