package com.example.Bai1SpringBoot.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.LoaiSanPham;
import com.example.Bai1SpringBoot.Model.Entity.SanPham;
import com.example.Bai1SpringBoot.Model.Entity.User;
import com.example.Bai1SpringBoot.Model.Repo.LoaiSanPhamRepo;
import com.example.Bai1SpringBoot.Model.Repo.SanPhamRepo;
import com.example.Bai1SpringBoot.Model.Repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class SanPhamController {
    @Autowired
    SanPhamRepo sanPhamRepo ;

    @Autowired
    UserRepo userRepo ;

    @Autowired      
    LoaiSanPhamRepo loaiSanPhamRepo ;


    
    
    @GetMapping("/ShowAddSanPham")
    public String ShowAddSanPham(Model model) throws Exception {
        ArrayList<LoaiSanPham> listLoaiSanPham = loaiSanPhamRepo.getAllLoaiSanPham();
        model.addAttribute("ListLoaiSanPham", listLoaiSanPham);
        return "SanPham/addsanpham"; // Trả về html

    }
    @GetMapping("/ViewDeteil/{id}")
    public String ViewDeteil(@PathVariable("id")int id,Model model) throws Exception{
        SanPham sanPham =  sanPhamRepo.getSanPhamByID(id);
        model.addAttribute("SanPhamDeteil",sanPham);
        return "Public/viewDeteil";
    }
    @PostMapping("/addsanphams")
    public String AddSanPham(@RequestParam("IdLoaiSanPham1") int idLoaiSanPham,
            @RequestParam("GiaTien1") double giaTien, @RequestParam("TenSanPham1") String tenSanPham,
            @RequestParam("SoLuong1") int soLuong, @RequestParam("IMG") String img, HttpSession httpSession) throws Exception {
        User user = (User) httpSession.getAttribute("UserAfterLogin");
        LoaiSanPham loaiSanPham = loaiSanPhamRepo.getLoaiSanPhamById(idLoaiSanPham);
        SanPham addSanPham = new SanPham(0, user, loaiSanPham, giaTien, tenSanPham, soLuong, img);
        sanPhamRepo.AddSanPham(addSanPham);
        return "redirect:/";

    }
    @GetMapping("/ShowEdit/{id}")
    public String ShowEdit(Model model , @PathVariable("id") int idSanPham ) throws Exception{
        ArrayList<LoaiSanPham> listLoaiSanPham = loaiSanPhamRepo.getAllLoaiSanPham();
        model.addAttribute("ListLoaiSanPham", listLoaiSanPham);
        SanPham sanPham = sanPhamRepo.getSanPhamByID(idSanPham);
        model.addAttribute("SanPham", sanPham);
        return "SanPham/editsanpham";
    }
    @PostMapping("/editsanpham")
    public String editSanpham(@RequestParam("IdLoaiSanPham1") int idLoaiSanPham,
            @RequestParam("GiaTien1") double giaTien, @RequestParam("TenSanPham1") String tenSanPham,
            @RequestParam("SoLuong1")int soLuong, @RequestParam("IMG")String img,@RequestParam("IdSanPham1") int idSanPham ,HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("UserAfterLogin");
        LoaiSanPham loaiSanPham = loaiSanPhamRepo.getLoaiSanPhamById(idLoaiSanPham);
        SanPham sanPham = new SanPham(idSanPham, user, loaiSanPham, giaTien, tenSanPham, soLuong, img);
        sanPhamRepo.UpdateSanPham(sanPham);
        return"redirect:/";
    }
    @GetMapping("/searchSanPham")
    public String searchSanPham(@RequestParam("search") String search, Model model) throws Exception{
        ArrayList<SanPham> spList = sanPhamRepo.getAllSanPham();
        ArrayList<SanPham> findSanPham = new ArrayList<>();
        for (SanPham sanPham : spList) {
            if(sanPham.getTenSanPham().toLowerCase().contains(search.toLowerCase())){
                findSanPham.add(sanPham);
            }
        }
        model.addAttribute("SanPhamList", findSanPham); 
        return"Public/index";
    }


    

}
