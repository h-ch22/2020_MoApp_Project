package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
	Map<String, Long> HighScore = new HashMap<>();
//	SendtoResult sendresult = new SendtoResult();
	public int score, scoredate;
	public Long setscore, sendScore;

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
		String[] units = time.split(":");
		int minutes = Integer.parseInt(units[1]);
		int seconds = Integer.parseInt(units[2]);
		int sectime = 60 * minutes + seconds;

		score = answer + sectime;
		textscore.setText("" + score + "점");
		sendScore = Long.valueOf(score);
		String name = user.getDisplayName();
		UserScore.put("score", sendScore);

		final DocumentReference docRefscore = db.collection("UserScore").document(name);
		docRefscore.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if (task.isSuccessful()) {
					DocumentSnapshot document = task.getResult();
					if (document.exists()) {
						Log.d(TAG, "DocumentSnapshot data: " + document.getData());
						setscore = document.getLong("highscore");
//						scoredate = setscore != null ? setscore.intValue() : null;
						update_hiscore();
					} else {
						Log.d(TAG, "No such document");
					}
				} else {
					Log.d(TAG, "get failed with ", task.getException());
				}
			}
		});

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

	private void update_hiscore() {

		if (setscore!= null && score < setscore.intValue()) {
			HighScore.put("highscore", setscore);
		} else {
			HighScore.put("highscore", sendScore);
		}
		db.collection("UserScore").document(user.getDisplayName()).set(HighScore, SetOptions.merge())
				.addOnSuccessListener(new OnSuccessListener<Void>() {
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