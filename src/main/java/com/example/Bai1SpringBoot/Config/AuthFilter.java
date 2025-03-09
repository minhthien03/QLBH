package com.example.Bai1SpringBoot.Config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.Bai1SpringBoot.Model.Entity.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("UserAfterLogin");
        // Ap dung cho tat ca cac Role
        if ((uri.equals("/login") || uri.equals("/") || uri.equals("/loginToSystem")
                || uri.startsWith("/ViewDeteil/") || uri.equals("/logout")
                || uri.equals("/searchSanPham")) || uri.equals("/showsignup") 
                || uri.equals("/singup")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Danh cho Admin duoc toan quyen su dung tat ca
        if (user == null) {
            httpServletResponse.sendRedirect("/login");

        } else
        // danh cho role Seller
        if (user.getRoleUser().equals("Seller") && uri.equals("/ShowAddSanPham")
                || uri.startsWith("/ViewDeteil") || uri.equals("/addsanphams")
                || uri.startsWith("/ShowEdit")   || uri.equals("/editsanpham")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else
        // danh cho role User
        if (user.getRoleUser().equals("User") && uri.equals("/OderSanPham")
                || uri.startsWith("/ShowOder")  || uri.equals("/ShowBillByUserId")
                || uri.startsWith("/AddToCart") || uri.equals("/ShowCart")
                || uri.startsWith("/reduce")    || uri.startsWith("/increase")
                || uri.equals("/BuySanPhamInCart")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else if (user.getRoleUser().equals("Admin")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        else {
            httpServletResponse.sendRedirect("/");
        }
    }

}
