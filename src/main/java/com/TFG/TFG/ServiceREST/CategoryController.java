package com.TFG.TFG.ServiceREST;


import com.TFG.TFG.DTO.CategoryDTO;
import com.TFG.TFG.DTO.ProductDTO;
import com.TFG.TFG.DTO.ReviewDTO;
import com.TFG.TFG.Model.Category;
import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository cr;

    @PostMapping(value = "/getPBC")
    private List<ReviewDTO> productListC(@RequestBody CategoryDTO categoryDTO){
        Category c=cr.findByNombre(categoryDTO.getNombre());
        if(c!=null) {
            List<Producto> productos = c.getProducts();
            List<ReviewDTO> DTOS = new ArrayList<>();
            for (Producto p : productos) {
                for(Review r:p.getResenas()){
                    ReviewDTO DTO=new ReviewDTO(
                            r.getId(),
                            r.getNombreR(),
                            r.getOpinion(),
                            r.getUser().getDNI(),
                            r.getUser().getNombreU(),
                            p.getId(),
                            p.getNombreP()
                    );
                    DTOS.add(DTO);
                }

            }
            return DTOS;
        }
        return null;
    }

    @GetMapping(value = "/getCategories")
    private List<CategoryDTO> getCategories(){
        List<Category> categories= cr.findAll();
        List<CategoryDTO> DTOS=new ArrayList<>();
        for(Category c:categories){
            CategoryDTO cDTO= new CategoryDTO(
                    c.getId(),
                    c.getNombre(),
                    c.getDescripcion()
            );
           DTOS.add(cDTO);
        }
        return DTOS;
    }
    /*@PostMapping(value="/postCategory")
    private String postCategory(@RequestBody Category c){
        cr.save(c);
        return "Categoria guardada";
    }

    /*@DeleteMapping(value = "/deleteC/{id}")
    private String deleteC(@PathVariable int id){
        Category c=cr.findById(id);
        cr.delete(c);
        return "Categoria borrada";
    }


    /*@PutMapping(value="/updateC")
    private String updateC(@RequestBody Category category){
        Category c=cr.findById(category.getId());
        c.setNombre(category.getNombre());
        c.setDescripcion(category.getDescripcion());
        c.setProducts(category.getProducts());

        cr.save(c);
        return "Categoria guardada";
    } No son necesarias para la parte de usuario

    /*@PutMapping(value = "/updateCt")
    public String updateCt(@RequestBody Category c){
        Category cU=cr.findById(c.getId());

        cU.setNombre(c.getNombre());
        cU.setDescripcion(c.getDescripcion());

        cr.save(cU);

        return "Categoria updateada";
    }*/
}
