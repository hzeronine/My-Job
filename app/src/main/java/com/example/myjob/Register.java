package com.example.myjob;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button btn_register;
    TextView txt_signin;
    FirebaseAuth mAuth;
    EditText editText_emailUser, editText_password, editText_re_password, editText_namedisplay;
    FirebaseUser user;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getID();
        init();
        setEvent();
    }
    void getID(){
        txt_signin = findViewById(R.id.txt_signin);
        mAuth = FirebaseAuth.getInstance();
        btn_register = findViewById(R.id.btn_register);
        editText_emailUser = findViewById(R.id.txtbox_register_userEmail);
        editText_password = findViewById(R.id.txtbox_register_password);
        editText_re_password = findViewById(R.id.txtbox_register_re_password);
        editText_namedisplay = findViewById(R.id.txtbox_register_namedisplay);
        db = FirebaseFirestore.getInstance();

    }
    void init(){
        String text = "Already have an account? <b>Login</b>";
        txt_signin.setText(Html.fromHtml(text));
    }
    void setEvent(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password,re_password, nameDisplay;
                email = String.valueOf(editText_emailUser.getText());
                password = String.valueOf(editText_password.getText());
                re_password = String.valueOf(editText_re_password.getText());
                nameDisplay = String.valueOf(editText_namedisplay.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT);
                    return;
                }
                if(TextUtils.isEmpty(nameDisplay)){
                    Toast.makeText(Register.this,"Enter Name",Toast.LENGTH_SHORT);
                    return;
                }
                if(TextUtils.isEmpty(re_password)){
                    Toast.makeText(Register.this,"Enter Re-Password",Toast.LENGTH_SHORT);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Toast.makeText(Register.this, "Create Account success.", Toast.LENGTH_SHORT).show();
                                        Adddatabase(email, password, nameDisplay);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Create Account fail.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    void Adddatabase(String email, String password, String nameDisplay){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Authentication succes.", Toast.LENGTH_SHORT).show();
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> DataUser = new HashMap<>();
                            DataUser.put("email", email);
                            DataUser.put("name", nameDisplay);
                            DataUser.put("image", "user_image/nonAvatar/nonAvatar.jpg");

                            db.collection("Users").document(user.getUid())
                                    .set(DataUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(Register.this, "add data succes.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(Register.this, "add data failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}