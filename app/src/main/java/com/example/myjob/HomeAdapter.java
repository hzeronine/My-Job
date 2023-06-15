package com.example.myjob;

import static com.example.myjob.RandomStringGenerator.generateRandomString;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    Context context;
    ArrayList<BigData> bigData;
    ArrayList<List> list;
    FirebaseUser user;

    int numberCare = 0;
    FirebaseFirestore database_saved = FirebaseFirestore.getInstance();;
    public HomeAdapter(Context context, ArrayList<BigData> bigData)
    {
        this.context = context;
        this.bigData = bigData;


    }
    public void setFilteredList(ArrayList<BigData> filteredList) {
        this.bigData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        BigData constructor_home = bigData.get(position);


        int maxLength = 40; // Số ký tự tối đa bạn muốn hiển thị

        String originalText = constructor_home.getSpecialized();
        if (originalText.length() > maxLength) {
            String trimmedText = originalText.substring(0, maxLength) + "...";
            holder.txt_tieuDe.setText(trimmedText);
        } else {
            holder.txt_tieuDe.setText(constructor_home.getSpecialized());
        }
        holder.txt_company.setText(constructor_home.getCompany_Name());
        holder.txt_diaChi.setText(constructor_home.getCity());
        holder.txt_luong.setText(constructor_home.getSalary());
        holder.img_icon.setImageResource(constructor_home.getLogo_URL());
        //holder.btn_imgSave.setImageResource(R.drawable.icon_save);
        holder.txt_date.setText(constructor_home.getDate());
        holder.btn_imgSave.setImageResource(constructor_home.isChecked() ? R.drawable.click_ic_save : R.drawable.icon_save);
        holder.btn_imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID_saved = "";
                int numberCare = constructor_home.getNumberCare();
                if(constructor_home.isChecked()) {
                    // Xóa khỏi mục Saved
                    holder.btn_imgSave.setImageResource(R.drawable.icon_save);
                    constructor_home.setChecked(false);
                    String id = constructor_home.getId();
                    database_saved.collection("Saved").document(constructor_home.getId()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Xóa thành công
                                    Toast.makeText(context, "Unsaved your post", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xóa thất bại
                                    Toast.makeText(context, "Unsaved your post fail " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Giảm lượt tương tác

                    numberCare = numberCare;
                    //int count = Integer.valueOf(numberCare);
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Number_Care", numberCare);

                    // Cập nhật dữ liệu vào trường cụ thể trong tài liệu
                    String collectionName = "Post"; // Tên của bộ sưu tập chứa tài liệu
                    String documentId = constructor_home.getUID_posted(); // ID của tài liệu cần cập nhật
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

                } else {
                    ID_saved = generateRandomString();
                    constructor_home.setId(ID_saved);
                    holder.btn_imgSave.setImageResource(R.drawable.click_ic_save);
                    constructor_home.setChecked(true);
                    user = FirebaseAuth.getInstance().getCurrentUser();
//                    // Set vào Saved khi ấn Save
                    Calendar calendar = Calendar.getInstance();
                    String JID = String.valueOf(calendar.getTimeInMillis());
                    String logo_url = "jobs_logo/default/defaultLogo.png";
//                    String PID = RandomStringGenerator.LastFour(user.getUid()) + "_" + JID;
                    // Sign in success, update UI with the signed-in user's information
                    //Toast.makeText(DangBai.this, "Authentication succes.", Toast.LENGTH_SHORT).show();
                    Map<String, Object> MapDataSaved = new HashMap<>();
                    MapDataSaved.put("ID_Saved", ID_saved);
                    MapDataSaved.put("Company_Name", constructor_home.getCompany_Name());
                    MapDataSaved.put("Logo_URL", logo_url);
                    MapDataSaved.put("City", constructor_home.getCity());
                    MapDataSaved.put("Specialized",constructor_home.getSpecialized());
                    MapDataSaved.put("Career",constructor_home.getCareer());
                    MapDataSaved.put("Exp",constructor_home.getExp());
                    MapDataSaved.put("Salary",constructor_home.getSalary());
                    MapDataSaved.put("Description",constructor_home.getDescription());
//                    MapDataSaved.put("UID_Posted",user.getUid());
                    MapDataSaved.put("JID_need",JID);
                    MapDataSaved.put("Time",RandomStringGenerator.getCurrentDateTime());



                    database_saved.collection("Saved").document(ID_saved)
                            .set(MapDataSaved)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Saved the post successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Save post failed", Toast.LENGTH_SHORT).show();
                                }
                            });



                    numberCare = numberCare +1;
                    //int count = Integer.valueOf(numberCare);
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Number_Care", numberCare);

                    // Cập nhật dữ liệu vào trường cụ thể trong tài liệu
                    String collectionName = "Post"; // Tên của bộ sưu tập chứa tài liệu
                    String documentId = constructor_home.getUID_posted(); // ID của tài liệu cần cập nhật
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
//
    @Override
    public int getItemCount() {
        return bigData.size(); // trả item tại vị trí postion
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_icon;
        TextView txt_diaChi;
        TextView txt_tieuDe;
        TextView txt_luong;

        TextView txt_date;
        ImageButton btn_imgSave;

        TextView txt_company;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            img_icon = itemView.findViewById(R.id.img_icon);
            txt_diaChi = itemView.findViewById(R.id.txt_diaChi);
            txt_tieuDe = itemView.findViewById(R.id.txt_tieuDe);
            txt_luong = itemView.findViewById(R.id.txt_luong);
            txt_date = itemView.findViewById(R.id.txt_date);
            btn_imgSave = itemView.findViewById(R.id.btn_imgsave);
            txt_company = itemView.findViewById(R.id.txt_company);


        }

    }
    public void clearRecyclerView() {
        bigData.clear();
        notifyDataSetChanged();
    }

    void setdata() {


    }
}
