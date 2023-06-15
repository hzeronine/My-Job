package com.example.myjob;

public class BigData {
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

    public String getJID_need() {
        return JID_need;
    }

    public void setJID_need(String JID_need) {
        this.JID_need = JID_need;
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

    private String JID_need;
    private int numberCare;
    private String titile;
    private String UID_posted;
    private int logo_URL;

    public BigData(String JID_need, int numberCare, String titile, String UID_posted, String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, int logo_URL, boolean checked) {
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
        this.JID_need = JID_need;
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

    public int getLogo_URL() {
        return logo_URL;
    }

    public void setLogo_URL(int logo_URL) {
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
