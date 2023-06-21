package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;

public class JobsPosted extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ViecLam> listJobsPostedItem;
    ArrayList<ViecLam> filteredList = new ArrayList<>();
    ViecLamAdapter jobsPostedAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_posted);
        recyclerView = findViewById(R.id.recyclerview_JP);
        searchView = findViewById(R.id.searchView_JL);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) { //Kiểm tra nếu văn bản lọc mới rỗng
                    filterList("");     //Chỉnh lại danh sách dữ liệu để trở về giá trị ban đầu
                    return false;
                } else {
                    filterList(newText); //Lọc danh sách dữ liệu theo văn bản lọc mới
                    return true;
                }
            }
        });
        listJobsPostedItem = generateJobsPostedItemList(); // gán danh sách JobsPostedItem với dữ liệu được cung cấp từ phương thức generateJobsPostedItemList()
        jobsPostedAdapter = new ViecLamAdapter(getApplicationContext(), listJobsPostedItem);
        recyclerView.setAdapter(jobsPostedAdapter);

        ImageButton btnDelete = findViewById(R.id.btn_Delete_JP);

        btnDelete.setOnClickListener(v -> {
            if(filteredList.isEmpty()) {
                ArrayList<ViecLam> selectedItems = jobsPostedAdapter.getSelectedItems();
                for (ViecLam item : selectedItems) {
                    listJobsPostedItem.remove(item);
                    Toast.makeText(getApplicationContext(), String.format("Deleted item with ID %s", item.getID()), Toast.LENGTH_SHORT).show();
                }
                jobsPostedAdapter.notifyDataSetChanged();

            }else{
                ArrayList<ViecLam> selectedItems = jobsPostedAdapter.getSelectedItems();
                for (ViecLam item : selectedItems) {
                    listJobsPostedItem.remove(item);
                }
                for (ViecLam item : selectedItems) {
                    filteredList.remove(item);
                    Toast.makeText(getApplicationContext(), String.format("Deleted item with ID %s", item.getID()), Toast.LENGTH_SHORT).show();
                }
                jobsPostedAdapter.notifyDataSetChanged();
            }
        });

        CheckBox ck_SAll = findViewById(R.id.ck_SAll2);
        ck_SAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                jobsPostedAdapter.selectAll();
            } else {
                jobsPostedAdapter.deselectAll();
            }
        });
        ImageButton btn_Home = findViewById(R.id.btn_Home_JP);
        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_NewPost = findViewById(R.id.btn_NewPost_JP);
        btn_NewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangBai.class);
                startActivity(intent);
            }
        });

        ImageButton btn_Jobs = findViewById(R.id.btn_Jobs_JP);
        btn_Jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JobsPosted.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_Account = findViewById(R.id.btn_Account_JP);
        btn_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationForm.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private ArrayList<ViecLam> generateJobsPostedItemList() {
        ArrayList<ViecLam> list = new ArrayList<>();
        list.add(new ViecLam("1","Chuyên Viên Cao Cấp Chăm Sóc Phát triển Khách Hàng", "Ngân hàng TMCP Phát triển TP.HCM (HDBank)", "Tới 28 triệu", "Hải Phòng, Hà Nội", "as", "R.drawable.img_1"));
        list.add(new ViecLam("2","Quản lý sản xuất", "Công ty GHI", "Thỏa thuận", "TP.HCM", "5", "1"));
        list.add(new ViecLam("3","Giảng viên tiếng Anh", "Trường đại học ABC", "20-25 triệu", "Hà Nội", "10", "2"));
        list.add(new ViecLam("4","Nhân viên bán hàng", "Công ty JKL", "Thỏa thuận", "Đà Nẵng", "12", "3"));
        list.add(new ViecLam("5","Designer UX/UI", "Công ty MNO", "15-20 triệu", "Hà Nội", "13", "4"));
        return list;
    }

    private void filterList(String text) {
        filteredList.clear();
        for (ViecLam item : listJobsPostedItem) {
            if (item.getTieuDe().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            jobsPostedAdapter.setFilteredList(filteredList);
        }
    }
}