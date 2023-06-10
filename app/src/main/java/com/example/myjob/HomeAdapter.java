package com.example.myjob;

import android.content.Context;

import android.os.Bundle;
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
    ArrayList<Test_Home> test_homes;
    RecyclerView recyclerView;

    public HomeAdapter(Context context, ArrayList<Test_Home> test_homes)
    {
        this.context = context;
        this.test_homes = test_homes;
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
        Test_Home test_home = test_homes.get(position);
        holder.txt_tieuDe.setText(test_home.getTieuDe());
        holder.txt_diaChi.setText(test_home.getDiaChi());
        holder.txt_luong.setText(test_home.getLuong());
        holder.img_icon.setImageResource(test_home.getHinhAnh());
        holder.btn_imgSave.setImageResource(R.drawable.icon_save);
    }
//
    @Override
    public int getItemCount() {
        return test_homes.size(); // trả item tại vị trí postion
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_icon;
        TextView txt_diaChi;
        TextView txt_tieuDe;
        TextView txt_luong;
        ImageButton btn_imgSave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            img_icon = itemView.findViewById(R.id.img_icon);
            txt_diaChi = itemView.findViewById(R.id.txt_diaChi);
            txt_tieuDe = itemView.findViewById(R.id.txt_tieuDe);
            txt_luong = itemView.findViewById(R.id.txt_luong);
            btn_imgSave = itemView.findViewById(R.id.btn_imgsave);
        }
    }
}
