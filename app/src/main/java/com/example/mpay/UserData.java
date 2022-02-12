package com.example.mpay;

public class UserData
{
   private String name,phone,email,imageUrl;

    public UserData() {
    }

    public UserData(String name, String phone, String email, String imageUrl) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
