package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.LoaiSanPham;
import com.example.Bai1SpringBoot.Model.Entity.SanPham;
import com.example.Bai1SpringBoot.Model.Entity.User;

@Repository
public class SanPhamRepo {
  public static ArrayList<SanPham> getAllSanPham() throws Exception {
        ArrayList<SanPham> allSanPham = new ArrayList<>();
        UserRepo userRepo = new UserRepo();
        LoaiSanPhamRepo loaiSanPhamRepo = new LoaiSanPhamRepo();
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
        BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from SANPHAM");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("IDSanPham");
            User idUser = userRepo.getUserById(rs.getInt("IDUser"));
            LoaiSanPham idLoaiSanPham = loaiSanPhamRepo.getLoaiSanPhamById(rs.getInt("IDLoaiSanPham"));
            double giaTien = rs.getDouble("GiaTien");
            String ten = rs.getString("TenSanPham");
            int soLuong = rs.getInt("SoLuong");
            String img = rs.getString("img");
            SanPham sp = new SanPham(id, idUser, idLoaiSanPham, giaTien, ten, soLuong,img);
            allSanPham.add(sp);

        }
        con.close();
        ps.close();
        rs.close();
        return allSanPham;
    }

    public SanPham getSanPhamByID(int id) throws Exception {
        UserRepo userRepo = new UserRepo();
        LoaiSanPhamRepo loaiSanPhamRepo = new LoaiSanPhamRepo();
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from SANPHAM where IDSanPham = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int idSanPham = rs.getInt("IDSanPham");
        User idUser = userRepo.getUserById(rs.getInt("IDUser"));
        LoaiSanPham idLoaiSanPham = loaiSanPhamRepo.getLoaiSanPhamById(rs.getInt("IDLoaiSanPham"));
        double giaTien = rs.getDouble("GiaTien");
        String ten = rs.getString("TenSanPham");
        int soLuong = rs.getInt("SoLuong");
        String img = rs.getString("img");
        SanPham sPham = new SanPham(idSanPham, idUser, idLoaiSanPham, giaTien, ten, soLuong,img);
        con.close();
        ps.close();
        rs.close();
        return sPham;
    }

    public static void AddSanPham(SanPham sanPham) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "insert into SANPHAM(IDUser,IDLoaiSanPham,GiaTien,TenSanPham,SoLuong,img) values (?,?,?,?,?,?)");
        ps.setInt(1, sanPham.getUser().getIdUser());
        ps.setInt(2, sanPham.getLoaiSanPham().getIdLoaiSanPham());
        ps.setDouble(3, sanPham.getGiaTien());
        ps.setString(4, sanPham.getTenSanPham());
        ps.setInt(5, sanPham.getSoLuong());
        ps.setString(6, sanPham.getImg());
        ps.executeUpdate();
        con.close();
        ps.close();

    }

    public static void DeleteSanPhamByID(int id) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("Delete from SANPHAM where idSanPham = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    public static void UpdateSanPham(SanPham sanPham) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update SANPHAM set IDLoaiSanPham=? ,GiaTien=? ,TenSanPham=? ,SoLuong=?, img=? where IDSanPham = ?");
        ps.setInt(1, sanPham.getLoaiSanPham().getIdLoaiSanPham());
        ps.setDouble(2, sanPham.getGiaTien());
        ps.setString(3, sanPham.getTenSanPham());
        ps.setInt(4, sanPham.getSoLuong());
        ps.setString(5 , sanPham.getImg());
        ps.setInt(6, sanPham.getIdSanPham());
        ps.executeUpdate();
        con.close();
        ps.close();     
    }
    public SanPham getSanPhamByName( String TenSanPham) throws Exception{
        UserRepo userRepo = new UserRepo();
        LoaiSanPhamRepo loaiSanPhamRepo = new LoaiSanPhamRepo();
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from SANPHAM where TenSanPham = ?");
        ps.setString(1, TenSanPham);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int idSanPham = rs.getInt("IDSanPham");
        User idUser = userRepo.getUserById(rs.getInt("IDUser"));
        LoaiSanPham idLoaiSanPham = loaiSanPhamRepo.getLoaiSanPhamById(rs.getInt("IDLoaiSanPham"));
        double giaTien = rs.getDouble("GiaTien");
        String ten = rs.getString("TenSanPham");
        int soLuong = rs.getInt("SoLuong");
        String img = rs.getString("img");
        SanPham spham = new SanPham(idSanPham, idUser, idLoaiSanPham, giaTien, ten, soLuong,img);
        con.close();
        ps.close();
        rs.close();
        return spham;
    }
    public static void UpdateSanPhamSauKhiMua(int id,  int soLuongMoi) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update SANPHAM set SoLuong= ? where IDSanPham = ?");
        ps.setInt(1, soLuongMoi);
        ps.setInt(2, id);
        ps.executeUpdate();
        con.close();
        ps.close();
    }

}
