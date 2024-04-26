package com.TFG.TFG.Respository;

import com.TFG.TFG.Model.Comment;
import com.TFG.TFG.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findById(long id);
}
