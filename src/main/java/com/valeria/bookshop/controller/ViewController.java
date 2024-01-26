package com.valeria.bookshop.controller;

import com.valeria.bookshop.db.entity.UserRole;
import com.valeria.bookshop.security.JwtAuthentication;
import com.valeria.bookshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final UserService userService;

    @GetMapping("/")
    public ModelAndView mainView() {
        String name = "Главная";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/account")
    public ModelAndView accountView() {
        String name = "Пользователь";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("account");
        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView cartView() {
        String name = "Корзина";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("cart");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginView() {
        String name = "Вход";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/signedIn")
    public ModelAndView signedIn() {
        ModelAndView modelAndView = new ModelAndView();
        var authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        userService.addNewUserIfNeed(authentication);
        modelAndView.setViewName("redirect:catalog");
        return modelAndView;
    }

    @GetMapping("/orders")
    public ModelAndView ordersView() {
        String name = "Заказы";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("orders");
        return modelAndView;
    }

    @GetMapping("/catalog")
    public ModelAndView catalogView() {
        String name = "Каталог";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("catalog");
        return modelAndView;
    }

    @GetMapping("/details")
    public ModelAndView detailsView() {
        String name = "Подробнее";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("product-details");
        return modelAndView;
    }

    @GetMapping("/contact")
    public ModelAndView contactView() {
        String name = "Контакты";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("contact");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView usersView() {
        String name = "Клиенты";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("/admins")
    public ModelAndView adminsView() {
        String name = "Администраторы";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("admins");
        return modelAndView;
    }

    @GetMapping("/delivery")
    public ModelAndView deliveryView() {
        String name = "Доставка";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("delivery");
        return modelAndView;
    }

    @GetMapping("/pay")
    public ModelAndView payView() {
        String name = "Оплата";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("pay");
        return modelAndView;
    }

    @GetMapping("/ofetra")
    public ModelAndView ofetraView() {
        String name = "Пользовательское соглашение";
        String userRole = String.valueOf(UserRole.ROLE_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageTitle", name);
        modelAndView.addObject("uRole", userRole);
        modelAndView.setViewName("ofetra");
        return modelAndView;
    }
}
