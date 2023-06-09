package com.example.myjob;

import static com.example.myjob.RandomStringGenerator.generateRandomString;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    Context context;
    ArrayList<BigData> bigData;
    ArrayList<List> list;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore database_saved = FirebaseFirestore.getInstance();

    ArrayList<String> listID_saved = listID_saved = new ArrayList<>();
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
            holder.txt_tieuDe.setText(constructor_home.getTitile());
        }
        holder.txt_company.setText(constructor_home.getCompany_Name());
        holder.txt_diaChi.setText(constructor_home.getCity());
        holder.txt_luong.setText(constructor_home.getSalary());
        //holder.img_icon.setImageResource(constructor_home.getLogo_URL());
        Picasso.get()
                .load(constructor_home.getLogo_URL())
                .into(holder.img_icon);

        //holder.btn_imgSave.setImageResource(R.drawable.icon_save);
        holder.txt_date.setText(constructor_home.getDate());
        holder.btn_imgSave.setImageResource(constructor_home.isChecked() ? R.drawable.click_ic_save : R.drawable.icon_save);
        holder.btn_imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numberCare = constructor_home.getNumberCare();
                if(constructor_home.isChecked()) {
                    // Xóa khỏi mục Saved
                    holder.btn_imgSave.setImageResource(R.drawable.icon_save);
                    constructor_home.setChecked(false);
                    String id = constructor_home.getId();
                    database_saved.collection("Saved").document(user.getUid())
                            .update("Post_Saved", FieldValue.arrayRemove(constructor_home.getPID()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Unsaved", Toast.LENGTH_SHORT).show();
                                }
                            });
                    // Giảm lượt tương tác
                    numberCare = numberCare - 1;
                    constructor_home.setNumberCare(numberCare);
                    // Tạo Map Update dữ liệu
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Number_Care", numberCare);

                    // Cập nhật dữ liệu vào trường cụ thể trong tài liệu
                    String collectionName = "Post";
                    String documentId = constructor_home.getUID_posted();
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

                    holder.btn_imgSave.setImageResource(R.drawable.click_ic_save);
                    constructor_home.setChecked(true);

                    //Truy cập database Saved
                    database_saved.collection("Saved").document(user.getUid())
                            .update("Post_Saved", FieldValue.arrayUnion(constructor_home.getPID()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Tăng tương tác post
                    numberCare = numberCare +1;
                    constructor_home.setNumberCare(numberCare);
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

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(constructor_home);
            }
        });

    }
//
    private void onClickGoToDetail(BigData constructor_home) {
        Intent intent = new Intent(context, JobDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_bigData", constructor_home);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
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

        LinearLayout layout_item;


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
            layout_item = itemView.findViewById(R.id.layout_item);

        }

    }
    public void clearRecyclerView() {
        bigData.clear();
        notifyDataSetChanged();
    }

}
