package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.DTO.ProductDTO;
import com.TFG.TFG.Model.Category;
import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.CategoryRepository;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.ReviewRepository;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository pr;
    @Autowired
    private UserRepository ur;
    @Autowired
    private ReviewRepository rr;
    @Autowired
    private CategoryRepository cr;

    @GetMapping(value = "/getProducts")
    public List<ProductDTO> getProductos(){
        List<Producto> productos=pr.findAll();
        List<ProductDTO> DTOS=new ArrayList<>();
        for(Producto p:productos){
            if(p.getResenas().isEmpty()){
                return DTOS;
            }
            ProductDTO pDTO=new ProductDTO(
                    p.getId(),
                    p.getNombreP(),
                    p.getDescripcionP(),
                    p.getPrecio(),
                    p.getCategory().getNombre()
            );
           DTOS.add(pDTO);
        }
        return  DTOS;
    }

   @GetMapping(value="/lowProducts")
   private List<ProductDTO> lowProducts(){
        int puntuacion=0;
        int cantidad=0;
        List<Producto> listaAll=new ArrayList<>();
        listaAll=pr.findAll();
        List<ProductDTO>DTOS=new ArrayList<>();
        for(Producto p:listaAll){
            for(Review r:p.getResenas()) {
                cantidad = cantidad + 1;
                puntuacion = puntuacion + r.getPuntuacion();
            }
            float media= (float) puntuacion /cantidad;
            if(media<=5){
                    ProductDTO pDTO=new ProductDTO(
                            p.getId(),
                            p.getNombreP(),
                            p.getDescripcionP(),
                            p.getPrecio(),
                            p.getCategory().getNombre()
                    );
                    DTOS.add(pDTO);
                }
        }
        return DTOS;
   }
    @GetMapping(value="/highProducts")
    private List<ProductDTO> highProducts(){
        int cantidad=0;
        int puntuacion=0;
        List<Producto> listaAll=new ArrayList<>();
        listaAll=pr.findAll();
        List<ProductDTO>DTOS=new ArrayList<>();
        for(Producto p:listaAll){
            for(Review r:p.getResenas()){
                cantidad = cantidad + 1;
                puntuacion = puntuacion + r.getPuntuacion();
            }
            float media= (float) puntuacion /cantidad;
            if(media>5){
                    ProductDTO pDTO=new ProductDTO(
                            p.getId(),
                            p.getNombreP(),
                            p.getDescripcionP(),
                            p.getPrecio(),
                            p.getCategory().getNombre()
                    );
                    DTOS.add(pDTO);
                }
            }
        return DTOS;
    }

    @GetMapping(value = "/Uproduct")
    private List<User> getUsersByProduct(@RequestBody ProductDTO productDTO){
        Producto p=pr.findById(productDTO.getId());
        return p.getUsers();
    }
}
