package com.example.myjob;

import java.io.Serializable;

public class BigData implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String company_Name;
    private String city;
    private String career;
    private String description;
    private String exp;
    private String salary;
    private String specialized;
    private String date;

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public int getNumberCare() {
        return numberCare;
    }

    public void setNumberCare(int numberCare) {
        this.numberCare = numberCare;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getUID_posted() {
        return UID_posted;
    }

    public void setUID_posted(String UID_posted) {
        this.UID_posted = UID_posted;
    }

    private String PID;
    private int numberCare;
    private String titile;
    private String UID_posted;
    private String logo_URL;

    public BigData() {
        this.company_Name = "";
        this.city = "";
        this.career = "";
        this.description = "";
        this.exp = "";
        this.salary = "";
        this.specialized = "";
        this.date = "";
        this.logo_URL = "";
        this.checked = false;
        this.PID = "";
        this.numberCare= 0;
        this.titile = "";
        this.UID_posted = "";
    }
    public BigData(String PID, int numberCare, String titile, String UID_posted, String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, String logo_URL, boolean checked) {
        this.company_Name = company_Name;
        this.city = city;
        this.career = career;
        this.description = description;
        this.exp = exp;
        this.salary = salary;
        this.specialized = specialized;
        this.date = date;
        this.logo_URL = logo_URL;
        this.checked = checked;
        this.PID = PID;
        this.numberCare= numberCare;
        this.titile = titile;
        this.UID_posted = UID_posted;
    }

    boolean checked;

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getLogo_URL() {
        return logo_URL;
    }

    public void setLogo_URL(String logo_URL) {
        this.logo_URL = logo_URL;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


}
