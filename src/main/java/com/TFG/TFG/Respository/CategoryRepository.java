package com.TFG.TFG.Respository;


import com.TFG.TFG.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findById(int id);
    Category findByNombre(String nombre);
}
