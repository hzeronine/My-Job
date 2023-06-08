package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    // Khai báo các biên giao diện
    EditText edt_currentpw , edt_Newpw , edt_Confirmpw;
    Button btn_Updatepw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        // Ánh xạ ID cho các biến giao diện
        edt_currentpw = findViewById(R.id.edt_CP_Currentpw);
        edt_Newpw = findViewById(R.id.edt_CP_Newpw);
        edt_Confirmpw = findViewById(R.id.edt_CP_Confirmpw);
        btn_Updatepw = findViewById(R.id.btn_CP_updatepw);
        //Xử lý su kiện
        btn_Updatepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CurrentPassword = edt_currentpw.getText().toString().trim();
                String NewPassword = edt_Newpw.getText().toString().trim();
                String ConFirmPassword = edt_Confirmpw.getText().toString().trim();
                if(CurrentPassword.isEmpty() || NewPassword.isEmpty() || ConFirmPassword.isEmpty()){
                    Toast.makeText(ChangePassword.this, "Please fill in all Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NewPassword.equals(ConFirmPassword));
                Toast.makeText(ChangePassword.this, "Please fill a correct reset Password", Toast.LENGTH_SHORT).show();
                return;}
            {
                Toast.makeText(ChangePassword.this, "Change Password Successfully", Toast.LENGTH_SHORT).show();
                edt_currentpw.setText("");
                edt_Newpw.setText("");
                edt_Confirmpw.setText("");


            }


        });
    }

}