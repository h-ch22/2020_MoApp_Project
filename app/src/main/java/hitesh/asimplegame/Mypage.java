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
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private ImageButton homebtn, profilebtn, malebtn, femalebtn;
    private String gender;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int GALLERY_CODE = 10;
    private static String phoneNumber;
    Map<String, SendtoServer> UserInfo = new HashMap<>();
    SendtoServer send = new SendtoServer();
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private String Displayname;


    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        uploadbtn = findViewById(R.id.mypage_upload);
        mypagesave = findViewById(R.id.mypage_savebtn);
        mypagelogout = findViewById(R.id.mypage_logoutbtn);
        name = findViewById(R.id.mypage_namevaule);
        number = findViewById(R.id.mypage_numbervaule);
        email = findViewById(R.id.mypage_Emailvaule);
        homebtn = findViewById(R.id.mypage_backbtu);
        profilebtn = findViewById(R.id.mypage_setprofile);
        malebtn =  findViewById(R.id.mypage_male);
        femalebtn = findViewById(R.id.mypage_female);
        circle = findViewById(R.id.mypage_profile1);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, Home.class);
                finish();
                startActivity(intent);
            }
        });

        Displayname = user.getDisplayName();
        name.setText(Displayname);

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

        email.setText(user.getEmail());

        DocumentReference docRef = db.collection("Users").document(Displayname);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String phoneNumber = documentSnapshot.getString("phone");
                System.out.println("폰넘버"+phoneNumber);
            }
        });

        number.setText(phoneNumber);
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
                String newname = name.getText().toString();
                String newemail = email.getText().toString();
                String newnumber = number.getText().toString();
                //user.updateEmail(newemail);
                UserInfo.put(Displayname, send);
                send.setEmail(newemail);
                send.setPhone(newnumber);
                send.setGender(gender);
                db.collection("Users").document(user.getDisplayName())
                        .set(UserInfo, SetOptions.merge())
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
                Toast.makeText(Mypage.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
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
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
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

    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            //StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            StorageReference ref = storageReference.child("images/" + Displayname);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast.makeText(Mypage.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
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

