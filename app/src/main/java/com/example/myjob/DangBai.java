package com.example.myjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DangBai extends AppCompatActivity {
    int checkError = 0;

    EditText edt_title, edt_companyname, edt_workaddress, edt_specialized, edt_experience,
            edt_salary, edt_position, edt_description, edt_career;
    Button btn_upload;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseFirestore db;
    ImageButton btn_Back;
    Spinner spinner;
    ImageView img_Company;
    boolean checkJID;
    Uri imageUri;
    final String[] selectedOption = new String[1];
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    // Lấy tham chiếu đến thư mục trong Firebase Storage mà bạn muốn lưu trữ ảnh
    StorageReference storageRef = storage.getReference().child("jobs_logo");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai);
        edt_title = findViewById(R.id.edt_UL_title);
        edt_companyname = findViewById(R.id.edt_UL_companyname);
        edt_workaddress = findViewById(R.id.edt_UL_workaddress);
        edt_specialized = findViewById(R.id.edt_UL_specialized);
        edt_experience = findViewById(R.id.edt_UL_experience);
        edt_salary = findViewById(R.id.edt_UL_salary);
        edt_position = findViewById(R.id.edt_UL_position);
        edt_description = findViewById(R.id.edt_UL_description);
        btn_upload = findViewById(R.id.btn_UL_upload);
        edt_career = findViewById(R.id.edt_UL_career);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        spinner = findViewById(R.id.spinner);
        img_Company = findViewById(R.id.img_Company);


// ...
        Menu();
        chooseCareer();
        img_Company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chọn ảnh từ ứng dụng khác
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });


        upLoadImg();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkError != 0) {
                    TextView textView22 = findViewById(R.id.textView22);
                    textView22.setTextColor(Color.RED);
                    textView22.setText("Please choose position");
                } else {
                    setdata();
                }

            }
        });


    }

    void setdata() {
        String title, companyname, workaddress, specialized, experience, salary, position, description;
        final String[] imageUrl = new String[1];
        title = String.valueOf(edt_title.getText());
        companyname = String.valueOf(edt_companyname.getText());
        workaddress = String.valueOf(edt_workaddress.getText());
        specialized = String.valueOf(edt_specialized.getText());
        experience = String.valueOf(edt_experience.getText());
        salary = String.valueOf(edt_salary.getText());
        position = selectedOption[0];
        description = String.valueOf(edt_description.getText());

        Calendar calendar = Calendar.getInstance();
        String JID = String.valueOf(calendar.getTimeInMillis());

        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);

        // Tải ảnh lên Firebase Storage
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Đăng ký lắng nghe sự kiện hoàn thành của quá trình tải lên
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    // Lấy URL của ảnh đã tải lên
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            // Thực hiện các hành động tiếp theo với URL của ảnh đã tải lên
                            imageUrl[0] = downloadUri.toString();
//                            String url = imageUrl[0];
//                            String fileName = "jobs_logo/default/defaultLogo.png";
//                            try {
//                                URL imageUrl = new URL(url);
//                                fileName = new File(imageUrl.getPath()).getName();
//                            } catch (MalformedURLException e) {
//                                e.printStackTrace();
//                            }
                            String PID = RandomStringGenerator.LastFour(user.getUid()) + "_" + JID;
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(DangBai.this, "Authentication succes.", Toast.LENGTH_SHORT).show();
                            Map<String, Object> Datajobs = new HashMap<>();
                            Datajobs.put("Company_Name", companyname);
                            Datajobs.put("Logo_URL", imageUrl[0]);
                            Datajobs.put("City", workaddress);
                            Datajobs.put("Specialized",specialized);
                            Datajobs.put("Career",position);
                            Datajobs.put("Exp",experience);
                            Datajobs.put("Salary",salary);
                            Datajobs.put("Description",description);

                            Map<String,Object> Datapost = new HashMap<>();
                            Datapost.put("Title",title);
                            Datapost.put("UID_Posted",user.getUid());
                            Datapost.put("Number_Care",0);
                            Datapost.put("JID_need",JID);
                            Datapost.put("Time",RandomStringGenerator.getCurrentDateTime());
                            Datapost.put("CV_ID_Uploaded", Arrays.asList(""));


                            db.collection("Jobs").document(JID)
                                    .set(Datajobs)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(DangBai.this, "add data succes.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(DangBai.this, "add data failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            db.collection("Post").document(PID)
                                    .set(Datapost)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                    });
                } else {
                    // Xử lý khi quá trình tải lên không thành công
                }
            }
        });


    }

    public String random() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(letters.length());
            char randomChar = letters.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString + "_" + random.nextInt(90);

    }

    public String LastFour(String inputString) {
        if (inputString.length() >= 4) {
            String lastFour = inputString.substring(inputString.length() - 4);
            return lastFour;
        } else {
            // Xử lý khi chuỗi đầu vào có ít hơn 4 ký tự
            System.out.println("Chuỗi đầu vào quá ngắn");
        }

        return inputString;
    }
    // Phương thức này sẽ được gọi khi người dùng chọn một ảnh và quay lại từ Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có khớp với PICK_IMAGE_REQUEST hay không
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy Uri của ảnh đã chọn
            imageUri = data.getData();

            // TODO: Xử lý ảnh được chọn ở đây, ví dụ như hiển thị nó trong ImageView
            img_Company.setImageURI(imageUri);
        }
    }

    private void upLoadImg() {
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một đường dẫn mới cho ảnh trên Firebase Storage

            }
        });
    }
    public void Menu() {
        ImageButton btn_newpost, btn_home, btn_saved, btn_jobs, btn_account;
        btn_saved = findViewById(R.id.btn_Saved);
        btn_home = findViewById(R.id.btn_home);
        btn_newpost = findViewById(R.id.btn_newpost);
        btn_jobs = findViewById(R.id.btn_jobs);
        btn_account = findViewById(R.id.btn_account);
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
                Intent intent = new Intent(getApplicationContext(), JobsPosted.class);
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
    private void chooseCareer() {
        String[] options = {"Please select the position to be recruited", "Part-time", "Full-time", "Intern", "Casual", "Other..."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption[0] = options[position];
                if (position == 5) {
                    edt_career.setVisibility(View.VISIBLE);
                    selectedOption[0] = String.valueOf(edt_career.getText());

                    TextView textView22 = findViewById(R.id.textView22);
                    textView22.setTextColor(Color.GRAY);
                    textView22.setText("Position(*)");
                    checkError = 0;
                } else if (position == 0) {
                    checkError++;
                }

                else {
                    edt_career.setVisibility(View.GONE);
                    TextView textView22 = findViewById(R.id.textView22);
                    textView22.setTextColor(Color.GRAY);
                    textView22.setText("Position(*)");
                    checkError = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý sự kiện khi không có tùy chọn nào được chọn
            }
        });
    }

}
