package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Bill {
    private int idBill;
    private User User;
    private SanPham sanPham;
    private int soLuong;
    private double tongTien;
}
