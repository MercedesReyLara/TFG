package com.TFG.TFG.ServiceREST;


import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.ReviewRepository;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {


    @Autowired
    private ProductRepository pr;
    @Autowired
    private UserRepository ur;
    @Autowired
    private ReviewRepository rr;


    @GetMapping(value = "/getReviews")
    private List<Review> getReviews(){
        return rr.findAll();
    }

    @PostMapping(value = "/postReviews/{DNI}")
    private String postReviews(@RequestBody Review review,@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        Producto p=pr.findById(review.getProduct().getId());
         /*Añadimos la reseña al usuario y al producto*/
        u.getResenas().add(review);
        ur.save(u);
        p.getResenas().add(review);
        pr.save(p);
        /*En cambio aquí le ponemos valores fijos porque esa reseña solo puede ser de 1
        producto y de 1 usuario.
         */
        review.setUser(u);
        review.setProduct(p);
        rr.save(review);
        return "Review guardada";
    }

    @GetMapping(value = "/{id}/getReviewsP")
    public List<Review> getReviewsProductos(@PathVariable int id){
        /*Queremos ver las reseñas de ese producto. Buscamos el producto por id
        y devolvemos su lista de reseñas
         */
        Producto p=pr.findById(id);
        return p.getResenas();

    }

    @GetMapping(value = "/{DNI}/getReviewsU")
    public List<Review> getReviewsProductos(@PathVariable String DNI){
        /*Queremos ver las reseñas de ese producto. Buscamos el producto por id
        y devolvemos su lista de reseñas
         */
        User u=ur.findByDNI(DNI);
        return u.getResenas();

    }

    @DeleteMapping(value = "/{id}/deleteReview")
    public String deleteReview(@PathVariable int id){
        Review r=rr.findById(id);
        rr.delete(r);
        return "Resena eliminada";
    }

    @PutMapping(value="/updateR")
    public String updateR(@RequestBody Review r){
        Review rU=rr.findById(r.getId());
        rU.setOpinion(r.getOpinion());
        rU.setUser(r.getUser());
        rU.setProduct(r.getProduct());

        rr.save(rU);
        return "Actualzacion review";
    }
}
