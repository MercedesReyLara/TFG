package com.TFG.TFG.ServiceREST;


import com.TFG.TFG.DTO.ProductDTO;
import com.TFG.TFG.DTO.ReviewDTO;
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
    @Autowired
    private UserRepository ur;
    @Autowired
    private ReviewRepository rr;


    @GetMapping(value = "/getReviews")
    private List<ReviewDTO> getReviews(){
        List<Review> reviews= rr.findAll();
        List<ReviewDTO> DTOS=new ArrayList<>();
        for(Review r:reviews){
            ReviewDTO rDTO = new ReviewDTO(
                    r.getId(),
                    r.getNombreR(),
                    r.getOpinion(),
                    r.getUser().getDNI(),
                    r.getUser().getCorreo(),
                    r.getProduct().getId(),
                    r.getProduct().getNombreP()
            );
            DTOS.add(rDTO);
        }
        return DTOS;
    }

    @PostMapping(value = "/postReviews")
    private Boolean postReviews(@RequestBody ReviewDTO review){
        User u=ur.findByDNI(review.getUser());
        Producto p=pr.findById(review.getProduct());
         /*Añadimos la reseña al usuario y al producto*/
        Review r=new Review(review.getNombre(),review.getOpinionU(),u,p);
        u.getResenas().add(r);
        ur.save(u);
        p.getResenas().add(r);
        pr.save(p);
        /*En cambio aquí le ponemos valores fijos porque esa reseña solo puede ser de 1
        producto y de 1 usuario.
         */
        rr.save(r);
        return true;
    }

    @GetMapping(value = "/getReviewsP")
    public List<ReviewDTO> getReviewsProductos(@RequestBody ProductDTO productDTO){
        /*Queremos ver las reseñas de ese producto. Buscamos el producto por id
        y devolvemos su lista de reseñas
         */
        Producto p=pr.findById(productDTO.getId());
        if(p!=null) {
            List<Review> reviews = p.getResenas();
            List<ReviewDTO> reviewsDTO = new ArrayList<>();
            for (Review r : reviews) {
                ReviewDTO rDTO = new ReviewDTO(
                        r.getId(),
                        r.getNombreR(),
                        r.getOpinion(),
                        r.getUser().getDNI(),
                        r.getUser().getCorreo(),
                        r.getProduct().getId(),
                        r.getProduct().getNombreP()
                );
                reviewsDTO.add(rDTO);
            }
            return reviewsDTO;
        }
        return null;
    }

    @GetMapping(value = "/{DNI}/getReviewsU")
    public List<ReviewDTO> getReviewsProductos(@PathVariable String DNI){
        /*Queremos ver las reseñas de ese producto. Buscamos el producto por id
        y devolvemos su lista de reseñas
         */
        User u=ur.findByDNI(DNI);
        if(u!=null) {
            List<Review> reviews = u.getResenas();
            List<ReviewDTO> reviewsDTO = new ArrayList<>();
            for (Review r : reviews) {
                ReviewDTO rDTO = new ReviewDTO(
                        r.getId(),
                        r.getNombreR(),
                        r.getOpinion(),
                        r.getUser().getDNI(),
                        r.getUser().getCorreo(),
                        r.getProduct().getId(),
                        r.getProduct().getNombreP()
                );
                reviewsDTO.add(rDTO);
            }
            return reviewsDTO;
        }
        return null;
    }

    @DeleteMapping(value = "/{id}/deleteReview")
    public Boolean deleteReview(@PathVariable int id){
        Review r=rr.findById(id);
        if(r!=null){
            rr.delete(r);
            return true;
        }
        return false;
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
