package ru.prytkovv.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prytkovv.demo.dto.ProductDto;
import ru.prytkovv.demo.service.ProductService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/products", produces = "application/json")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProduct(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return productService.getProducts(limit, ProductDto::of);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id") UUID id) {
        return productService.getProduct(id, ProductDto::of);
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable("id") UUID id) {
        productService.removeProduct(id);
    }
}
