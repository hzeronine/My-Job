package com.example.myjob;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class SavedActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ViecLam> listViecLam;
    ArrayList<ViecLam> filteredList = new ArrayList<>();
    ViecLamAdapter viecLamAdapter;
    HomeAdapter homeAdapter;
    SearchView searchView;
    FirebaseFirestore database_ref;
    FirebaseUser user;
    ImageButton btnDelete;
    CheckBox ck_SAll;
    ImageButton btn_imgSaved, btn_Account, btn_Home, btn_NewPost, btn_Jobs;
    ArrayList<String> listID_saved;
    ArrayList<String> listID_posts;
    ArrayList<BigData> list_home;
    private HashMap<String, String> dictionary_Time = new HashMap<>();
    private HashMap<String, String> dictionary_Title = new HashMap<>();
    private HashMap<String, Integer> dictionary_NumberCare = new HashMap<>();
    private HashMap<String, String> dictionary_JIDNeed = new HashMap<>();
    private HashMap<String, String> dictionary_UID_Posted = new HashMap<>();
    private HashMap<String, String> dictionary_ID_Posted = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        setID();
        getDataPosted();
//        ViewDataSaved();
        Toast.makeText(getApplicationContext(),listID_posts.get(0),Toast.LENGTH_SHORT).show();
//        listViecLam = generateViecLamList(); // gán danh sách ViecLam với dữ liệu được cung cấp từ phương thức generateViecLamList()
//        viecLamAdapter = new ViecLamAdapter(getApplicationContext(), listViecLam);
//        recyclerView.setAdapter(viecLamAdapter);



        setEventActivity();
        setEventMenu();
    }

    private void setEventActivity() {
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

        ck_SAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                viecLamAdapter.selectAll();
            } else {
                viecLamAdapter.deselectAll();
            }
        });
    }

    private void setEventMenu() {
        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
            }
        });


        btn_NewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangBai.class);
                startActivity(intent);
            }
        });

        btn_Jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JobsPosted.class);
                startActivity(intent);
                finish();
            }
        });


        btn_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationForm.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setID() {
        // Ánh xạ ID
        list_home = new ArrayList<>();
        listID_saved = new ArrayList<>();
        listID_posts = new ArrayList<>();
        database_ref = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        btn_imgSaved = findViewById(R.id.btn_Saved);
        btn_imgSaved.setImageResource(R.drawable.click_ic_save);
        btnDelete = findViewById(R.id.btn_Delete);
        ck_SAll = findViewById(R.id.ck_SAll);
        btn_Account = findViewById(R.id.btn_Account);
        btn_Home = findViewById(R.id.btn_Home);
        btn_NewPost = findViewById(R.id.btn_NewPost);
        btn_Jobs = findViewById(R.id.btn_Jobs);

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

    public void ViewDataSaved() {

        database_ref.collection("Saved")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Lấy từng ID bỏ vào list
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listID_saved.add(document.getId().toString());
                            }

                            //Lấy dữ liệu từ jobs
                            for(int i=0; i < listID_saved.size(); i++) {
                                String id_Jobs = listID_saved.get(i).toString();
                                database_ref.collection("Saved").document(listID_saved.get(i))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                String id = id_Jobs;
                                                String companyName;
                                                String city;
                                                String career;
                                                String exp;
                                                String salary;
                                                String date_submitted = "null";
                                                String description;
                                                String specialized;
                                                String logo_URL;

                                                String JID_need;
                                                int numberCare = 10;
                                                String UID_post;
                                                String title;

                                                if(task.isSuccessful()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();

                                                    companyName = documentSnapshot.getData().get("Company_Name").toString();
                                                    city = documentSnapshot.getData().get("City").toString();
                                                    career = documentSnapshot.getData().get("Career").toString();
                                                    exp = documentSnapshot.getData().get("Exp").toString();
                                                    salary = documentSnapshot.getData().get("Salary").toString();
                                                    description = documentSnapshot.getData().get("Description").toString();
                                                    specialized = documentSnapshot.getData().get("Specialized").toString();
                                                    logo_URL = documentSnapshot.getData().get("Logo_URL").toString();
//                                        if((dictionary_DateTime.containsKey(id_Jobs))) {
//                                            date_submitted = dictionary_DateTime.values().toString();
//
//                                        }
                                                    // Toast.makeText(HomeJob.this, numberCare, Toast.LENGTH_SHORT).show();

                                                    //numberCare = Integer.parseInt(dictionary_JIDNeed.get(id_Jobs));
                                                    //Toast.makeText(HomeJob.this, id_Jobs, Toast.LENGTH_SHORT).show();
                                                    //date_submitted = documentSnapshot.getData().get("Logo_URL").toString();
                                                    date_submitted = dictionary_Time.get(id_Jobs.toString());
                                                    JID_need = dictionary_JIDNeed.get(id_Jobs);
                                                    UID_post = dictionary_UID_Posted.get(id_Jobs);
                                                    title = dictionary_Title.get(id_Jobs);
                                                    //numberCare = dictionary_NumberCare.get(id_Jobs);
                                                    String ID_post = dictionary_ID_Posted.get(id_Jobs);

                                                    if(companyName != "" && city != "" && career != "" && exp != "" && salary != "" && date_submitted != "") {
                                                        //ConstructorHome(String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, int logo_URL, boolean checked)
                                                        list_home.add(new BigData(JID_need,numberCare,title,ID_post, companyName,city,career,description,exp, salary,specialized,date_submitted,R.drawable.img_1,true));
                                                        //Toast.makeText(SavedActivity.this, JID_need, Toast.LENGTH_SHORT).show();

                                                        //listViecLam.add(new ViecLam("10","Nhân viên hành chính", "Công ty DEF", "Thỏa thuận", "Hà Nội", "29", 8));
                                                        // Sắp xếp danh sách theo thời gian gần nhất
//                                                        Collections.sort(list_home, new Comparator<BigData>() {
//                                                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
//                                                            @Override
//                                                            public int compare(BigData item1, BigData item2) {
//                                                                try {
//                                                                    Date date1 = dateFormat.parse(item1.getDate());
//                                                                    Date date2 = dateFormat.parse(item2.getDate());
//                                                                    // Sử dụng compareTo để so sánh thời gian
//                                                                    return date2.compareTo(date1);
//                                                                } catch (ParseException e) {
//                                                                    e.printStackTrace();
//                                                                }
//                                                                return 0;
//                                                            }
//                                                        });
                                                        homeAdapter = new HomeAdapter(getApplicationContext(), list_home);
                                                        recyclerView.setAdapter(homeAdapter);
//                                                        homeAdapter = new HomeAdapter(getApplicationContext(),list_home);
//                                                        recyclerView.setAdapter(homeAdapter);
//                                                        recyclerViewHome.setAdapter(homeAdapter);
//                                                        //recyclerViewList.add(recyclerView);
                                                    }
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getDataPosted() {
        database_ref.collection("Saved").document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            listID_saved = (ArrayList<String>) documentSnapshot.getData().get("Post_Saved");
                        }
                    }
                });
    }
}
