package com.example.myjob;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class ViecLam {
    private boolean isSelected;
    private String tieuDe;

    private int hinhAnh;
    private String tenCty;
    private String mucLuong;
    private String thoiHan;

    private String viTri;

    private String ID;

    public ViecLam(String ID,String tieuDe, String tenCty, String mucLuong, String viTri, String thoiHan, int hinhAnh)
    {
        this.ID = ID;
        this.tieuDe = tieuDe;
        this.tenCty = tenCty;
        this.mucLuong = mucLuong;
        this.viTri = viTri;
        this.thoiHan = thoiHan;
        this.hinhAnh = hinhAnh;
        this.isSelected = false;
    }
    public String getTieuDe() {
        return tieuDe;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public String getTenCty() {
        return tenCty;
    }

    public String getMucLuong() {
        return mucLuong;
    }

    public String getThoiHan() {
        return "Còn " + thoiHan + " ngày để ứng tuyển";
    }
    public String getViTri() {
        return viTri;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setTenCty(String tenCty) {
        this.tenCty = tenCty;
    }

    public void setMucLuong(String mucLuong) {
        this.mucLuong = mucLuong;
    }

    public void setThoiHan(String thoiHan) {
        this.thoiHan = thoiHan;
    }


}
