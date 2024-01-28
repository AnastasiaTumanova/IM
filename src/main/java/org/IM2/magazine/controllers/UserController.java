package org.IM2.magazine.controllers;


import org.IM2.magazine.dto.ProductDTO;
import org.IM2.magazine.dto.UserDTO;
import org.IM2.magazine.models.User;
import org.IM2.magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model){
        List<UserDTO> list = userService.getAll();
        model.addAttribute("users", list);
        Logger logger = Logger.getLogger(UserController.class.getName());
        logger.log(Level.INFO, "Данные получены");
        return "userList";
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/new")
    public String newUser(Model model){
        System.out.println("Called method newUser");
        model.addAttribute("user", new UserDTO());
        Logger logger = Logger.getLogger(UserController.class.getName());
        logger.log(Level.INFO, "Пользователь добавлен");
        return "user";
    }

    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username){
        System.out.println("Called method getRoles");
        User byName = userService.findByName(username);
        return byName.getRole().name();
    }
    @PostMapping("/new")
    public String saveUser(UserDTO dto, Model model){
        if(userService.save(dto)){
            Logger logger = Logger.getLogger(UserController.class.getName());
            logger.log(Level.INFO, "Данные сохранены");
            return "redirect:/";
        }
        else {
            model.addAttribute("user", dto);
            return "user";
        }

    }
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if (principal==null)
        {
            Logger logger = Logger.getLogger(UserController.class.getName());
            logger.log(Level.WARNING, "Вы не авторизированы");
        }
        User user = userService.findByName(principal.getName());
        UserDTO dto = UserDTO.builder().username(user.getUsername()).email(user.getEmail()).build();
        model.addAttribute("user", dto);
        return "profile";
    }
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal){
        if(principal==null || !Objects.equals(principal.getName(), dto.getUsername())){
            Logger logger = Logger.getLogger(UserController.class.getName());
            logger.log(Level.WARNING, "Вы не авторизированы");

        }
        if(dto.getPassword()!=null && !dto.getPassword().isEmpty()){
            model.addAttribute("user", dto);

            return "profile";
        }
        userService.updateProfile(dto);
        Logger logger = Logger.getLogger(UserController.class.getName());
        logger.log(Level.INFO, "Профиль обновлен");
        return "redirect:/users/profile";
    }

}
