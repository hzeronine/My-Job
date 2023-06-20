package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SavedActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ViecLam> listViecLam = new ArrayList<>();
    ArrayList<ViecLam> filteredList = new ArrayList<>();
    ViecLamAdapter viecLamAdapter;
    HomeAdapter homeAdapter;
    SearchView searchView;
    FirebaseFirestore database_ref;
    FirebaseUser user;
    ImageButton btnDelete;
    CheckBox ck_SAll;
    ImageButton btn_imgSaved, btn_Account, btn_Home, btn_NewPost, btn_Jobs;
    public List<String> listID_saved;
    public ArrayList<String> listID_posts;
    ArrayList<BigData> list_home;
    private Map<String,BigData> dic_bigdata = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        setID();
        getDataPosted();


        //listID_saved.add("Asd");


        //ViewDataSaved();
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
                Intent intent = new Intent(getApplicationContext(), JobsList.class);
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
        btn_imgSaved = findViewById(R.id.btn_Saved_SF);
        btn_imgSaved.setImageResource(R.drawable.click_ic_save);
        btnDelete = findViewById(R.id.btn_Delete);
        ck_SAll = findViewById(R.id.ck_SAll);
        btn_Account = findViewById(R.id.btn_Account);
        btn_Home = findViewById(R.id.btn_Home);
        btn_NewPost = findViewById(R.id.btn_NewPost);
        btn_Jobs = findViewById(R.id.btn_Jobs);

    }

    //Đưa data vào đây nè Huy
