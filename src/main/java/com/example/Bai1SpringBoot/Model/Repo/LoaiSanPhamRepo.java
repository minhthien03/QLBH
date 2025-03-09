package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import com.example.Bai1SpringBoot.Model.Entity.LoaiSanPham;

@Repository
public class LoaiSanPhamRepo {
    public static ArrayList<LoaiSanPham> getAllLoaiSanPham() throws Exception {
        Class.forName(BaseConnection.nameClass);
        ArrayList<LoaiSanPham> allLoaiSanPham = new ArrayList<>();
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from LOAiSANPHAM");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("IDLoaiSanPham");
            String loaiSanPham = rs.getString("LoaiSanPham");
            LoaiSanPham lsp = new LoaiSanPham(id, loaiSanPham);
            allLoaiSanPham.add(lsp);
        }
        con.close();
        ps.close();
        rs.close();
        return allLoaiSanPham;
    }

    public LoaiSanPham getLoaiSanPhamById(int id) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from LOAISANPHAM where IDLoaiSanPham = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int idLoaiSanPham = rs.getInt("IDLoaiSanPham");
        String loaiSanPham = rs.getString("LoaiSanPham");
        LoaiSanPham lsp = new LoaiSanPham(idLoaiSanPham, loaiSanPham);
        ps.close();
        con.close();
        rs.close();
        return lsp;
    }

    public static void AddLoaiSanPham(LoaiSanPham loaiSanPham) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("insert into  LOAISANPHAM(LoaiSanPham) values (?)");
        ps.setString(1, loaiSanPham.getLoaiSanPham());
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public static void DeleteLoaiSanPhamByID(int id) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("Delete from LOAISANPHAM where idLoaiSanPham = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public static void UpdateLoaiSanPham(int id, String loaiSanPham) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update LOAISANPHAM set LoaiSanPham = ? where IDLoaiSanPham = ? ");
        ps.setString(1, loaiSanPham);
        ps.setInt(2, id);
        ps.executeUpdate();
        con.close();
        ps.close();
    }

    public LoaiSanPham getLoaiSanPhamByName(String LoaiSanPham) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from LOAISANPHAM where LoaiSanPham = ?");
        ps.setString(1, LoaiSanPham);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int idLoaiSanPham = rs.getInt("IDLoaiSanPham");
        String loaiSanPham = rs.getString("LoaiSanPham");
        LoaiSanPham lspham = new LoaiSanPham(idLoaiSanPham, loaiSanPham);
        ps.close();
        con.close();
        rs.close();
        return lspham;
    }
}
