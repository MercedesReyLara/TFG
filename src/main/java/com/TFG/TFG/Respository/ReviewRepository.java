package com.TFG.TFG.Respository;

import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Review findById(long id);
    Review findByProduct(Producto producto);
    Review findByUser(User user);
}
