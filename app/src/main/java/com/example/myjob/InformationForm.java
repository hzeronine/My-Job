package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationForm extends AppCompatActivity {
    CircleImageView image_avatar;
    Button btn_infor_logout, btn_infor_changePassword, btn_infor_uploadCV;
    TextView txt_infor_name;
    ImageView FL_infor_homeJobs, FL_infor_saved, FL_infor_post, FL_infor_jobs, FL_infor_account;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseFirestore db;
    DocumentReference docRef;
    FirebaseUser user;
    //ProgressDialog progressDialog = new ProgressDialog(InformationForm.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_form);
        getID();
        setData();
        setEvent();
        //if(progressDialog.isShowing())
            //progressDialog.dismiss();
    }

    private void setData() {


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            String name = "";
            String avatar_path = "";
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    name = doc.getData().get("name").toString();
                    if (name != "")
                        txt_infor_name.setText(name);
                   avatar_path = doc.getData().get("image").toString();
                   //Toast.makeText(InformationForm.this,storageRef.toString(),Toast.LENGTH_SHORT).show();
                   //Toast.makeText(InformationForm.this,avatar_path,Toast.LENGTH_SHORT).show();
                   StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("user_images/nonAvatar/nonAvatar.jpg");
                   //Toast.makeText(InformationForm.this,imageRef.toString(),Toast.LENGTH_SHORT).show();
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'

                            Glide.with(InformationForm.this)
                                    .load(uri.toString())
                                    .into(image_avatar);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(InformationForm.this,"false to load",Toast.LENGTH_SHORT).show();
                        }
                    });
//                    try {
//                        File file = File.createTempFile("temp","jpg");
//                        imageRef.getFile(file).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
//                                //Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                                //image_avatar.setImageBitmap(bitmap);
//                                Picasso.get().load(imageRef.getDownloadUrl()).into(image_avatar);
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }


                }
            }
        });
    }

    private void setEvent() {
        btn_infor_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChangePassword.class);
                startActivity(intent);
                finish();
            }
        });

        btn_infor_uploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });

        btn_infor_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        FL_infor_homeJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
            }
        });
        FL_infor_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SavedActivity.class);
                startActivity(intent);
                finish();
            }
        });
        FL_infor_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangBai.class);
                startActivity(intent);
            }
        });
        FL_infor_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JobsPosted.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getID() {
        image_avatar = findViewById(R.id.image_infor_avatar);
        btn_infor_logout = findViewById(R.id.btn_infor_logout);
        btn_infor_changePassword = findViewById(R.id.btn_infor_changePassword);
        btn_infor_uploadCV = findViewById(R.id.btn_infor_uploadCV);
        FL_infor_homeJobs = findViewById(R.id.btn_infor_homejob);
        FL_infor_saved = findViewById(R.id.btn_infor_saved);
        FL_infor_post = findViewById(R.id.btn_infor_post);
        FL_infor_jobs = findViewById(R.id.btn_infor_Jobs);
        FL_infor_account = findViewById(R.id.btn_infor_Account);
        txt_infor_name = findViewById(R.id.txt_infor_NameUser);
        //firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();
        //firebase store
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("Users").document(user.getUid());
        //firebase storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }
}