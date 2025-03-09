package com.example.Bai1SpringBoot.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.Bill;
import com.example.Bai1SpringBoot.Model.Entity.SanPham;
import com.example.Bai1SpringBoot.Model.Entity.User;
import com.example.Bai1SpringBoot.Model.Repo.BillRepo;
import com.example.Bai1SpringBoot.Model.Repo.SanPhamRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class OderController {
    @Autowired
    BillRepo billRepo;
    @Autowired
    SanPhamRepo sanPhamRepo;

    ArrayList<SanPham> cartList = new ArrayList<>();

    @GetMapping("/ShowOder/{id}")
    public String showoder(@PathVariable("id") int id, Model model) throws Exception {
        SanPham sanPham = sanPhamRepo.getSanPhamByID(id);
        model.addAttribute("SanPhamOrder", sanPham);
        return "Oder/showoder";
    }

    @PostMapping("/OderSanPham")
    public String oderSanPham(@RequestParam("IdSanPham1") int id, @RequestParam("SoLuong1") int soLuong,
            HttpSession httpSession) throws Exception {
        User user = (User) httpSession.getAttribute("UserAfterLogin");
        SanPham sanPham = sanPhamRepo.getSanPhamByID(id);
        double tongTien = sanPham.getGiaTien() * soLuong;
        int newSoLuong = sanPham.getSoLuong() - soLuong;
        Bill bill = new Bill(0, user, sanPham, soLuong, tongTien);
        sanPhamRepo.UpdateSanPhamSauKhiMua(id, newSoLuong);
        billRepo.addBill(bill);
        return "redirect:/";
    }

    @GetMapping("/ShowBillByUserId")
    public String ShowBillByUserId(HttpSession httpSession, Model model) throws Exception {
        User user = (User) httpSession.getAttribute("UserAfterLogin");
        ArrayList<Bill> billList = billRepo.getBillById(user.getIdUser());
        model.addAttribute("OderList", billList);
        return "Oder/showoderbyuserid";

    }

    @GetMapping("/AddToCart/{id}")
    public String addToCart(@PathVariable("id") int id, HttpSession httpSession) throws Exception {
        SanPham sanPham = sanPhamRepo.getSanPhamByID(id);
        for (SanPham s : cartList) {
            if (s.getIdSanPham() == sanPham.getIdSanPham()) {
                s.setSoLuong(s.getSoLuong() + 1);
                return "redirect:/";
            }
        }
        sanPham.setSoLuong(1);
        cartList.add(sanPham);
        httpSession.setAttribute("CartList", cartList);
        return "redirect:/";
    }

    @GetMapping("ShowCart")
    public String showCart(HttpSession httpSession, Model model) {
        ArrayList<SanPham> sList = (ArrayList<SanPham>) httpSession.getAttribute("CartList");
        
        if (sList == null) {
            sList = new ArrayList<>();
            model.addAttribute("CartListModel", null);
        }else{
            model.addAttribute("CartListModel", sList); 
        }
        double tongTienAllSanPham = 0;
        for (SanPham sanPham : sList) {
            tongTienAllSanPham = tongTienAllSanPham+(sanPham.getGiaTien()*sanPham.getSoLuong());
        }
        httpSession.setAttribute("tongTienAllSanPham", tongTienAllSanPham);
        return "Oder/showcart";

    }

    @GetMapping("/reduce/{id}")
    public String reduce(@PathVariable("id") int id, HttpSession httpSession) {
        ArrayList<SanPham> spList = (ArrayList<SanPham>) httpSession.getAttribute("CartList");
        for (SanPham sanPham : spList) {
            if (sanPham.getIdSanPham() == id) {

                if (sanPham.getSoLuong() == 1) {
                    spList.remove(sanPham);
                    return "redirect:/ShowCart";
                } else {
                    sanPham.setSoLuong(sanPham.getSoLuong() - 1);
                }
            }
        }
        return "redirect:/ShowCart";
    }

    @GetMapping("/increase/{id}")
    public String increase(@PathVariable("id") int id, HttpSession httpSession) {
        ArrayList<SanPham> spList = (ArrayList<SanPham>) httpSession.getAttribute("CartList");
        for (SanPham sanPham : spList) {
            if (sanPham.getIdSanPham()==id) {
                sanPham.setSoLuong(sanPham.getSoLuong() + 1);
            return "redirect:/ShowCart";
            }
        }

        return "redirect:/ShowCart";
    }
    @GetMapping("/BuySanPhamInCart")
    public String buyInCart(HttpSession httpSession) throws Exception{
        ArrayList<SanPham> spList = (ArrayList<SanPham>) httpSession.getAttribute("CartList");
        User user = (User) httpSession.getAttribute("UserAfterLogin");
        for (SanPham sanPham : spList) {
            SanPham sanPhamCu = sanPhamRepo.getSanPhamByID(sanPham.getIdSanPham());
            double tongTien = sanPham.getGiaTien() * sanPham.getSoLuong();
            int newSoLuong = sanPhamCu.getSoLuong() - sanPham.getSoLuong();
            Bill bill = new Bill(0, user, sanPham, sanPham.getSoLuong(), tongTien);
            sanPhamRepo.UpdateSanPhamSauKhiMua(sanPhamCu.getIdSanPham(), newSoLuong);
            billRepo.addBill(bill);
        }
        spList.clear();
        return "redirect:/";

    }
}
