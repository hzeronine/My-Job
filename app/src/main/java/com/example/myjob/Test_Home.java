package com.example.myjob;

public class Test_Home {
    private String tieuDe;
    private String diaChi;
    private String luong;
    private int hinhAnh;

    public Test_Home(String tieuDe, String diaChi, String luong, int hinhAnh) {
        this.tieuDe = tieuDe;
        this.diaChi = diaChi;
        this.luong = luong;
        this.hinhAnh = hinhAnh;
    }


    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLuong() {
        return luong;
    }

    public void setLuong(String luong) {
        this.luong = luong;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }


}
