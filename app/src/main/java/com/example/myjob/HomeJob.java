package com.example.myjob;

import static android.content.ContentValues.TAG;
import static com.example.myjob.RandomStringGenerator.generateRandomString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeJob extends AppCompatActivity {
    RecyclerView recyclerView, recyclerViewHome;
    ArrayList<ConstructorHome> list_home;
    HomeAdapter homeAdapter;
    HomeAdapter homeAdapterFilter;
    FirebaseUser user;
    FirebaseFirestore database_jobs;
    FirebaseFirestore database_post;
    Button btn_newst, btn_congNghe, btn_it, btn_marketing, btn_phucVu;

    DocumentReference docRef;
    TextView textView9;
    ArrayList<String> listID_jobs;
    ArrayList<String> listID_posts;
    SearchView searchViewHome;
    ImageButton btn_newpost, btn_home, btn_saved, btn_jobs, btn_account;

    FrameLayout search_visibility;

//    ImageButton btn_Home = findViewById(R.id.btn_Home);
//    ImageButton btn_Save = findViewById(R.id.btn_Saved);
//    ImageButton btn_Add = findViewById(R.);
//    ImageButton btn_Jobs;
//    ImageButton btn_account;
    static ArrayList<ConstructorHome> filteredList = new ArrayList<>();
    static HashMap<String, String> dictionary_Time = new HashMap<>();
    List<RecyclerView> recyclerViewList = new ArrayList<>();

    List<RecyclerView> filteredRecyclerViewList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_job);
        //Ánh xạ ID
        recyclerView = findViewById(R.id.recycView);
        btn_marketing = findViewById(R.id.btn_makerting);
        btn_phucVu = findViewById(R.id.btn_phucVu);
        btn_newst = findViewById(R.id.btn_newst);
        database_jobs = FirebaseFirestore.getInstance();
        database_post = FirebaseFirestore.getInstance();
        textView9 = findViewById(R.id.textView9);
        btn_it = findViewById(R.id.btn_IT);
        btn_congNghe = findViewById(R.id.btn_congNghe);
        btn_newpost = findViewById(R.id.btn_newpost);
        btn_home = findViewById(R.id.btn_home);
        searchViewHome = findViewById(R.id.searchViewHome);
        btn_saved = findViewById(R.id.btn_Saved);
        btn_home = findViewById(R.id.btn_home);
        btn_newpost = findViewById(R.id.btn_newpost);
        btn_jobs = findViewById(R.id.btn_jobs);
        btn_account = findViewById(R.id.btn_account);
        search_visibility = findViewById(R.id.seach_visibility);
        recyclerViewHome = findViewById(R.id.recycView2);
        listID_jobs = new ArrayList<>();
        listID_posts = new ArrayList<>();
        list_home = new ArrayList<>();

        Menu();
        getDate();
        ViewDataJobs();

        searchViewHome.clearFocus();
        searchViewHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) { //Kiểm tra nếu văn bản lọc mới rỗng
                    search_visibility.setVisibility(View.GONE);
                    filterList("");     //Chỉnh lại danh sách dữ liệu để trở về giá trị ban đầu
                    return false;
                } else {
                    search_visibility.setVisibility(View.VISIBLE);
                    searchViewHome.setVisibility(View.VISIBLE);
                    filterList(newText); //Lọc danh sách dữ liệu theo văn bản lọc mới
                    return true;
                }
            }
        });
        searchViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //search_visibility.setVisibility(View.VISIBLE);
            }
        });


    }

    public void getDate() {
        //database_post.enableNetwork();
        database_post.collection("Post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listID_posts.add(document.getId().toString());
                    }

                    //Lấy dữ liệu từ Post
                    for (int i = 0; i < listID_posts.size(); i++) {
                        database_post.collection("Post").document(listID_posts.get(i))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String datetime;
                            String JID_need;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    datetime = documentSnapshot.getData().get("Time").toString();
                                    JID_need = documentSnapshot.getData().get("JID_need").toString();
                                    dictionary_Time.put(JID_need,datetime);

//                                    if(JID_need != "" && datetime != "") {
//                                        //ConstructorHome(String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, int logo_URL, boolean checked)
//                                        list_home.add(new ConstructorHome(JID_need, "city", "career", "description", "exp", "salary", "specialized", datetime, R.drawable.img_1, false));
//                                        //homeAdapter.clearRecyclerView();
//
//                                        homeAdapter = new HomeAdapter(getApplicationContext(), list_home);
//
//                                        recyclerViewHome.setAdapter(homeAdapter);
//                                    }
                                }
                            }

                        });

                    }
                }
            }
        });
        database_post.disableNetwork();


    }
    public void ViewDataJobs() {

        database_jobs.collection("Jobs")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Lấy từng ID bỏ vào list
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listID_jobs.add(document.getId().toString());
                    }

                    //Lấy dữ liệu từ jobs
                    for(int i=0; i < listID_jobs.size(); i++) {
                        String id_Jobs = listID_jobs.get(i).toString();
                        database_jobs.collection("Jobs").document(listID_jobs.get(i))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    String companyName;
                                    String city;
                                    String career;
                                    String exp;
                                    String salary;
                                    String date_submitted = "chưa lấy được time";
                                    String description;
                                    String specialized;
                                    String logo_URL;



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
                                        date_submitted = dictionary_Time.get(id_Jobs.toString());
                                        //date_submitted = documentSnapshot.getData().get("Logo_URL").toString();
                                        if(companyName != "" && city != "" && career != "" && exp != "" && salary != "" && date_submitted != "") {
                                            //ConstructorHome(String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, int logo_URL, boolean checked)
                                            list_home.add(new ConstructorHome(companyName,city,career,description,exp, salary,specialized,date_submitted,R.drawable.img_1,false));

                                            // Sắp xếp danh sách theo thời gian gần nhất
//                                            Collections.sort(list_home, new Comparator<ConstructorHome>() {
//                                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
//                                                @Override
//                                                public int compare(ConstructorHome item1, ConstructorHome item2) {
//                                                    try {
//                                                        Date date1 = dateFormat.parse(item1.getDate());
//                                                        Date date2 = dateFormat.parse(item2.getDate());
//                                                        // Sử dụng compareTo để so sánh thời gian
//                                                        return date2.compareTo(date1);
//                                                    } catch (ParseException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    return 0;
//                                                }
//                                            });
                                            homeAdapter = new HomeAdapter(getApplicationContext(),list_home);
                                            recyclerView.setAdapter(homeAdapter);
                                            recyclerViewHome.setAdapter(homeAdapter);
                                            //recyclerViewList.add(recyclerView);



                                            //Set dữ liệu vào horizotalScrollView
                                            LayoutInflater inflater = LayoutInflater.from(HomeJob.this);
                                            LinearLayout linearLayoutHorizontal = findViewById(R.id.linear_layout_horizontal);
                                            View itemView = inflater.inflate(R.layout.item_view_scroll, linearLayoutHorizontal, false);

                                            // Truy cập các thành phần trong itemView1 và thiết lập giá trị
                                            int maxLength = 25;
                                            TextView txt_career = itemView.findViewById(R.id.txt_career);
                                            String originalText = career;
                                            if (originalText.length() > maxLength) {
                                                String trimmedText = originalText.substring(0, maxLength) + "...";
                                                txt_career.setText(trimmedText);
                                            } else {
                                                txt_career.setText(career);
                                            }
                                            TextView txt_diaChi = itemView.findViewById(R.id.txt_daiChi_HSR);
                                            txt_diaChi.setText(city);
                                            TextView txt_salary = itemView.findViewById(R.id.txt_salary);
                                            txt_salary.setText(salary);
                                            // Thêm itemView vào trong LinearLayout của HorizontalScrollView
                                            linearLayoutHorizontal.addView(itemView);
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
    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Lưu ý: Tháng bắt đầu từ 0
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String dateTime = String.format("%02d/%02d/%04d %02d:%02d", day, month, year, hour, minute);
        return dateTime;
    }

    private void filterList(String text) {
        filteredList.clear();
        String searchText = text.toLowerCase();
        for (ConstructorHome job : list_home) {
            String specialized = job.getSpecialized().toLowerCase();
            if (specialized.contains(searchText)) {
                filteredList.add(job);
            }
        }
        if (filteredList.isEmpty()) {
            homeAdapter.clearRecyclerView();
            recyclerView.setAdapter(homeAdapter);
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            homeAdapter.setFilteredList(filteredList);
            //recycler_search_visibility = recyclerView;
            recyclerView.setAdapter(homeAdapter);
        }
    }
    public void Menu() {
        btn_newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangBai.class);
                startActivity(intent);
                finish();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
            }
        });
        btn_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
            }
        });
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationForm.class);
                startActivity(intent);
                finish();
            }
        });
        btn_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SavedActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}