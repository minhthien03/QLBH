package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SanPham {
    private int idSanPham;
    private User User;
    private LoaiSanPham loaiSanPham;
    private double giaTien;
    private String tenSanPham;
    private int soLuong;
    private String img;
}
