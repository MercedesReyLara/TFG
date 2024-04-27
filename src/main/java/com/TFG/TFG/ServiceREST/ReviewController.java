package com.TFG.TFG.ServiceREST;


import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.ReviewRepository;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {


    @Autowired

    private ProductRepository pr;
    private UserRepository ur;
    private ReviewRepository rr;


    @GetMapping(value = "/getReviews")
    private List<Review> getReviews(){
        return rr.findAll();
    }

    @PostMapping(value = "/postReviews")
    private String postReviews(@PathVariable long product_id, @PathVariable long user_id,
                               @RequestBody Review review){
        Review r=rr.save(review);
        User u=ur.findById(user_id);
        Producto p=pr.findById(product_id);
         /*Añadimos la reseña al usuario y al producto*/
        u.getResenas().add(r);
        p.getResenas().add(r);
        /*En cambio aquí le ponemos valores fijos porque esa reseña solo puede ser de 1
        producto y de 1 usuario
         */
        r.setUser(u);
        r.setProduct(p);
        return "Review guardada";
    }

    @GetMapping(value = "/{id}/getReviews")
    public List<Review> getUserWork(@RequestBody long id_product){
        /*Queremos ver las reseñas de ese producto. Buscamos el producto por id
        y devolvemos su lista de reseñas
         */
        Producto p=pr.findById(id_product);
        return p.getResenas();

    }
}
