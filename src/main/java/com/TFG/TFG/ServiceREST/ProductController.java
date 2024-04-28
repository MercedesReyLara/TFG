package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.Model.Category;
import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.CategoryRepository;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.ReviewRepository;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository pr;
    @Autowired
    private UserRepository ur;
    @Autowired
    private ReviewRepository rr;
    @Autowired
    private CategoryRepository cr;

    @GetMapping(value = "/getProduct")
    public List<Producto> getProductos(){
        return pr.findAll();
    }

    @PostMapping(value = "/postProducto")
    public String postProductos(@RequestBody Producto producto){
        Category cF=producto.getCategory();
        Category c=cr.findById(cF.getId());
        c.getProducts().add(producto);
        pr.save(producto);
        cr.save(c);
        return "Producto guardado";
    }

    @DeleteMapping(value = "/deleteProducto/{id}")
    public String deleteProductos(@PathVariable long id){
        Producto p=pr.findById(id);
        pr.delete(p);
        return "Producto eliminado";
    }

    @GetMapping(value = "/{id}/product")
    private List<Producto> getProducstByUser(@PathVariable long id){
        User u=ur.findById(id);
        List<Producto>products=new ArrayList<>();
        for(Review r:u.getResenas()){
            products.add(r.getProduct());
        }
        return products;
    }
}
