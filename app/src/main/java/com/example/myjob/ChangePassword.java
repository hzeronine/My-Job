package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    // Khai báo các biên giao diện
    EditText edt_currentpw , edt_Newpw , edt_Confirmpw;
    Button btn_Updatepw;
    ImageView btn_back;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        // Ánh xạ ID cho các biến giao diện
        init();
        //Xử lý su kiện
        setEvent();
    }

    private void setEvent() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationForm.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Updatepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ChangePassword.this,"Yes sir",Toast.LENGTH_SHORT).show();
                String oldPassword,new_password, re_new_password;
                oldPassword = String.valueOf(edt_currentpw.getText());
                new_password = String.valueOf(edt_Newpw.getText());
                re_new_password = String.valueOf(edt_Confirmpw.getText());
                if(TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(ChangePassword.this,"Enter old Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(new_password)){
                    Toast.makeText(ChangePassword.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(re_new_password)){
                    Toast.makeText(ChangePassword.this,"Enter Re-Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(new_password.equals(re_new_password) == false){
                    Toast.makeText(ChangePassword.this,"Wrong Re-Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), oldPassword);
                user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(new_password).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Update Success", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Update Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Wrong current password", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });
    }

    private void init() {
        edt_currentpw = findViewById(R.id.edt_CP_Currentpw);
        edt_Newpw = findViewById(R.id.edt_CP_Newpw);
        edt_Confirmpw = findViewById(R.id.edt_CP_Confirmpw);
        btn_Updatepw = findViewById(R.id.btn_CP_updatepw);
        btn_back = findViewById(R.id.btn_CP_back);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
    }

}