package com.TFG.TFG.ServiceREST;


import com.TFG.TFG.Model.Category;
import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository cr;

    @GetMapping(value = "/getProductByCategory/{id}")
    private List<Producto> productListC(@PathVariable long category_id){
        Category c=cr.findById(category_id);
        return c.getProducts();
    }

    @PostMapping(value="/postCategory")
    private String postCategory(@RequestBody Category c){
        cr.save(c);
        return "Categoria guardada";
    }

    @DeleteMapping(value = "/deleteC/{id}")
    private String deleteC(@PathVariable long id){
        Category c=cr.findById(id);
        cr.delete(c);
        return "Categoria borrada";
    }

    /*@PutMapping(value="/updateC")
    private String updateC(@RequestBody Category category){
        Category c=cr.findById(category.getId());
        c.setName(category.getName());
        c.setDescription(category.getDescription());
        c.setProducts(category.getProducts());
    }*/
}