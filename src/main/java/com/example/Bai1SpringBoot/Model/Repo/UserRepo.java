package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.User;

@Repository
public class UserRepo {
    public ArrayList<User> getAllUser() throws Exception {
        ArrayList<User> allUser = new ArrayList<>();
        
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
        BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from `User`");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("IDUser"); 
            String ten = rs.getString("Ten");
            int tuoi = rs.getInt("Tuoi");
            int sdt = rs.getInt("SDT");
            int cccd = rs.getInt("CCCD");
            String diaChi = rs.getString("DiaChi");
            String userName = rs.getString("UserName");
            String password = rs.getString("Password");
            String roleUser = rs.getString("RoleUser");
            User user = new User(id, ten, tuoi, sdt, ten, cccd, diaChi, userName, password, roleUser);
            allUser.add(user);
        }
        ps.close();
        con.close();
        rs.close();
        return allUser;
    }

    public User getUserById(int id ) throws Exception{
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
        BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("select * from `User` where IDUser = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int idUser = rs.getInt("IDUser"); 
        String ten = rs.getString("Ten");
        int tuoi = rs.getInt("Tuoi");
        int sdt = rs.getInt("SDT");
        int cccd = rs.getInt("CCCD");
        String diaChi = rs.getString("DiaChi");
        String userName = rs.getString("UserName");
        String password = rs.getString("Password");
        String roleUser = rs.getString("RoleUser");
        User user = new User(idUser, ten, tuoi, sdt, ten, cccd, diaChi, userName, password, roleUser);
        ps.close();
        con.close();
        rs.close();
        return user;
    }
    public static void addNewUser(User user) throws Exception {
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
        BaseConnection.password);
        PreparedStatement ps = con.prepareStatement(
            "insert into `User`(Ten,Tuoi,SDT,Email,CCCD,DiaChi,UserName,`Password`,RoleUser) values (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, user.getTen());
        ps.setInt(2, user.getTuoi());
        ps.setInt(3,user.getSdt());
        ps.setString(4,user.getEmail());
        ps.setInt(5, user.getCccd());
        ps.setString(6,user.getDiaChi());
        ps.setString(7, user.getUserName());
        ps.setString(8,user.getPassword());
        ps.setString(9, user.getRoleUser());
        ps.executeUpdate();
        con.close();
        ps.close();
    }
    public static void DeleteUserByID(int id) throws Exception{
        Class.forName(BaseConnection.nameClass);
        Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
        BaseConnection.password);
        PreparedStatement ps = con.prepareStatement("Delete from User where idUser = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
        ps.close();
    }
    public static void UpdateUserByID(int id , String ten, int tuoi, int sdt, String email, int CCCD, String DiaChi) throws Exception{
    Class.forName(BaseConnection.nameClass);
    Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
    BaseConnection.password);
    PreparedStatement ps = con.prepareStatement(
        "update `User` set Ten=?,Tuoi=?,SDT=?,Email=?,CCCD=?,DiaChi=? where IDUser = ?");
    ps.setString(1, ten);
    ps.setInt(2, tuoi);
    ps.setInt(3,sdt);
    ps.setString(4,email);
    ps.setInt(5, CCCD);
    ps.setString(6,DiaChi);
    ps.setInt(7, id);
    ps.executeUpdate();
    con.close();
    ps.close();
    }
}
