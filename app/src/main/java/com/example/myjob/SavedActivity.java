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

public class SavedActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ViecLam> listViecLam;
    ArrayList<ViecLam> filteredList = new ArrayList<>();
    ViecLamAdapter viecLamAdapter;
    SearchView searchView;

    ImageButton btn_imgSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        recyclerView = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        btn_imgSaved = findViewById(R.id.btn_Saved);
        btn_imgSaved.setImageResource(R.drawable.click_ic_save);
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
        listViecLam = generateViecLamList(); // gán danh sách ViecLam với dữ liệu được cung cấp từ phương thức generateViecLamList()
        viecLamAdapter = new ViecLamAdapter(getApplicationContext(), listViecLam);
        recyclerView.setAdapter(viecLamAdapter);

        ImageButton btnDelete = findViewById(R.id.btn_Delete);

        btnDelete.setOnClickListener(v -> {
            if(filteredList.isEmpty()) {
                ArrayList<ViecLam> selectedItems = viecLamAdapter.getSelectedItems();
                for (ViecLam item : selectedItems) {
                    listViecLam.remove(item);
                    Toast.makeText(getApplicationContext(), String.format("Deleted item with ID %s", item.getID()), Toast.LENGTH_SHORT).show();
                }
                viecLamAdapter.notifyDataSetChanged();

            }else{
                ArrayList<ViecLam> selectedItems = viecLamAdapter.getSelectedItems();
                for (ViecLam item : selectedItems) {
                    listViecLam.remove(item);
                }
                for (ViecLam item : selectedItems) {
                    filteredList.remove(item);
                    Toast.makeText(getApplicationContext(), String.format("Deleted item with ID %s", item.getID()), Toast.LENGTH_SHORT).show();
                }
                viecLamAdapter.notifyDataSetChanged();
            }
        });

        CheckBox ck_SAll = findViewById(R.id.ck_SAll);
        ck_SAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
               viecLamAdapter.selectAll();
            } else {
                viecLamAdapter.deselectAll();
            }
        });
        ImageButton btn_Home = findViewById(R.id.btn_Home);
        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_NewPost = findViewById(R.id.btn_NewPost);
        btn_NewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangBai.class);
                startActivity(intent);
            }
        });

        ImageButton btn_Jobs = findViewById(R.id.btn_Jobs);
        btn_Jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JobsPosted.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_Account = findViewById(R.id.btn_Account);
        btn_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationForm.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Đưa data vào đây nè Huy
    private ArrayList<ViecLam> generateViecLamList() {
        ArrayList<ViecLam> list = new ArrayList<>();
        list.add(new ViecLam("1","Chuyên Viên Cao Cấp Chăm Sóc Phát triển Khách Hàng", "Ngân hàng TMCP Phát triển TP.HCM (HDBank)", "Tới 28 triệu", "Hải Phòng, Hà Nội", "as", R.drawable.img_1));
        list.add(new ViecLam("2","Quản lý sản xuất", "Công ty GHI", "Thỏa thuận", "TP.HCM", "5", 1));
        list.add(new ViecLam("3","Giảng viên tiếng Anh", "Trường đại học ABC", "20-25 triệu", "Hà Nội", "10", 2));
        list.add(new ViecLam("4","Nhân viên bán hàng", "Công ty JKL", "Thỏa thuận", "Đà Nẵng", "12", 3));
        list.add(new ViecLam("5","Designer UX/UI", "Công ty MNO", "15-20 triệu", "Hà Nội", "13", 4));
        list.add(new ViecLam("6","Chuyên viên tư vấn bảo hiểm", "Ngân hàng XYZ", "20-25 triệu", "TP.HCM", "9", 6));
        list.add(new ViecLam("7","Thực tập sinh CNTT", "Công ty ABC", "5-10 triệu", "Hà Nội", "6", 5));
        list.add(new ViecLam("8","Kỹ sư phần mềm Java", "Công ty PQR", "20-25 triệu", "Đà Nẵng", "7", 6));
        list.add(new ViecLam("9","Chuyên viên kiểm toán", "Công ty STU", "Thỏa thuận", "Hà Nội", "30", 7));
        list.add(new ViecLam("10","Nhân viên hành chính", "Công ty DEF", "Thỏa thuận", "Hà Nội", "29", 8));
        return list;
    }

    private void filterList(String text) {
        filteredList.clear();
        for (ViecLam viecLam : listViecLam) {
            if (viecLam.getTieuDe().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(viecLam);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            viecLamAdapter.setFilteredList(filteredList);
        }
    }
}
