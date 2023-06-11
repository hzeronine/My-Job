package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.media.Image;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ViecLam> listViecLam;
    ArrayList<ViecLam> filteredList = new ArrayList<>();
    ViecLamAdapter viecLamAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        recyclerView = findViewById(R.id.recyclerview);
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
        list.add(new ViecLam("11","Chuyên viên quản lý dự án", "Công ty LMN", "Tới 30 triệu", "TP.HCM", "5", 9));
        list.add(new ViecLam("12","Kế toán trưởng", "Công ty XYZ", "25-30 triệu", "Hà Nội", "8", 10));
        list.add(new ViecLam("13","Nhân viên văn phòng", "Công ty HIJ", "Thỏa thuận", "Hải Phòng", "12", 11));
        list.add(new ViecLam("14","Chuyên viên tài chính", "Công ty ABC", "20-25 triệu", "Hà Nội", "9", 12));
        list.add(new ViecLam("15","Nhân viên kinh doanh phát triển thị trường", "Công ty XYZ", "Thỏa thuận", "TP.HCM", "10", 13));
        list.add(new ViecLam("16","Nhân viên kiểm tra chất lượng", "Công ty PQR", "15-20 triệu", "Đà Nẵng", "6", 14));
        list.add(new ViecLam("17","Marketing manager", "Công ty LMN", "Tới 40 triệu", "Hà Nội", "15", 15));
        list.add(new ViecLam("18","Nhân viên thu mua", "Công ty GHI", "Thỏa thuận", "Hải Phòng", "10", 16));
        list.add(new ViecLam("19","Chuyên viên tư vấn tài chính", "Ngân hàng JKL", "20-25 triệu", "TP.HCM", "8", 17));
        list.add(new ViecLam("20","Kỹ sư phần mềm .NET", "Công ty MNO", "Thỏa thuận", "Hà Nội", "7", 18));

        return list;
    }

    private void filterList(String text) {
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
