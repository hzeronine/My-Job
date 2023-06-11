package com.example.myjob;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;


public class ViecLamAdapter extends RecyclerView.Adapter<ViecLamAdapter.ViewHolder> {
    Context context;
    ArrayList<ViecLam> listViecLam;
    ArrayList<ViecLam> filteredList;

    private boolean isAllSelected;




    public ArrayList<ViecLam> getSelectedItems() {
        ArrayList<ViecLam> selectedItems = new ArrayList<>();
        for (ViecLam item : listViecLam) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
    public interface OnCheckBoxListener {
        void onCheckBoxClick(int position, boolean isChecked);
    }

    private OnCheckBoxListener listener;

    public void setOnCheckBoxListener(OnCheckBoxListener listener) {
        this.listener = listener;
    }

    public ViecLamAdapter(Context context, ArrayList<ViecLam> listViecLam)
    {
        this.context = context;
        this.listViecLam = listViecLam;
    }

    public void setFilteredList(ArrayList<ViecLam> filteredList)
    {
        this.listViecLam = filteredList;
        notifyDataSetChanged();
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
        if (listViecLam != null && !listViecLam.isEmpty() && position < listViecLam.size()) {
            ViecLam viecLam = listViecLam.get(position);
            holder.txt_TieuDe.setText(viecLam.getTieuDe());
            holder.txt_Ten.setText(viecLam.getTenCty());
            holder.txt_MucLuong.setText(viecLam.getMucLuong());
            holder.txt_ViTri.setText(viecLam.getViTri());
            holder.txt_ThoiHan.setText(viecLam.getThoiHan());
            holder.img_avt.setImageResource(viecLam.getHinhAnh());
            holder.img_Luong.setImageResource(R.drawable.img_3);
            holder.img_ViTri.setImageResource(R.drawable.img_4);
            holder.ck_Delete.setOnCheckedChangeListener(null);
            holder.ck_Delete.setChecked(viecLam.isSelected());
            holder.ck_Delete.setOnCheckedChangeListener((CheckBox, isChecked) -> {
                viecLam.setSelected(isChecked);
            });
            holder.ck_Delete.setChecked(viecLam.isSelected() || isAllSelected);
            holder.ck_Delete.setOnClickListener(v -> {
                viecLam.setSelected(holder.ck_Delete.isChecked());
            });
        }
    }

    @Override
    public int getItemCount() {
        return listViecLam.size(); // trả item tại vị trí postion
    }
    public void removeItem(int position) {
        listViecLam.remove(position);
        filteredList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filteredList.size());
    }
    public void selectAll() {
        isAllSelected = true;
        notifyDataSetChanged();
    }
    public void deselectAll() {
        isAllSelected = false;
        for (ViecLam item : listViecLam) {
            item.setSelected(false);
        }
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox ck_Delete;
        LinearLayout rowItem;
        ImageView img_avt;
        TextView txt_TieuDe;
        TextView txt_Ten;
        TextView txt_MucLuong;
        TextView txt_ViTri;
        TextView txt_ThoiHan;

        ImageView img_Luong;

        ImageView img_ViTri;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            img_avt = itemView.findViewById(R.id.img_avt);
            txt_TieuDe = itemView.findViewById(R.id.txt_TieuDe);
            txt_Ten = itemView.findViewById(R.id.txt_Ten);
            txt_ViTri = itemView.findViewById(R.id.txt_ViTri);
            txt_MucLuong = itemView.findViewById(R.id.txt_MucLuong);
            txt_ThoiHan = itemView.findViewById(R.id.txt_ThoiHan);
            img_Luong = itemView.findViewById(R.id.imageView_Luong);
            img_ViTri = itemView.findViewById(R.id.imageView_ViTri);
            rowItem = itemView.findViewById(R.id.rowItem);
            ck_Delete = itemView.findViewById(R.id.ck_Delete);

        }
    }
    
}
