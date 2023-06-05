package com.example.myjob;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViecLamAdapter extends RecyclerView.Adapter<ViecLamAdapter.ViewHolder> {
    Context context;
    ArrayList<ViecLam> listViecLam;

    public ViecLamAdapter(Context context, ArrayList<ViecLam> listViecLam)
    {
        this.context = context;
        this.listViecLam = listViecLam;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        ViecLam sanPham = listViecLam.get(position);
    }

    @Override
    public int getItemCount() {
        return listSanPham.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt;
        TextView txt_TieuDe;
        CheckBox ck_Delte;
        TextView txt_Ten;
        TextView txt_MucLuong;
        TextView txt_ViTri;
        TextView txt_ThoHan;
    }
        public ViewHolder(@NonNull View item_View) {
            super(item_View);
            // Ánh xạ view


        }
    }
}
