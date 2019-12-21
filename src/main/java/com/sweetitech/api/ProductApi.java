package com.sweetitech.api;

import com.sweetitech.entity.Product;
import com.sweetitech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductApi {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product/all")
    public List<Product> fetchProducts(){
        List<Product> productList = (List<Product>) productRepository.findAll();
        return productList;
    }

    @GetMapping("/product/{id}")
    public Product fetchSingleProduct(@PathVariable(value = "id") long id) throws Exception {
        Product product = productRepository.findById(id).get();
        return product;
    }
}
