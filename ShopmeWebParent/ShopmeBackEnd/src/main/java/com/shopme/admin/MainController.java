package com.shopme.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zcmich
 * @created 28/09/2022- 09:00
 * @project ShopmeProject
 */

@Controller
public class MainController {

    @GetMapping
    public String viewHomePage(){
        return "index";
    }
}
