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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JobsPosted extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ViecLam> listJobsPostedItem;

    ArrayList<BigData> listBigdata = new ArrayList<>();
    ArrayList<ViecLam> filteredList = new ArrayList<>();
    ViecLamAdapter jobsPostedAdapter;
    SearchView searchView;
    FirebaseFirestore database_ref;
    FirebaseUser user;
    ImageButton btnDelete;
    CheckBox ck_SAll;
    ImageButton btn_imgSaved, btn_Account, btn_Home, btn_NewPost, btn_Jobs;
    public List<String> listID_saved;
    public ArrayList<String> listID_posts;
    private Map<String,BigData> dic_bigdata = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_posted);
        SetID();
        getDataPosted();

        SetEvent();
        EventHome();
    }


    public void getDataPosted() {

        database_ref.collection("Saved").document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            AtomicInteger counter = new AtomicInteger(0);
                            for (Object obj : (List<String>)documentSnapshot.getData().get("Post_Posted")){
                                listID_saved.add(obj.toString());
                                if(counter.incrementAndGet() == ((List<?>) documentSnapshot.getData().get("Post_Posted")).size())
                                    SetDataDicBigdata();
                                //Toast.makeText(SavedActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();
                            }

                            //if(counter.incrementAndGet() == ((List<Object>) documentSnapshot.getData().get("Post_Saved")).size())

                            //Toast.makeText(getApplicationContext(),String.valueOf(listID_saved.size()) ,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Get Failure",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void SetDataDicBigdata() {
        if(listID_saved.size() <= 1){
            Toast.makeText(JobsPosted.this, String.valueOf(listID_saved.size()), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(JobsPosted.this, String.valueOf(listID_saved.size()), Toast.LENGTH_SHORT).show();
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
                                dic_bigdata.get(JID_need).setChecked(true);
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
                    listJobsPostedItem.add(new ViecLam(temp.getPID(),temp.getTitile(),temp.getCompany_Name(),temp.getSalary(),temp.getCity(),String.valueOf(30-diffInDays),temp.getLogo_URL()));
                    listBigdata.add(temp);
                }
            }catch (Exception e){
                Toast.makeText(this, "false to set day" , Toast.LENGTH_SHORT).show();
            }
            if(x.incrementAndGet() == dic_bigdata.size()){
                jobsPostedAdapter = new ViecLamAdapter(getApplicationContext(), listJobsPostedItem, listBigdata);
                recyclerView.setAdapter(jobsPostedAdapter);
            }
            //listViecLam.add(new ViecLam(temp.getPID(),temp.getTitile(),temp.getCompany_Name(),temp.getSalary(),temp.getCity(),temp.get));
        }
    }
    private void SetID() {
        listID_saved = new ArrayList<>();
        listID_posts = new ArrayList<>();
        database_ref = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.recyclerview_JP);
        searchView = findViewById(R.id.searchView_JL);
        searchView.clearFocus();
    }

    private void SetEvent() {
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
    }

    private void EventHome() {
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