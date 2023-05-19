package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    Button btn_register;
    TextView txt_signin;
    FirebaseAuth mAuth;
    EditText editText_emailUser,editText_password, editText_re_password;
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
        editText_emailUser = findViewById(R.id.txtbox_username);
        editText_password = findViewById(R.id.txtbox_password);
        editText_re_password = findViewById(R.id.txtbox_re_password);
    }
    void init(){
        txt_signin = findViewById(R.id.txt_signin);
        String text = "Already have an account? <b>Login</b>";
        txt_signin.setText(Html.fromHtml(text));
    }
    void setEvent(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password,re_password;
                email = String.valueOf(editText_emailUser.getText());
                password = String.valueOf(editText_password.getText());
                re_password = String.valueOf(editText_re_password.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT);
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
                                    Toast.makeText(Register.this, "Create Account succes.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Create Account fail.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}