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
    private UserRepository ur;
    private ReviewRepository rr;
    private ReviewController(ProductRepository pr, UserRepository ur, ReviewRepository rr){
        this.rr=rr;
        this.pr=pr;
        this.ur=ur;
    }

    @GetMapping(value = "/getReviews")
    private List<Review> getReviews(){
        return rr.findAll();
    }

    /*@PostMapping(value = "/postReviews")
    private String postReviews(@PathVariable long product_id, @PathVariable long user_id,
                               @RequestBody Review review){
        Review r=rr.save(review);
        User u=ur.findById(user_id);
        Producto p=pr.findById(product_id);
        r.setUser(u);
        r.setProduct(p);


        return "Review guardada";
    }*/
}