//    private ArrayList<ViecLam> generateViecLamList() {
//        ArrayList<ViecLam> list = new ArrayList<>();
//        list.add(new ViecLam("1","Chuyên Viên Cao Cấp Chăm Sóc Phát triển Khách Hàng", "Ngân hàng TMCP Phát triển TP.HCM (HDBank)", "Tới 28 triệu", "Hải Phòng, Hà Nội", "as", R.drawable.img_1));
//        list.add(new ViecLam("2","Quản lý sản xuất", "Công ty GHI", "Thỏa thuận", "TP.HCM", "5", 1));
//        list.add(new ViecLam("3","Giảng viên tiếng Anh", "Trường đại học ABC", "20-25 triệu", "Hà Nội", "10", 2));
//        list.add(new ViecLam("4","Nhân viên bán hàng", "Công ty JKL", "Thỏa thuận", "Đà Nẵng", "12", 3));
//        list.add(new ViecLam("5","Designer UX/UI", "Công ty MNO", "15-20 triệu", "Hà Nội", "13", 4));
//        list.add(new ViecLam("6","Chuyên viên tư vấn bảo hiểm", "Ngân hàng XYZ", "20-25 triệu", "TP.HCM", "9", 6));
//        list.add(new ViecLam("7","Thực tập sinh CNTT", "Công ty ABC", "5-10 triệu", "Hà Nội", "6", 5));
//        list.add(new ViecLam("8","Kỹ sư phần mềm Java", "Công ty PQR", "20-25 triệu", "Đà Nẵng", "7", 6));
//        list.add(new ViecLam("9","Chuyên viên kiểm toán", "Công ty STU", "Thỏa thuận", "Hà Nội", "30", 7));
//        list.add(new ViecLam("10","Nhân viên hành chính", "Công ty DEF", "Thỏa thuận", "Hà Nội", "29", 8));
//        return list;
//    }

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

    public void SetDataDicBigdata() {
        if(listID_saved.size() <= 1){
            Toast.makeText(SavedActivity.this, String.valueOf(listID_saved.size()), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(SavedActivity.this, String.valueOf(listID_saved.size()), Toast.LENGTH_SHORT).show();
        AtomicInteger atomicInt = new AtomicInteger(0);
        for(int i = 1; i < listID_saved.size(); i++){
            //Toast.makeText(SavedActivity.this, listID_saved.get(i), Toast.LENGTH_SHORT).show();
            database_ref.collection("Post").document(listID_saved.get(i))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                String JID_need = documentSnapshot.getData().get("JID_need").toString();
                                dic_bigdata.put(JID_need, new BigData());
                                dic_bigdata.get(JID_need).setPID(documentSnapshot.getId());
                                dic_bigdata.get(JID_need).setId(documentSnapshot.getData().get("JID_need").toString());
                                dic_bigdata.get(JID_need).setTitile(documentSnapshot.getData().get("Title").toString());
                                dic_bigdata.get(JID_need).setDate(documentSnapshot.getData().get("Time").toString());
                                dic_bigdata.get(JID_need).setNumberCare(documentSnapshot.getLong("Number_Care").intValue());
                                dic_bigdata.get(JID_need).setUID_posted(documentSnapshot.getData().get("UID_Posted").toString());
                                if(atomicInt.incrementAndGet() == listID_saved.size() - 1)
                                    GetJobData();
                            }else{
                                Toast.makeText(getApplicationContext(),"Get Failure",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

    public void getDataPosted() {

        database_ref.collection("Saved").document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            //AtomicInteger counter = new AtomicInteger(0);
                            for (Object obj : (List<String>)documentSnapshot.getData().get("Post_Saved")){
                                listID_saved.add(obj.toString());
                                //Toast.makeText(SavedActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();
                            }

                           //if(counter.incrementAndGet() == ((List<Object>) documentSnapshot.getData().get("Post_Saved")).size())
                            SetDataDicBigdata();
                            //Toast.makeText(getApplicationContext(),String.valueOf(listID_saved.size()) ,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Get Failure",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    public void GetJobData() {
        AtomicInteger x = new AtomicInteger(0);
        for(Map.Entry<String,BigData> entry : dic_bigdata.entrySet()){
            database_ref.collection("Jobs").document(entry.getKey())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();
                                String JID = documentSnapshot.getId();
                                dic_bigdata.get(JID).setCity(documentSnapshot.getData().get("City").toString());
                                dic_bigdata.get(JID).setCareer(documentSnapshot.getData().get("Career").toString());
                                dic_bigdata.get(JID).setCompany_Name(documentSnapshot.getData().get("Company_Name").toString());
                                dic_bigdata.get(JID).setDescription(documentSnapshot.getData().get("Description").toString());
                                dic_bigdata.get(JID).setExp(documentSnapshot.getData().get("Exp").toString());
                                dic_bigdata.get(JID).setLogo_URL(documentSnapshot.getData().get("Logo_URL").toString());
                                dic_bigdata.get(JID).setSalary(documentSnapshot.getData().get("Salary").toString());
                                dic_bigdata.get(JID).setSpecialized(documentSnapshot.getData().get("Specialized").toString());
                                if(x.incrementAndGet() == dic_bigdata.size())
                                    ViewData();
                            }else{

                            }
                        }
                    });

        }

    }


    private void ViewData() {
        AtomicInteger x = new AtomicInteger(0);
        for(Map.Entry<String,BigData> entry : dic_bigdata.entrySet()) {
            BigData temp = entry.getValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            long diffInDays;
            //Toast.makeText(this, String.valueOf(entry.getKey()), Toast.LENGTH_SHORT).show();
            try {
                Date datePosted = dateFormat.parse(temp.getDate().toString());
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                long diffInMillis = currentDate.getTime() - datePosted.getTime();
                diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                if(diffInDays < 30){
                    listViecLam.add(new ViecLam(temp.getPID(),temp.getTitile(),temp.getCompany_Name(),temp.getSalary(),temp.getCity(),String.valueOf(30-diffInDays),temp.getLogo_URL()));
                }
            }catch (Exception e){
                Toast.makeText(this, "false to set day" , Toast.LENGTH_SHORT).show();
            }
            if(x.incrementAndGet() == dic_bigdata.size()){
                viecLamAdapter = new ViecLamAdapter(getApplicationContext(), listViecLam);
                recyclerView.setAdapter(viecLamAdapter);
            }
            //listViecLam.add(new ViecLam(temp.getPID(),temp.getTitile(),temp.getCompany_Name(),temp.getSalary(),temp.getCity(),temp.get));
        }
    }
}
