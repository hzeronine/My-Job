package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ViecLam> listViecLam;
    ViecLamAdapter viecLamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        recyclerView=findViewById(R.id.recyclerview);
        listViecLam = new ArrayList<>();
        listViecLam.add(new ViecLam("Chuyên Viên Cao Cấp Chăm Sóc Phát triển Khách Hàng","Ngân hàng TMCP Phát triển TP.HCM (HDBank)","Tới 28 triệu","Hải Phòng , Hà Nội","as",R.drawable.img_1));
        viecLamAdapter = new ViecLamAdapter(this,listViecLam);
        recyclerView.setAdapter(viecLamAdapter);
    }
}