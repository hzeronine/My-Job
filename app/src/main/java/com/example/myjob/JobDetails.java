package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class JobDetails extends AppCompatActivity {
    BigData bigData;
    ImageButton detail_btn_Saved;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore database_saved = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        bigData = (BigData) bundle.get("object_bigData");

        ImageButton detail_btn_back = findViewById(R.id.detail_btn_back);
        detail_btn_Saved = findViewById(R.id.detail_btn_Saved);
        TextView detail_salary = findViewById(R.id.detail_salary);
        TextView detail_career = findViewById(R.id.detail_career);
        TextView detail_work_exp = findViewById(R.id.detail_work_exp);
        TextView detail_specialized = findViewById(R.id.detail_specialized);
        TextView detail_address = findViewById(R.id.detail_address);
        TextView detail_job_posting_date = findViewById(R.id.detail_job_posting_date);
        TextView detail_txt_title = findViewById(R.id.detail_txt_title);
        TextView detail_txt_company = findViewById(R.id.detail_txt_company);
        TextView detail_jobDescription = findViewById(R.id.detail_jobDescription);
        TextView detail_interest= findViewById(R.id.detail_interest);

        ImageView detail_img_btn_logo = findViewById(R.id.detail_img_btn_logo);
        // Set data
        detail_txt_title.setText(bigData.getTitile());
        detail_txt_company.setText(bigData.getCompany_Name());
        detail_salary.setText(bigData.getSalary());
        detail_career.setText(bigData.getCareer());
        detail_work_exp.setText(bigData.getExp());
        detail_specialized.setText(bigData.getSpecialized());
        detail_address.setText(bigData.getCity());
        detail_job_posting_date.setText(bigData.getDate());
        detail_jobDescription.setText(bigData.getDescription());
        detail_interest.setText(bigData.getNumberCare() + " people ");
        //set image and other
        detail_btn_Saved.setImageResource(bigData.isChecked() ? R.drawable.click_ic_save : R.drawable.icon_save);
        Picasso.get()
                .load(bigData.getLogo_URL())
                .into(detail_img_btn_logo);

        detail_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Saved();
    }

    private void Saved(){
        detail_btn_Saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numberCare = bigData.getNumberCare();
                if(bigData.isChecked()) {
                    // Xóa khỏi mục Saved
                    detail_btn_Saved.setImageResource(R.drawable.icon_save);
                    bigData.setChecked(false);
                    String id = bigData.getId();
                    database_saved.collection("Saved").document(user.getUid())
                            .update("Post_Saved", FieldValue.arrayRemove(bigData.getPID()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Unsaved", Toast.LENGTH_SHORT).show();
                                }
                            });
                    // Giảm lượt tương tác
                    numberCare = numberCare - 1;
                    bigData.setNumberCare(numberCare);
                    // Tạo Map Update dữ liệu
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Number_Care", numberCare);

                    // Cập nhật dữ liệu vào trường cụ thể trong tài liệu
                    String collectionName = "Post";
                    String documentId = bigData.getUID_posted();
                    // Truy cập data và update
                    database_saved.collection(collectionName)
                            .document(documentId)
                            .update(updateData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Cập nhật thành công
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý khi cập nhật thất bại
                                }
                            });
                    //Ngược lại
                } else {

                    detail_btn_Saved.setImageResource(R.drawable.click_ic_save);
                    bigData.setChecked(true);

                    //Truy cập database Saved
                    database_saved.collection("Saved").document(user.getUid())
                            .update("Post_Saved", FieldValue.arrayUnion(bigData.getPID()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Tăng tương tác post
                    numberCare = numberCare +1;
                    bigData.setNumberCare(numberCare);
                    //int count = Integer.valueOf(numberCare);
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Number_Care", numberCare);

                    // Cập nhật dữ liệu vào trường cụ thể trong tài liệu
                    String collectionName = "Post"; // Tên của bộ sưu tập chứa tài liệu
                    String documentId = bigData.getUID_posted(); // ID của tài liệu cần cập nhật
                    database_saved.collection(collectionName)
                            .document(documentId)
                            .update(updateData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Cập nhật thành công
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý khi cập nhật thất bại
                                }
                            });
                }
            }
        });
    }
}