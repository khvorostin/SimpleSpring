package com.khvorostin.simplespring.market.controllers;

import com.khvorostin.simplespring.market.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
public class SecurityController {

    @Autowired
    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    // http://localhost:8410/market/security/
    @GetMapping("/")
    public String unsecuredPage() {
        return "unsecured page";
    }

    // http://localhost:8410/market/security/can_add_new_products
    @GetMapping("/can_add_new_products")
    public String userCanAddProducts() {
        return "this user can add new products (avail only for admin)";
    }

    // http://localhost:8410/market/security/can_edit_products
    @GetMapping("/can_edit_products")
    public String userCanEditProducts() {
        return "this user can edit products (avail only for admin)";
    }

    // http://localhost:8410/market/security/can_view_cart
    @GetMapping("/can_view_cart")
    public String userCanViewCart() {
        return "this user can view cart (avail for user)";
    }

}
