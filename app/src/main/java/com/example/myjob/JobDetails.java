package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class JobDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        BigData bigData = (BigData) bundle.get("object_bigData");

        TextView txt_thongtin1 = findViewById(R.id.txt_thongtin1);
        txt_thongtin1.setText(bigData.getCompany_Name());
    }
}