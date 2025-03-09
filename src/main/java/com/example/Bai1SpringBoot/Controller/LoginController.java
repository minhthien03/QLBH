package com.example.Bai1SpringBoot.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.SanPham;
import com.example.Bai1SpringBoot.Model.Entity.User;
import com.example.Bai1SpringBoot.Model.Repo.LoaiSanPhamRepo;
import com.example.Bai1SpringBoot.Model.Repo.LoginRepo;
import com.example.Bai1SpringBoot.Model.Repo.SanPhamRepo;
import com.example.Bai1SpringBoot.Model.Repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    LoginRepo loginRepo;

    @Autowired
    SanPhamRepo sanPhamRepo;

    @Autowired
    LoaiSanPhamRepo loaiSanPhamRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/showsignup")
    public String ShowSignUp() {
        return ("Public/signup");
    }

    @PostMapping("/singup")
    public String singUp(@RequestParam("ten") String ten,
            @RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("cpassword") String cpassword,
            @RequestParam("roleUser") String roleUser) throws Exception {
        if (password.equals(cpassword)) {
            User user = new User(ten, username, password, roleUser);
            userRepo.addNewUser(user);
            return "redirect:/";
        } else {
            return "Public/singup";
        }

    }

    @GetMapping("/login") // Đường dẫn Web
    public String ShowLogin() {
        return "Public/login"; // Trả về html

    }

    @GetMapping("/logout")
    public String Logout(HttpSession httpSession) {
        httpSession.removeAttribute("UserAfterLogin");
        return "redirect:/";
    }

    @PostMapping("/loginToSystem")
    public String CheckLoginUser(@RequestParam("Username1") String Username,
            @RequestParam("Password1") String Password, HttpSession httpSession) throws Exception {
        User user = loginRepo.CheckLoginUser(Username, Password);
        if (user == null) {
            return "Public/login";
        } else {
            httpSession.setAttribute("UserAfterLogin", user);
            return "redirect:/";
        }

    }

    @GetMapping("/")
    public String ShowIndex(Model model) throws Exception {
        ArrayList<SanPham> spList = sanPhamRepo.getAllSanPham();
        model.addAttribute("SanPhamList", spList);
        return "Public/index";
    }

}
