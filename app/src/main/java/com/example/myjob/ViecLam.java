package com.example.myjob;

public class ViecLam {
    private String tieuDe;

    private int hinhAnh;
    private String tenCty;
    private String mucLuong;
    private String thoiHan;

    public ViecLam(String tieuDe, String tenCty, String mucLuong, String thoiHan, int hinhAnh)
    {
        this.tieuDe = tieuDe;
        this.tenCty = tenCty;
        this.mucLuong = mucLuong;
        this.thoiHan = thoiHan;
        this.hinhAnh = hinhAnh;
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
        return thoiHan;
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
