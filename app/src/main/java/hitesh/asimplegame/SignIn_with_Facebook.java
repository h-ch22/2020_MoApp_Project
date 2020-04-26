package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn_with_Facebook extends BaseActivity implements View.OnClickListener{
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private static final String TAG = "Facebook Signin : ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton loginButton =  findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>(){
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "Facebook : onSuccess - " + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Facebook : onCancel");
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "Facebook : onError");
                updateUI(null);
            }
        });
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d(TAG, "handleFacebookAccessToken : " + token);
        showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithCredential : success");
                            Toast.makeText(SignIn_with_Facebook.this, "Facebook 로그인 완료.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }

                        else{
                            Log.w(TAG, "signInWithCredential : failure", task.getException());
                            Toast.makeText(SignIn_with_Facebook.this, "Facebook 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    }

    private void signOut(){
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(this, Signup_Popup.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(View v){
        int i = v.getId();

        if(i == R.id.facebook_login_button){
            signOut();
        }
    }
}
