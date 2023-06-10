package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kotlinx.coroutines.Job;

public class HomeJob extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Test_Home> list_home;
    HomeAdapter homeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_job);
        recyclerView = findViewById(R.id.recycView);
        list_home = new ArrayList<>();
        list_home.add(new Test_Home("Chuyên viên tư vấn thiết kế đồ họa chuyên nghiệp Xspace Chuyên viên tư vấn thiết kế đồ họa chuyên nghiệp Xspace", "Công ty GHI", "Thỏa thuận", R.drawable.img_1));
        list_home.add(new Test_Home("Giảng viên tiếng Anh", "Trường đại học ABC", "20-25 triệu", R.drawable.img_1));
        list_home.add(new Test_Home("Buôn bán nhà đất", "Trường đại học X", "20-25 triệu", R.drawable.img_1));
        list_home.add(new Test_Home("Nhân viên IT", "Trường đại học ABC", "20-25 triệu", R.drawable.img_1));

        homeAdapter = new HomeAdapter(getApplicationContext(),list_home);
        recyclerView.setAdapter(homeAdapter);



//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference jobsRef = database.child("jobs");
//        String jobId = jobsRef.push().getKey();
//
//        Job job = new Job("San Francisco");
//        jobsRef.child(jobId).setValue(job);

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout linearLayoutHorizontal = findViewById(R.id.linear_layout_horizontal);

        // Inflate layout "item_view"
        View itemView = inflater.inflate(R.layout.item_view_scroll, linearLayoutHorizontal, false);

        // Truy cập các thành phần trong itemView1 và thiết lập giá trị
        TextView textView1 = itemView.findViewById(R.id.txt_tenCV);
        textView1.setText("Công Việc gì đây");
        // Thêm itemView vào trong LinearLayout của HorizontalScrollView
        linearLayoutHorizontal.addView(itemView);

        View itemView2 = inflater.inflate(R.layout.item_view_scroll, linearLayoutHorizontal, false);
        // Thêm itemView vào trong LinearLayout của HorizontalScrollView
        linearLayoutHorizontal.addView(itemView2);

        View itemView3 = inflater.inflate(R.layout.item_view_scroll, linearLayoutHorizontal, false);
        // Thêm itemView vào trong LinearLayout của HorizontalScrollView
        linearLayoutHorizontal.addView(itemView3);
    }
}