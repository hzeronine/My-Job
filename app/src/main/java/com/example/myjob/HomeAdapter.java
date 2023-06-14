package com.example.myjob;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    Context context;
    ArrayList<ConstructorHome> constructor_homes;

    ArrayList<ConstructorHome> list_jobs;
    RecyclerView recyclerView;
    int hind = 0;

    public HomeAdapter(Context context, ArrayList<ConstructorHome> constructor_homes)
    {
        this.context = context;
        this.constructor_homes = constructor_homes;
    }
    public void setFilteredList(ArrayList<ConstructorHome> filteredList) {
        this.constructor_homes = filteredList;
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
        ConstructorHome constructor_home = constructor_homes.get(position);


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
                if(constructor_home.isChecked()) {
                    holder.btn_imgSave.setImageResource(R.drawable.icon_save);
                    constructor_home.setChecked(false);
                } else {
                    holder.btn_imgSave.setImageResource(R.drawable.click_ic_save);
                    constructor_home.setChecked(true);
                }
            }
        });
    }
//
    @Override
    public int getItemCount() {
        return constructor_homes.size(); // trả item tại vị trí postion
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
        constructor_homes.clear();
        notifyDataSetChanged();
    }
}
