package com.example.jon_snow.tanyadokterhewan;

public class Users {
    private String image;
    private String display_name;
    private String status;

    public Users(){

    }
    public Users(String image, String display_name, String status) {
        this.image = image;
        this.display_name = display_name;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
