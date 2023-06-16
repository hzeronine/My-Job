package com.example.myjob;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
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

public class HomeJob extends AppCompatActivity {
    RecyclerView recyclerView, recyclerViewHome;
    ArrayList<BigData> list_home;
    HomeAdapter homeAdapter;
    FirebaseFirestore database_jobs;
    FirebaseFirestore database_post;
    FirebaseFirestore database_saved;
    FirebaseUser user;
    Button btn_fulltime, btn_parttime, btn_casual, btn_intern, btn_newst;
    TextView textView9;
    ArrayList<String> listID_jobs;
    ArrayList<String> listID_posts;
    ArrayList<String> listID_saved;
    SearchView searchViewHome;
    ImageButton btn_newpost, btn_home, btn_saved, btn_jobs, btn_account;
    Animation animation;
    FrameLayout logo_splash_visibility;

    FrameLayout search_visibility;
    private int searchErrorCount = 0;
    static ArrayList<BigData> filteredList = new ArrayList<>();
    private HashMap<String, String> dictionary_Time = new HashMap<>();
    private HashMap<String, String> dictionary_Title = new HashMap<>();
    private HashMap<String, Integer> dictionary_NumberCare = new HashMap<>();
    private HashMap<String, String> dictionary_JIDNeed = new HashMap<>();
    private HashMap<String, String> dictionary_UID_Posted = new HashMap<>();
    private HashMap<String, String> dictionary_ID_Posted = new HashMap<>();
    private HashMap<String, String> dictionary_ID_Saved = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_job);
        //Ánh xạ ID
        recyclerView = findViewById(R.id.recycView);
        btn_intern = findViewById(R.id.btn_intern);
        btn_casual = findViewById(R.id.btn_Casual);
        btn_newst = findViewById(R.id.btn_newst);
        database_jobs = FirebaseFirestore.getInstance();
        database_post = FirebaseFirestore.getInstance();
        database_saved = FirebaseFirestore.getInstance();
        textView9 = findViewById(R.id.textView9);
        btn_parttime = findViewById(R.id.btn_parttime);
        btn_fulltime = findViewById(R.id.btn_fulltime);
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
        listID_saved = new ArrayList<>();
        list_home = new ArrayList<>();
        searchViewHome.clearFocus();
        user = FirebaseAuth.getInstance().getCurrentUser();
        logo_splash_visibility = findViewById(R.id.logo_splash_visibility);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        SplashHome();
        btn_home.setImageResource(R.drawable.click_ic_home);
        Menu();

        getDateAndJobs();
        //getDateAndJobs();

        Search();
        refreshDataView();


        btn_fulltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListbyCareer("full-time");
            }
        });
        btn_newst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapter.clearRecyclerView();
                getDateAndJobs();
            }
        });


    }

    public void getDateAndJobs() {
        //database_post.enableNetwork();
        //dictionary_Time.clear();
        // Khởi tạo một Counter để đếm số công việc đã hoàn thành
        AtomicInteger counter = new AtomicInteger(0);
        database_post.collection("Post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //
                    getIDSaved();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listID_posts.add(document.getId().toString());
                    }

                    //Lấy dữ liệu từ Post
                    for (int i = 0; i < listID_posts.size(); i++) {
                        String id_Post = listID_posts.get(i).toString();
                        database_post.collection("Post").document(listID_posts.get(i))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String datetime;
                            String JID_need;
                            int number_care;
                            String JID_post;
                            String title;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    JID_need = documentSnapshot.getData().get("JID_need").toString();
                                    datetime = documentSnapshot.getData().get("Time").toString();
                                    title = documentSnapshot.getData().get("Time").toString();
                                    JID_post = documentSnapshot.getData().get("UID_Posted").toString();
                                    number_care = documentSnapshot.getLong("Number_Care").intValue();
                                    dictionary_Time.put(JID_need,datetime);
                                    dictionary_Title.put(JID_need,title);
                                    dictionary_UID_Posted.put(JID_need,JID_post);
                                    dictionary_NumberCare.put(JID_need,number_care);
                                    dictionary_JIDNeed.put(JID_need,JID_need);
                                    dictionary_ID_Posted.put(JID_need, id_Post);
                                    //Toast.makeText(HomeJob.this, JID_need, Toast.LENGTH_SHORT).show();
                                    // Tăng counter lên 1
                                    if (counter.incrementAndGet() == listID_posts.size()) {
                                        // Nếu counter đạt giá trị của listID_posts, tức là tất cả công việc đã hoàn thành
                                        // Gọi phương thức ViewDataJobs

                                        ViewDataJobs();

                                    }
//                                    }
                                }
                            }

                        });

                    }
                }
            }
        });
        //database_post.disableNetwork();



    }

    public void getIDSaved() {
            AtomicInteger counter = new AtomicInteger(0);
            database_saved.collection("Saved").document(user.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();
                                int i = 1;
                                for (Object obj : (List<String>)documentSnapshot.getData().get("Post_Saved"))
                                    listID_saved.add(obj.toString());
                               // Toast.makeText(getApplicationContext(),String.valueOf(dictionary_ID_Saved.size()) ,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Get Failure",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
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
                                        date_submitted = dictionary_Time.get(id_Jobs.toString());
                                        JID_need = dictionary_JIDNeed.get(id_Jobs);
                                        UID_post = dictionary_UID_Posted.get(id_Jobs);
                                        title = dictionary_Title.get(id_Jobs);
                                        numberCare = dictionary_NumberCare.get(id_Jobs);
                                        String ID_post = dictionary_ID_Posted.get(id_Jobs);
                                        String PID = dictionary_ID_Posted.get(id_Jobs);
                                        boolean getSaved = listID_saved.contains(PID);
                                        boolean saved = false;
                                        if(getSaved) {
                                            saved = true;
                                        }
                                       // Toast.makeText(HomeJob.this, numberCare, Toast.LENGTH_SHORT).show();

                                        //numberCare = Integer.parseInt(dictionary_JIDNeed.get(id_Jobs));
                                        //Toast.makeText(HomeJob.this, id_Jobs, Toast.LENGTH_SHORT).show();
                                        //date_submitted = documentSnapshot.getData().get("Logo_URL").toString();
                                        if(companyName != "" && city != "" && career != "" && exp != "" && salary != "" && date_submitted != "") {
                                            //ConstructorHome(String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, int logo_URL, boolean checked)
                                            list_home.add(new BigData(PID,numberCare,title,ID_post, companyName,city,career,description,exp, salary,specialized,date_submitted,R.drawable.img_1,saved));

                                            // Sắp xếp danh sách theo thời gian gần nhất
                                            Collections.sort(list_home, new Comparator<BigData>() {
                                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                                @Override
                                                public int compare(BigData item1, BigData item2) {
                                                    try {
                                                        Date date1 = dateFormat.parse(item1.getDate());
                                                        Date date2 = dateFormat.parse(item2.getDate());
                                                        // Sử dụng compareTo để so sánh thời gian
                                                        return date2.compareTo(date1);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    return 0;
                                                }
                                            });
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
                                            TextView txt_career = itemView.findViewById(R.id.txt_SpecializedVS);
                                            String originalText = career;
                                            if (originalText.length() > maxLength) {
                                                String trimmedText = originalText.substring(0, maxLength) + "...";
                                                txt_career.setText(trimmedText);
                                            } else {
                                                txt_career.setText(career);
                                            }


                                            TextView txt_Company_Name = itemView.findViewById(R.id.txt_Company_NameVS);
                                            setTrimmedText(txt_Company_Name, city, 25);
                                            TextView txt_salary = itemView.findViewById(R.id.txt_salarySV);
                                            txt_salary.setText(salary);
                                            TextView txt_city = itemView.findViewById(R.id.txt_view1);
                                            setTrimmedText(txt_city, city, 6);
                                            TextView txt_cereer = itemView.findViewById(R.id.txt_view2);
                                            setTrimmedText(txt_cereer, career, 6);
                                            TextView txt_exp = itemView.findViewById(R.id.txt_view3);
                                            setTrimmedText(txt_exp, exp, 6);
                                            ImageView logo = itemView.findViewById(R.id.imgView_logo);
                                            logo.setImageResource(R.drawable.img_1);
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
    private void filterList(String text) {

        filteredList.clear();
        String searchText = text.toLowerCase();
        for (BigData job : list_home) {
            String specialized = job.getSpecialized().toLowerCase();
            String career = job.getCareer().toLowerCase();
            if (specialized.contains(searchText) || career.contains(searchText)) {
                filteredList.add(job);
            }
        }
        if (filteredList.isEmpty()) {
            homeAdapter.clearRecyclerView();
            //recyclerView.setAdapter(homeAdapter);


        } else {
            homeAdapter.setFilteredList(filteredList);
            //recycler_search_visibility = recyclerView;
            recyclerView.setAdapter(homeAdapter);
        }

        if (filteredList.isEmpty() && searchErrorCount == 0) {
            Toast.makeText(this, "Can't find the job you're looking for, please try searching again!", Toast.LENGTH_SHORT).show();
            searchErrorCount++;
        }
    }
    private void filterListbyCareer(String text) {
        filteredList.clear();
        String searchText = text.toLowerCase();
        for (BigData job : list_home) {
            String career = job.getCareer().toLowerCase();
            if (career.contains(searchText)) {
                filteredList.add(job);
            }
        }
        if (filteredList.isEmpty()) {
            homeAdapter.clearRecyclerView();
            recyclerViewHome.setAdapter(homeAdapter);
            Toast.makeText(this, "Can't find the job you're looking for at the moment", Toast.LENGTH_SHORT).show();
        } else {
            homeAdapter.setFilteredList(filteredList);
            //recycler_search_visibility = recyclerView;
            recyclerViewHome.setAdapter(homeAdapter);
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
    private void Search() {
        searchViewHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) { //Kiểm tra nếu văn bản lọc mới rỗng
                    search_visibility.setVisibility(View.GONE);
                    filterList("");
                    return false;
                } else {
                    search_visibility.setVisibility(View.VISIBLE);
                    searchViewHome.setVisibility(View.VISIBLE);
                    filterList(newText); //Lọc danh sách dữ liệu theo văn bản lọc mới
                    return true;
                }
            }
        });
    }
    private void setTrimmedText(TextView textView, String text, int maxLength) {
        if (text.length() > maxLength) {
            String trimmedText = text.substring(0, maxLength) + "...";
            textView.setText(trimmedText);
        } else {
            textView.setText(text);
        }
    }

    private void Filter(String text) {
        filteredList.clear();
        String searchText = text.toLowerCase();
        for (BigData job : list_home) {
            String specialized = job.getSpecialized().toLowerCase();
            String career = job.getCareer().toLowerCase();
            if (specialized.contains(searchText) || career.contains(searchText)) {
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
    public void onBackPressed() {
        // Kiểm tra nếu RecyclerView không hiển thị
        if (search_visibility.getVisibility() == View.VISIBLE) {
            // Hiển thị lại RecyclerView
            search_visibility.setVisibility(View.GONE);
            getDateAndJobs();
            // Nếu cần, cập nhật dữ liệu trong RecyclerView
            // recyclerView.setAdapter(homeAdapter);
        } else {
            // Thực hiện hành động mặc định khi nhấn nút "Back"
            super.onBackPressed();
        }
    }
    public void refreshDataView() {
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeAdapter.clearRecyclerView(); // Xóa dữ liệu hiện có trong RecyclerView
                Intent intent = new Intent(getApplicationContext(), HomeJob.class);
                startActivity(intent);
                finish();
                //getDateAndJobs(); // Lấy dữ liệu mới
                swipeRefreshLayout.setRefreshing(false); // Kết thúc hiệu ứng "refresh"
            }
        });
    }

    public void delay3s(final Runnable task) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                task.run();
            }
        }, 2000);
    }

    private void SplashHome() {
        logo_splash_visibility.setVisibility(View.VISIBLE);
        delay3s(new Runnable() {
            @Override
            public void run() {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Phương thức này được gọi khi hiệu ứng bắt đầu
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Phương thức này được gọi khi hiệu ứng kết thúc
                        logo_splash_visibility.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Phương thức này được gọi khi hiệu ứng lặp lại (nếu có)
                    }
                });

                logo_splash_visibility.startAnimation(animation);
            }
        });
    }
}