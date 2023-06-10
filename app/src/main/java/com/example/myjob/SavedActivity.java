package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<ViecLam> listViecLam;
    ViecLamAdapter viecLamAdapter;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        recyclerView=findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);

                return true;
            }
    });
        listViecLam = new ArrayList<>();
        listViecLam.add(new ViecLam("Chuyên Viên Cao Cấp Chăm Sóc Phát triển Khách Hàng","Ngân hàng TMCP Phát triển TP.HCM (HDBank)","Tới 28 triệu","Hải Phòng , Hà Nội","as",R.drawable.img_1));
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

    private void filterList(String text) {
        ArrayList<ViecLam> filteredList = new ArrayList<>();
        for (ViecLam viecLam:listViecLam)
        {
            if(viecLam.getTieuDe().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(viecLam);
            }
        }
        if (filteredList.isEmpty())
        {
            Toast.makeText(this,"No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            viecLamAdapter.setFilteredList(filteredList);
        }
    }

}