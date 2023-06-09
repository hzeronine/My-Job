package com.example.myjob;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViecLamAdapter extends RecyclerView.Adapter<ViecLamAdapter.ViewHolder> {
    Context context;
    ArrayList<ViecLam> listViecLam;
    ArrayList<ViecLam> filteredList;
    ArrayList<BigData> listBigdata;
    private boolean isAllSelected;
    boolean isFilterApplied = false;

    BigData data;

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
        this.listBigdata = new ArrayList<>();
    }
    public ViecLamAdapter(Context context, ArrayList<ViecLam> listViecLam, ArrayList<BigData> listBigdata)
    {
        this.context = context;
        this.listViecLam = listViecLam;
        this.listBigdata = listBigdata;
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

            if(listBigdata.size() > 1)
                data = listBigdata.get(position);
            ViecLam viecLam = listViecLam.get(position);
            holder.txt_TieuDe.setText(viecLam.getTieuDe());
            holder.txt_Ten.setText(viecLam.getTenCty());
            holder.txt_MucLuong.setText(viecLam.getMucLuong());
            holder.txt_ViTri.setText(viecLam.getViTri());
            holder.txt_ThoiHan.setText(viecLam.getThoiHan());
//            Picasso.get()
//                    .load(viecLam.getHinhAnh())
//                    .into(holder.img_avt);
            holder.img_avt.setImageResource(R.drawable.img_1);
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
            if (data != null)
                holder.rowItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickGoToDetail(data);
                    }
                });
        }
    }

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
        if (isFilterApplied) {
            // Nếu bộ lọc tìm kiếm đã được áp dụng, sử dụng filteredItems làm danh sách dữ liệu
            return filteredList.size();
        } else {
            // Nếu bộ lọc tìm kiếm chưa được áp dụng, sử dụng items làm danh sách dữ liệu
            return listViecLam.size();
        }
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
        for(ViecLam item: listViecLam) {
            isAllSelected = false;
        }
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox ck_Delete;
        ConstraintLayout rowItem;
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
