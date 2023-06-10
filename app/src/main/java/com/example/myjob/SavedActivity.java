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
        recyclerView = findViewById(R.id.recyclerview);
        listViecLam = new ArrayList<>();
        listViecLam.add(new ViecLam("Chuyên Viên Cao Cấp Chăm Sóc Phát triển Khách Hàng","Ngân hàng TMCP Phát triển TP.HCM (HDBank)","Tới 28 triệu","Hải Phòng , Hà Nội","as",R.drawable.img_1));
//        listViecLam.add(new ViecLam("Kế toán trưởng", "Công ty TNHH DEF", "25-30 triệu", "Hà Nội", "12", R.drawable.img_4));
        listViecLam.add(new ViecLam("Quản lý sản xuất", "Công ty GHI", "Thỏa thuận", "TP.HCM", "5", 1));
        listViecLam.add(new ViecLam("Giảng viên tiếng Anh", "Trường đại học ABC", "20-25 triệu", "Hà Nội", "10", 2));
        listViecLam.add(new ViecLam("Nhân viên bán hàng", "Công ty JKL", "Thỏa thuận", "Đà Nẵng", "12", 3));
        listViecLam.add(new ViecLam("Designer UX/UI", "Công ty MNO", "15-20 triệu", "Hà Nội", "13",4));
        listViecLam.add(new ViecLam("Chuyên viên tư vấn bảo hiểm", "Ngân hàng XYZ", "20-25 triệu", "TP.HCM", "9",6));
        listViecLam.add(new ViecLam("Thực tập sinh CNTT", "Công ty ABC", "5-10 triệu", "Hà Nội", "6", 5));
        listViecLam.add(new ViecLam("Kỹ sư phần mềm Java", "Công ty PQR", "20-25 triệu", "Đà Nẵng", "7", 6));
        listViecLam.add(new ViecLam("Chuyên viên kiểm toán", "Công ty STU", "Thỏa thuận", "Hà Nội", "30", 7));
        listViecLam.add(new ViecLam("Nhân viên hành chính", "Công ty DEF", "Thỏa thuận", "Hà Nội", "29", 8));

        viecLamAdapter = new ViecLamAdapter(getApplicationContext(),listViecLam);
        recyclerView.setAdapter(viecLamAdapter);

    }
}