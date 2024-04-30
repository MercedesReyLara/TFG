package com.TFG.TFG.Respository;


import com.TFG.TFG.Model.Category;
import com.TFG.TFG.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Producto, Integer> {
    Producto findById(int id);
    List<Producto> findByCategory(Category category);
    Producto findByNombreP(String nombreP);
}
