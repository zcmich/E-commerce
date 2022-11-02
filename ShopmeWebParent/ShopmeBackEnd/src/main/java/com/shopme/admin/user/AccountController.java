package com.shopme.admin.user;

import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String updateView(@AuthenticationPrincipal ShopmeUserDetails loggedUser, Model model){
         String email = loggedUser.getUsername();
         User user = userService.getByEmail(email);
         model.addAttribute("user", user);
        return "account_form";
    }



}
