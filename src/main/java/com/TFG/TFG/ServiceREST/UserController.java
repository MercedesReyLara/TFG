package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository ur;
    @Autowired
    private ProductRepository pr;


    @GetMapping(value = "/getUsers")
    public List<User> getUsers(){
        return ur.findAll();
    }

    @GetMapping(value = "/logIn")
    public User getUser(@RequestParam (value = "nombreU",defaultValue = "")String nombreU,
                        @RequestParam (value = "contrasena",defaultValue = "")String contrasena){
        User u=ur.findByNombreU(nombreU);
            if(u.getContrasena().equals(contrasena)){
                return u;
            }
            return null;
        }

    @GetMapping(value = "/getUser/{DNI}")
    public User getDNI(@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        return u;
    }
    @PostMapping(value = "/postUser/{DNI}")
    public boolean postUser(@RequestBody User user,@PathVariable String DNI){
        List<User> us=ur.findAll();
        for(User uT:us) {
            if (uT.getDNI().equals(DNI)) {
                return false;
            }
            if (uT.getCorreo().equals(user.getCorreo())||uT.getNombreU().equals(user.getNombreU())) {
                return false;
            }
        }
        user.setDNI(DNI);
        ur.save(user);
        return true;
    }

    @DeleteMapping(value = "/deleteUser/{DNI}")
    public Boolean deleteUser(@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        if(u==null){
            return false;
        }
        ur.delete(u);
        return true;
    }

    @GetMapping(value = "/{DNI}/getUserProducts")
    public List<Producto> getProducts(@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        if(u==null){
            return null;
        }
        return u.getProductsU();
    }
}
