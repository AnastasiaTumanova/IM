package org.IM2.magazine.controllers;

import org.IM2.Im2Application;
import org.IM2.magazine.dao.ProductRepository;
import org.IM2.magazine.dto.ProductDTO;
import org.IM2.magazine.models.Product;
import org.IM2.magazine.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }
    @GetMapping
    public String list(Model model){
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        if(principal==null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        Logger logger = Logger.getLogger(ProductController.class.getName());
        logger.log(Level.INFO, "Bucket Success Add");
        return "redirect:/products";
    }

    @PostMapping
    public String addProduct(ProductDTO dto){
        productService.addProduct(dto);
        Logger logger = Logger.getLogger(ProductController.class.getName());
        logger.log(Level.INFO, "Product Success Add");
        return "redirect:/products";
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ProductDTO getById(@PathVariable Long id){
        return productService.getById(id);
    }
}
