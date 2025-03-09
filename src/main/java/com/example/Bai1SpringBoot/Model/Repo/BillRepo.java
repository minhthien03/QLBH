package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Bill;
import com.example.Bai1SpringBoot.Model.Entity.SanPham;
import com.example.Bai1SpringBoot.Model.Entity.User;

@Repository
public class BillRepo {
    public static ArrayList<Bill> getBillById(int id) throws Exception {
        ArrayList<Bill> billList = new ArrayList<>();
        UserRepo userRepo = new UserRepo();
        SanPhamRepo sanPhamRepo = new SanPhamRepo();
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
        BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from BILL where IDUser = ?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int bid = rs.getInt("IDBill");
            User idUser = userRepo.getUserById(rs.getInt("IDUser"));
            SanPham idSanPham = sanPhamRepo.getSanPhamByID(rs.getInt("IDSanPham"));
            int soLuong = rs.getInt("SoLuong");
            double tongTien = rs.getDouble("TongTien");
            Bill bill = new Bill(bid, idUser, idSanPham, soLuong, tongTien);
            billList.add(bill);
        }
        con.close();
        ps.close();
        rs.close();
        return billList;

    }

    // public Bill getBillById(int id) throws Exception {
    //     UserRepo userRepo = new UserRepo();
    //     SanPhamRepo sanPhamRepo = new SanPhamRepo();
    //     Class.forName(BaseConnection.nameClass);
    //     Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
    //             BaseConnection.password);
    //     PreparedStatement ps = con.prepareStatement("select * from BILL where IDBILL = ?");
    //     ps.setInt(1, id);
    //     ResultSet rs = ps.executeQuery();
    //     rs.next();
    //     int idBill = rs.getInt("IDBill");
    //     User idUser = userRepo.getUserById(rs.getInt("IDUser"));
    //     SanPham idSanPham = sanPhamRepo.getSanPhamByID(rs.getInt("IDSanPham"));
    //     int soLuong = rs.getInt("SoLuong");
    //     double tongTien = rs.getDouble("TongTien");
    //     Bill bill = new Bill(idBill, idUser, idSanPham, soLuong, tongTien);
    //     con.close();
    //     ps.close();
    //     rs.close();
    //     return bill;
    // }

    public static void addBill(Bill bill) throws Exception{
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("insert into BILL(IDUser,IDSanPham,SoLuong,TongTien) values(?,?,?,?)");
        ps.setInt(1,bill.getUser().getIdUser());
        ps.setInt(2 ,bill.getSanPham().getIdSanPham());
        ps.setInt(3 ,bill.getSoLuong());
        ps.setDouble(4 ,bill.getTongTien());
        ps.executeUpdate();
        con.close();
        ps.close();
    }
}
