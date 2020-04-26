package hitesh.asimplegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends BaseActivity {
    private EditText email;
    private EditText password;
    private Button Signin;
    private Button Signup;
    private LoginButton Facebook_Signin;
    private SignInButton Google_Signin;
    private Button forgot;
    FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = (EditText) findViewById(R.id.login_id);
        password = (EditText) findViewById(R.id.login_pw);
        Signin = (Button) findViewById(R.id.login_btn);
        Signup = (Button) findViewById(R.id.login_register);
        Facebook_Signin = (LoginButton) findViewById(R.id.facebook_login_button);
        Google_Signin = (SignInButton) findViewById(R.id.login_google);
        forgot = findViewById(R.id.login_forgot);

        mAuth = FirebaseAuth.getInstance();

        Facebook_Signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignIn_with_Facebook.class);
                startActivity(intent);
                finish();
            }
        });

        Google_Signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignIn_with_Google.class);
                startActivity(intent);
                finish();
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pw = password.getText().toString();
                showProgressDialog();

                if(mail == null || pw == null){
                    hideProgressDialog();
                    Toast.makeText(SignIn.this, "이메일과 비밀번호를 입력하십시오.", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(mail, pw)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>(){

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    hideProgressDialog();
                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    Toast.makeText(SignIn.this, "로그인을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }

                                else{
                                    hideProgressDialog();
                                    Toast.makeText(SignIn.this, "로그인을 완료하지 못했습니다.\n이메일과 비밀번호를 다시 확인하신 후 문제가 계속 발생할 경우 관리자에게 문의하세요.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, ResetPassword.class);
                startActivity(intent);
            }
        });


    }
}

