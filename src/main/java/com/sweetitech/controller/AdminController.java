package com.sweetitech.controller;

import com.sweetitech.entity.Product;
import com.sweetitech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final List<String> productTypes = Arrays.asList
            ("RAM", "MOTHERBOARD" ,"GRAPHICS_CARD");

    @Autowired
    private ProductRepository productRepository;

    /*
    @ Mapping for admin dashboard
    */
    @RequestMapping("/dashboard")
    public String loadDashBoard(Model model)
    {
        List<Product> productList = (List<Product>) productRepository.findAll();

        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product product1) {
                double d=(product1.getProfitPercentage()-product.getProfitPercentage());
                int ans=(int)d;
                return ans;
            }
        });
        List<Product>topFive=new ArrayList<Product>();
        for(int i=0;i<productList.size()&&i<5;i++)topFive.add(productList.get(i));
        model.addAttribute("productList",topFive);
        return "/dashboard";
    }

    /*
    @ Mapping to display all product as list
    */
    @RequestMapping("/product-list")
    public String loadProductList(Model model) {
        List<Product> productList = (List<Product>) productRepository.findAll();
        model.addAttribute("productList",productList);
        return "products";
    }

    /*
    @ Mapping for CRUD operations
     */
    @RequestMapping("/crud-product")
    private String loadAddEditAndDeleteAblePage(Model model){
        List<Product> productList = (List<Product>) productRepository.findAll();
        model.addAttribute("productList",productList);
        return "crud-product";
    }

    /*
    @ Mapping to add product
    */
    @ResponseBody
    @RequestMapping(value = "/add-product", method = RequestMethod.POST)
    private boolean addProduct(@RequestParam String name, @RequestParam double price, @RequestParam String type
            , @RequestParam double rate)
    {
        boolean performedSuccess = true;
        try{
            /* Product type validation */
            if(!productTypes.contains(type)){
                return false;
            }
            Product product = new Product(name, price,rate, type);
            productRepository.save(product);
        }catch (Exception e){
            performedSuccess = false;
        }
        return performedSuccess;
    }



    /*
   @ Mapping to update product
   */
    @ResponseBody
    @RequestMapping(value = "/update-product", method = RequestMethod.POST)
    private boolean updateProduct(@RequestParam Long id, @RequestParam String name, @RequestParam double price, @RequestParam String type
            , @RequestParam double rate)
    {
        boolean performedSuccess = true;
        try{
            /* Product type validation */
            if(!productTypes.contains(type)){
                return false;
            }
            Product product = new Product(name, price,rate, type);
            product.setId(id);
            productRepository.save(product);
        }catch (Exception e){
            performedSuccess = false;
        }
        return performedSuccess;
    }



    /*
    @ Delete product by 'id'
     */
    @RequestMapping("/delete")
    public String deleteProduct(@RequestParam long id){
        productRepository.deleteById(id);
        return "redirect:/admin/crud-product";
    }
}
