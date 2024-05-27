package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.DTO.ProductDTO;
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

    @GetMapping(value = "/getProducts")
    public List<ProductDTO> getProductos(){
        List<Producto> productos=pr.findAll();
        List<ProductDTO> DTOS=new ArrayList<>();
        for(Producto p:productos){
            ProductDTO pDTO=new ProductDTO(
                    p.getId(),
                    p.getNombreP(),
                    p.getDescripcionP(),
                    p.getPrecio(),
                    p.getCategory().getNombre()
            );
           DTOS.add(pDTO);
        }
        return  DTOS;
    }

    /*@PostMapping(value = "/postProducto")
    public String postProductos(@RequestBody Producto producto){
        Category cF=producto.getCategory();
        Category c=cr.findById(cF.getId());
        c.getProducts().add(producto);
        pr.save(producto);
        cr.save(c);
        return "Producto guardado";
    } No voy a utilizarlas en la parte de usuario

    @PutMapping(value = "/{id}/updateP")
    private String updateProduct(@PathVariable int id,@RequestBody Producto product){
        Producto p=pr.findById(id);
        p.setNombreP(product.getNombreP());
        p.setDescripcionP(product.getDescripcionP());
        p.setResenas(product.getResenas());
        p.setCategory(product.getCategory());
        p.setUsers(product.getUsers());

        pr.save(p);

        return "Modificado";
    }
    @DeleteMapping(value = "/deleteProducto/{id}")
    public String deleteProductos(@PathVariable int id){
        Producto p=pr.findById(id);
        pr.delete(p);
        return "Producto eliminado";
    }*/

    @GetMapping(value = "/Uproduct")
    private List<User> getUsersByProduct(@RequestBody ProductDTO productDTO){
        Producto p=pr.findById(productDTO.getId());
        return p.getUsers();
    }
}