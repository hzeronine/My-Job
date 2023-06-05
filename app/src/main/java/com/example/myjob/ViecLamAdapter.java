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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
        ViecLam viecLam = listViecLam.get(position);
        holder.txt_TieuDe.setText(viecLam.getTieuDe());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.txt_Ten.setText(viecLam.getTenCty());
        holder.txt_MucLuong.setText(viecLam.getMucLuong());
        holder.txt_ViTri.setText(viecLam.getViTri());
        holder.txt_ThoiHan.setText(viecLam.getThoiHan());
        holder.img_avt.setImageResource(viecLam.getHinhAnh());
    }

    @Override
    public int getItemCount() {
        return listViecLam.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt;
        TextView txt_TieuDe;
        TextView txt_Ten;
        TextView txt_MucLuong;
        TextView txt_ViTri;
        TextView txt_ThoiHan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            img_avt = itemView.findViewById(R.id.img_avt);
            txt_TieuDe = itemView.findViewById(R.id.txt_TieuDe);
            txt_Ten = itemView.findViewById(R.id.txt_Ten);
            txt_ViTri = itemView.findViewById(R.id.txt_ViTri);
            txt_MucLuong = itemView.findViewById(R.id.txt_MucLuong);
            txt_ThoiHan = itemView.findViewById(R.id.txt_ThoiHan);
        }
    }
}
