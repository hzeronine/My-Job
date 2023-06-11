package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DangBai extends AppCompatActivity {
    EditText edt_title, edt_companyname, edt_workaddress, edt_specialized, edt_experience,
            edt_salary, edt_position, edt_description;
    Button btn_upload;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai);
        edt_title = findViewById(R.id.edt_UL_title);
        edt_companyname = findViewById(R.id.edt_UL_companyname);
        edt_workaddress = findViewById(R.id.edt_UL_workaddress);
        edt_specialized = findViewById(R.id.edt_UL_specialized);
        edt_experience = findViewById(R.id.edt_UL_experience);
        edt_salary = findViewById(R.id.edt_UL_salary);
        edt_position = findViewById(R.id.edt_UL_position);
        edt_description = findViewById(R.id.edt_UL_description);
        btn_upload = findViewById(R.id.btn_UL_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdata();
            }
        });
    }

    void setdata() {
        String title, companyname, workaddress, specialized, experience, salary, position, description;
        title = String.valueOf(edt_title.getText());
        companyname = String.valueOf(edt_companyname.getText());
        workaddress = String.valueOf(edt_workaddress.getText());
        specialized = String.valueOf(edt_specialized.getText());
        experience = String.valueOf(edt_experience.getText());
        salary = String.valueOf(edt_salary.getText());
        position = String.valueOf(edt_position.getText());
        description = String.valueOf(edt_description.getText());
        String JID = random();
        String logo_url = "jobs_logo/default/defaultLogo.png";
        String PID = LastFour(user.getUid()) + "_" + JID;
        // Sign in success, update UI with the signed-in user's information
        //Toast.makeText(DangBai.this, "Authentication succes.", Toast.LENGTH_SHORT).show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> Datajobs = new HashMap<>();
        Datajobs.put("Company_Name", companyname);
        Datajobs.put("Logo_URL", logo_url);
        Datajobs.put("City", workaddress);
        Datajobs.put("Specialized",specialized);
        Datajobs.put("Career",position);
        Datajobs.put("Exp",experience);
        Datajobs.put("Salary",salary);
        Datajobs.put("Description",description);
        Map<String,Object> Datapost = new HashMap<>();
        Datapost.put("Title","");
        Datapost.put("UID_Posted","");
        Datapost.put("Number_Care","");
        Datapost.put("JID_need","");
        Datapost.put("Time","");
        Datapost.put("CV_ID_Uploaded","");


        db.collection("Users").document(user.getUid())
                .set(Datajobs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(DangBai.this, "add data succes.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(DangBai.this, "add data failed.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public String random() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(letters.length());
            char randomChar = letters.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString + "_" + random.nextInt(90);

    }

    public String LastFour(String inputString) {
        if (inputString.length() >= 4) {
            String lastFour = inputString.substring(inputString.length() - 4);
            return lastFour;
        } else {
            // Xử lý khi chuỗi đầu vào có ít hơn 4 ký tự
            System.out.println("Chuỗi đầu vào quá ngắn");
        }

        return inputString;
    }
}
