package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {
    private int idUser;
    private String ten;
    private int tuoi;
    private int sdt;
    private String email;
    private int cccd;
    private String diaChi;
    private String userName;
    private String password;
    private String roleUser;
    public User(String ten, String userName, String password, String roleUser) {
        this.ten = ten;
        this.userName = userName;
        this.password = password;
        this.roleUser = roleUser;
    }
}
