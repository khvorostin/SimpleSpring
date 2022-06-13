package com.khvorostin.simplespring.controllers;

import com.khvorostin.simplespring.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private ProductsService productsService;

    @Autowired
    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    // GET http://localhost:8410/showroom/products
    @GetMapping(value = "/products")
    public String showProductsList(Model model) {
        model.addAttribute("products", productsService.findAll());
        return "products-list";
    }

    // GET http://localhost:8410/showroom/products/{id}
    @GetMapping(value = "/products/{id}")
    public String showProductPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productsService.findOne(id));
        return "product";
    }

    // GET http:://localhost:8410/showroom/products/add
    @GetMapping(value = "/products/add")
    public String addProduct() {
        return "product-form";
    }

    // POST http:://localhost:8410/showroom/products/add
    @PostMapping(value = "/products/add")
    public String addProduct(@RequestParam String title, @RequestParam double cost) {
        Long id = productsService.addProduct(title, cost);
        return "redirect:/products/" + id.toString();
    }

    // GET http://localhost:8410/showroom/products/{id}/incr
    // GET http://localhost:8410/showroom/products/{id}/decr
    @GetMapping(value = "/products/{id}/{action}")
    public String changeCost(@PathVariable("id") Long id, @PathVariable("action") String action) {
        if (action.equals("decr")) {
            productsService.decrCost(id);
        } else if (action.equals("incr")) {
            productsService.incCost(id);
        }

        return "redirect:/products";
    }
}
