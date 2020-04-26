package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignIn_with_Google extends BaseActivity {
    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton BtnGoogleSignin;
    private CallbackManager mCallbackManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        BtnGoogleSignin = findViewById(R.id.login_google);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions GSO = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, GSO);

        BtnGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplication(), Home.class);
            startActivity(intent);
            finish();
        }
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            showProgressDialog();

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }

            catch(ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            hideProgressDialog();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignIn_with_Google.this, "Google 로그인 완료", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        }

                        else{
                            hideProgressDialog();
                            Toast.makeText(SignIn_with_Google.this, "Google 로그인 실패\n계정을 다시 확인한 후 문제가 계속될 경우 관리자에게 문의하세요.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(this, Signup_Popup.class);
            startActivity(intent);
            finish();
        }
    }
}
