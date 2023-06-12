package com.example.myjob;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;

public class JobsPostedAdapter extends RecyclerView.Adapter<JobsPostedAdapter.ViewHolder> {
    Context context;
    ArrayList<JobsPostedItem> listJobsPostedItem;
    ArrayList<JobsPostedItem> filteredList;
    private boolean isAllSelected;
    boolean isFilterApplied = false;

    public ArrayList<JobsPostedItem> getSelectedItems() {
        ArrayList<JobsPostedItem> selectedItems = new ArrayList<>();
        for (JobsPostedItem item : listJobsPostedItem) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
    public interface OnCheckBoxListener {
        void onCheckBoxClick(int position, boolean isChecked);
    }

    private JobsPostedAdapter.OnCheckBoxListener listener;

    public void setOnCheckBoxListener(JobsPostedAdapter.OnCheckBoxListener listener) {
        this.listener = listener;
    }

    public JobsPostedAdapter(Context context, ArrayList<JobsPostedItem> listJobsPostedItem)
    {
        this.context = context;
        this.listJobsPostedItem = listJobsPostedItem;
    }

    public void setFilteredList(ArrayList<JobsPostedItem> filteredList)
    {
        this.listJobsPostedItem = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobsPostedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new JobsPostedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsPostedAdapter.ViewHolder holder, int position) {
        // Gán dữ liêu
        if (listJobsPostedItem != null && !listJobsPostedItem.isEmpty() && position < listJobsPostedItem.size()) {
            JobsPostedItem jobsPostedItem = listJobsPostedItem.get(position);
            holder.txt_TieuDe_JP.setText(jobsPostedItem.getTieuDe());
            holder.txt_Ten.setText(jobsPostedItem.getTenCty());
            holder.txt_MucLuong.setText(jobsPostedItem.getMucLuong());
            holder.txt_ViTri.setText(jobsPostedItem.getViTri());
            holder.txt_ThoiHan.setText(jobsPostedItem.getThoiHan());
            holder.img_avt.setImageResource(jobsPostedItem.getHinhAnh());
            holder.img_Luong.setImageResource(R.drawable.img_3);
            holder.img_ViTri.setImageResource(R.drawable.img_4);
            holder.ck_Delete.setOnCheckedChangeListener(null);
            holder.ck_Delete.setChecked(jobsPostedItem.isSelected());
            holder.ck_Delete.setOnCheckedChangeListener((CheckBox, isChecked) -> {
                jobsPostedItem.setSelected(isChecked);
            });
            holder.ck_Delete.setChecked(jobsPostedItem.isSelected() || isAllSelected);
            holder.ck_Delete.setOnClickListener(v -> {
                jobsPostedItem.setSelected(holder.ck_Delete.isChecked());
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isFilterApplied) {
            // Nếu bộ lọc tìm kiếm đã được áp dụng, sử dụng filteredItems làm danh sách dữ liệu
            return filteredList.size();
        } else {
            // Nếu bộ lọc tìm kiếm chưa được áp dụng, sử dụng items làm danh sách dữ liệu
            return listJobsPostedItem.size();
        }
    }
    public void removeItem(int position) {
        listJobsPostedItem.remove(position);
        filteredList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filteredList.size());
    }
    public void selectAll() {
        isAllSelected = true;
        notifyDataSetChanged();
    }
    public void deselectAll() {
        for(JobsPostedItem item: listJobsPostedItem) {
            isAllSelected = false;
        }
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox ck_Delete;
        ConstraintLayout rowItem;
        ImageView img_avt;
        TextView txt_TieuDe_JP;
        TextView txt_Ten;
        TextView txt_MucLuong;
        TextView txt_ViTri;
        TextView txt_ThoiHan;
        ImageView img_Luong;
        ImageView img_ViTri;
        public ViewHolder(@NonNull View view) {
            super(view);
            // Ánh xạ view
            img_avt = view.findViewById(R.id.img_avt);
            txt_TieuDe_JP = view.findViewById(R.id.txt_TieuDe);
            txt_Ten = view.findViewById(R.id.txt_Ten);
            txt_ViTri = view.findViewById(R.id.txt_ViTri);
            txt_MucLuong = view.findViewById(R.id.txt_MucLuong);
            txt_ThoiHan = view.findViewById(R.id.txt_ThoiHan);
            img_Luong = view.findViewById(R.id.imageView_Luong);
            img_ViTri = view.findViewById(R.id.imageView_ViTri);
            rowItem = view.findViewById(R.id.rowItem);
            ck_Delete = view.findViewById(R.id.ck_Delete);
        }
    }
}
