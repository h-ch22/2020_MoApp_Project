package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class ResultActivity extends QuestionActivity {
	private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
	private DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
	FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
	private FirebaseFirestore db = FirebaseFirestore.getInstance();
	private static final String TAG = "Send-Result Process";
	Map<String, Long> UserScore = new HashMap<>();
//	SendtoResult sendresult = new SendtoResult();
	public int score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		TextView textResult = (TextView) findViewById(R.id.textResult);
		TextView textTime = (TextView) findViewById(R.id.textTime);
		TextView textscore = (TextView) findViewById(R.id.textScore);

		Bundle b = getIntent().getExtras();
		int answer = b.getInt("score");
		textResult.setText("" + answer +"문제");

		String time = b.getString("time");

		if(time == null){
			textTime.setText("00:00:00");
		}

		else{
			textTime.setText("" + time);
		}

		String convertTime = time;
		String[] units = time.split(":"); //will break the string up into an array
		int minutes = Integer.parseInt(units[1]); //first element
		int seconds = Integer.parseInt(units[2]); //second element
		int sectime = 60 * minutes + seconds;

		score = answer + sectime;
		textscore.setText("" + score + "점");

		String name = user.getDisplayName();

//		sendresult.setScore(score);
//		sendresult.setLevel("null");
		Long sendScore = Long.valueOf(score);
		UserScore.put("score", sendScore);

		db.collection("UserScore").document(name).set(UserScore, SetOptions.merge())
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
						toastMessage("사용자 데이터를 서버로 전송하지 못하였습니다.");
					}
				});

	}

	public void playagain(View o) {
			Intent intent = new Intent(this, QuestionActivity.class);
			startActivity(intent);
	}

	private void toastMessage(String message){
		Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
	}
}