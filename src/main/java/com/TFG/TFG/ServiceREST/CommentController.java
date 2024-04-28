package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.Model.Category;
import com.TFG.TFG.Model.Comment;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Respository.CommentRepository;
import com.TFG.TFG.Respository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository cr;
    @Autowired
    private ReviewRepository rr;


    @GetMapping(value = "getComments")
    private List<Comment> getComments(){
        return cr.findAll();
    }
    
    @PostMapping(value="postComment/{id}")
    private String postComment(@RequestBody Comment comment){
        Review r= rr.findById(comment.getReview().getId());
        if (r== null) {
           return "Review no encontrada";
        }
        cr.save(comment);
        r.getComments().add(comment);
        rr.save(r);

        return "Comentario posteado con exito";
    }

    @DeleteMapping(value = "{id}/deleteComment")
    private String deleteComment(@PathVariable long id){
        Comment c=cr.findById(id);
        cr.delete(c);
        return "Comentario eliminado";
    }

    @PutMapping(value="updateC")
    public String updateC(@RequestBody Comment c){
        Comment cU=cr.findById(c.getId());
        cU.setTitle(c.getTitle());
        cU.setReview(c.getReview());

        cr.save(cU);

        return "Comentario updateado";
    }

}
