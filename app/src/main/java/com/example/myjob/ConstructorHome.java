package com.example.myjob;

public class ConstructorHome {

    private String company_Name;
    private String city;
    private String career;
    private String description;
    private String exp;
    private String salary;
    private String specialized;
    private String date;
    private int logo_URL;

    public ConstructorHome(String company_Name, String city, String career, String description, String exp, String salary, String specialized, String date, int logo_URL, boolean checked) {
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
