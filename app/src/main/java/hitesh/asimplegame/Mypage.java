package hitesh.asimplegame;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mypage extends BaseActivity {
    private static final String TAG ="MYPAGETag";
    private FirebaseAuth mAuth;
    private CircleImageView circle;
    private Button mypagesave, mypagelogout, uploadbtn;
    private EditText name, number, email;
    private TextView score, highscore;
    private ImageButton homebtn, profilebtn, malebtn, femalebtn;
    private String gender, phone;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int GALLERY_CODE = 10;
    final FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri filePath;
    private String Displayname;
    StorageReference storageRef = storage.getReference();
    SendtoServer send = new SendtoServer();
    public Long setscore, sethighscore;


    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();
        Displayname = user.getDisplayName();
        StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+getUid());

        uploadbtn = findViewById(R.id.mypage_upload);
        mypagesave = findViewById(R.id.mypage_savebtn);
        mypagelogout = findViewById(R.id.mypage_logoutbtn);
        name = findViewById(R.id.mypage_namevaule);
        number = findViewById(R.id.mypage_numbervaule);
        email = findViewById(R.id.mypage_Emailvaule);
        homebtn = findViewById(R.id.mypage_backbtu);
        profilebtn = findViewById(R.id.mypage_setprofile);
        malebtn = findViewById(R.id.mypage_male);
        femalebtn = findViewById(R.id.mypage_female);
        circle = findViewById(R.id.mypage_profile1);
        score = findViewById(R.id.mypage_lastscorevalue);
        highscore = findViewById(R.id.mypage_highscorevaule);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, Home.class);
                finish();
                startActivity(intent);
            }
        });

        Glide.with(this).load(ref).into(circle);

        name.setText(Displayname);
        email.setText(user.getEmail());
        profilebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        final DocumentReference docRefuser = db.collection("Users").document(Displayname);
        docRefuser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        number.setText(document.getString("phone"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        final DocumentReference docRefscore = db.collection("UserScore").document(Displayname);
        docRefscore.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        score.setText(document.getLong("score").toString());
                        if(document.getLong("highscore") != null){
                        highscore.setText(document.getLong("highscore").toString());
                        }
                        setscore = document.getLong("score");
                        sethighscore = document.getLong("highscore");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        malebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
            }
        });

        femalebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
            }
        });

        mypagelogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Mypage.this, SignIn.class);
                finish();
                startActivity(intent);
            }
        });

        mypagesave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newname =name.getText().toString().trim();
                String newnumber = number.getText().toString().trim();
                String newemail = email.getText().toString().trim();

                if(!newnumber.equals("") && !newname.equals("") && !newemail.equals("")) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(newname)
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                        update();
                                    }
                                }
                            });
                    user.updateEmail(newemail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User email address updated.");
                                    }
                                }
                            });
                    Toast.makeText(Mypage.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Mypage.this, "필드를 채워주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SelectImage()
    {   // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), GALLERY_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                circle.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void update() {
        final FirebaseUser user = mAuth.getCurrentUser();
        String newDisplayname = user.getDisplayName();
        Map<String, Object> UserInfo = new HashMap<>();
        Map<String, Long> UserScore = new HashMap<>();
        String newname =name.getText().toString().trim();
        String newnumber = number.getText().toString().trim();
        String newemail = email.getText().toString().trim();

        UserInfo.put("email", newemail);
        UserInfo.put("phone", newnumber);
        UserInfo.put("gender", gender);
        UserInfo.put("name", newname);

        db.collection("Users").document(Displayname).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

        db.collection("UserScore").document(Displayname).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

        db.collection("Users").document(newDisplayname).set(UserInfo, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                });

        UserScore.put("score", setscore);
        UserScore.put("highscore",sethighscore);

        db.collection("UserScore").document(newDisplayname).set(UserScore, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Send User Data to Server was Successfully completed");
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Send User Data to Server was not completed.");
                    }
                });
    }

    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("images/" + getUid());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Mypage.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Mypage.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }
}