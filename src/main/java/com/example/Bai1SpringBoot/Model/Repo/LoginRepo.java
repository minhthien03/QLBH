package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.User;

@Repository
public class LoginRepo {
    public User CheckLoginUser (String userName , String passWord) throws Exception{
        Class.forName(BaseConnection.nameClass);
    Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
    BaseConnection.password);
    PreparedStatement ps = con.prepareStatement(
        "select * from `User` where UserName = ? and `Password` = ?");
        ps.setString(1, userName);
        ps.setString(2, passWord);
        ResultSet rs = ps.executeQuery();
       if ( rs.next()) {
        int idUser = rs.getInt("IDUser"); 
        String ten = rs.getString("Ten");
        int tuoi = rs.getInt("Tuoi");
        int sdt = rs.getInt("SDT");
        int cccd = rs.getInt("CCCD");
        String diaChi = rs.getString("DiaChi");
        String roleUser = rs.getString("RoleUser");
        User userCheckLogin = new User(idUser, ten, tuoi, sdt, ten, cccd, diaChi, userName, passWord, roleUser);
        ps.close();
        con.close();
        rs.close();
        return userCheckLogin;
       }
        return null;

    }
}
