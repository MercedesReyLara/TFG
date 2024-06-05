package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.DTO.ProductDTO;
import com.TFG.TFG.DTO.ReviewDTO;
import com.TFG.TFG.DTO.UserDTO;
import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.Review;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.ReviewRepository;
import com.TFG.TFG.Respository.UserRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository ur;
    @Autowired
    private ProductRepository pr;
    @Autowired
    private ReviewRepository rr;


    @GetMapping(value = "/getUsers")
    public List<UserDTO> getUsers(){
        List<User> users=ur.findAll();
        List<UserDTO> activos=new ArrayList<>();
        for(User un : users){
            if(un.getActivo()){
                UserDTO DTO=new UserDTO(
                   un.getDNI(),
                   un.getNombreU(),
                   un.getApellidosU(),
                   un.getCorreo(),
                   un.getContrasena(),
                   un.getDescripcion(),
                        Base64.getDecoder().decode(un.getProfileP()),
                   un.getActivo()
                );
                activos.add(DTO);
            }
        }
        return activos;
    }

    @GetMapping(value = "/logIn")
    public UserDTO getUser(@RequestParam (value = "nombreU",defaultValue = "")String nombreU,
                        @RequestParam (value = "contrasena",defaultValue = "")String contrasena){
        if(ur.findByNombreU(nombreU)==null){
            return new UserDTO();
        }
        User u=ur.findByNombreU(nombreU);
        if(u.getContrasena().equals(contrasena)){
                UserDTO DTO=new UserDTO(
                        u.getDNI(),
                        u.getNombreU(),
                        u.getApellidosU(),
                        u.getCorreo(),
                        u.getContrasena(),
                        u.getDescripcion(),
                        Base64.getDecoder().decode(u.getProfileP()),
                        u.getActivo() );
                return DTO;
            }
            return null;
        }

    @PostMapping(value = "/getUser")
    public UserDTO getDNI(@RequestBody UserDTO user){
        User u=ur.findByDNI(user.getDni());
        UserDTO uDTO=new UserDTO(u.getDNI(),u.getNombreU(),u.getApellidosU(),
                u.getCorreo(),u.getContrasena(),u.getDescripcion(),
                Base64.getDecoder().decode(u.getProfileP()),u.getActivo());
        return uDTO;
    }


    @PostMapping(value = "/postUser")
    public boolean postUser(@RequestBody UserDTO user){
        List<User> us=ur.findAll();
        for(User uT:us) {
            if (uT.getDNI().equals(user.getDni())) {
                return false;
            }
            if (uT.getCorreo().equals(user.getCorreo())||uT.getNombreU().equals(user.getNombreU())) {
                return false;
            }
        }
        User u=new User(user.getDni(),user.getNombreU(),user.getApellidosU(),user.getCorreo(),user.getContrasena(),
                user.getDescripcion(), user.codearImagen(user.getProfileP()),user.isActivo());
        ur.save(u);
        return true;
    }

    @PutMapping(value = "/darDeBaja")
    public Boolean darBajaUser(@RequestBody UserDTO user){
        User u=ur.findByDNI(user.getDni());
        if(u==null){
            return false;
        }
        u.setActivo(false);
        ur.save(u);
        return true;
    }

    @DeleteMapping(value = "/deleteUser/{DNI}")
    public Boolean deleteUser(@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        if(u==null){
            return false;
        }
        for(Review r:u.getResenas()){
            rr.delete(r);
        }

        for(Producto p:u.getProductsU()){
            p.getUsers().remove(u);
        }
        ur.delete(u);
        return true;
    }

    @PutMapping(value = "/reactivarUser")
    public Boolean reactivarUser(@RequestBody User user){
        User u=ur.findByDNI(user.getDNI());
        if(u==null){
            return false;
        }
        u.setActivo(true);
        ur.save(u);
        return true;
    }


    @PostMapping(value = "/getUserProducts")
    public List<ProductDTO> getProducts(@RequestBody User user){
        User u=ur.findByDNI(user.getDNI());
        if(u==null){
            return null;
        }
        List<Producto> products=u.getProductsU();
        List<ProductDTO> DTOS=new ArrayList<>();
        for(Producto p:products){
            ProductDTO DTO=new ProductDTO(
                 p.getId(),
                 p.getNombreP(),
                 p.getDescripcionP(),
                 p.getPrecio(),
                 p.getCategory().getNombre()
            );
            DTOS.add(DTO);
        }
        return DTOS;
    }

    @PostMapping(value = "/getReviewsUser")
    public List<ReviewDTO> getReviews(@RequestBody User user){
        User u=ur.findByDNI(user.getDNI());
        if(u==null){
            return null;
        }
        List<Review> reviews=u.getResenas();
        List<ReviewDTO> DTOS=new ArrayList<>();
        for(Review r:reviews){
            ReviewDTO DTO=new ReviewDTO(
                    r.getId(),
                    r.getNombreR(),
                    r.getOpinion(),
                    r.getPuntuacion(),
                    r.getUser().getDNI(),
                    r.getUser().getCorreo(),
                    r.getProduct().getId(),
                    r.getProduct().getNombreP()
            );
            DTOS.add(DTO);
        }
        return DTOS;
    }

    @PutMapping(value="/updateUser")
    public Boolean updateUser(@RequestBody UserDTO user){
        User u=ur.findByDNI(user.getDni());
        if(u==null){
            return false;
        }
        u.setDescripcion(user.getDescripcion());
        u.setNombreU(user.getNombreU());
        u.setContrasena(user.getContrasena());

        ur.save(u);
        return true;
    }

    @PostMapping(value="/getUserByCorreo")
    public UserDTO userByName(@RequestBody UserDTO user){
        User u=ur.findByCorreo(user.getCorreo());
        if(u==null){
            return new UserDTO();
        }
        UserDTO userN=new UserDTO(u.getDNI(),u.getNombreU(),u.getApellidosU(),u.getCorreo(),u.getContrasena(),
                u.getDescripcion(),Base64.getDecoder().decode(u.getProfileP()),u.getActivo());
        return userN;
    }
}
