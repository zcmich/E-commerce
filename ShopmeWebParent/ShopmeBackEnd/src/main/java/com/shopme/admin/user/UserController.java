package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listAll(Model model){
        List<User> listUser = service.listAll();
        model.addAttribute( "listUser", listUser);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        User user = new User();
        user.setEnabled(true);
        List<Role> listRoles = service.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles",listRoles);
        model.addAttribute("pageTitle", "Create new User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
        System.out.println(user);
        service.save(user);
        redirectAttributes.addFlashAttribute("message", "User "+ user.getFirstName()+ " "+user.getLastName()
                                                                   +" has been saved successfully");
        return  "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            User user = service.get(id);
            List<Role> listRoles = service.listRoles();
            model.addAttribute("user", user);
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("pageTitle", "Edit User (ID: "+ user.getId() +" )");
            return "user_form";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return  "redirect:/users";

        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            service.delete(id);
            redirectAttributes.addFlashAttribute("message","User with Id "+ id + " successfully deleted");
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return  "redirect:/users";

    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes){
        service.upateUserEnabledStatus(id,enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "User with ID " + id + "has been" + status;
        redirectAttributes.addFlashAttribute("message",message);
        return  "redirect:/users";
    }



}
