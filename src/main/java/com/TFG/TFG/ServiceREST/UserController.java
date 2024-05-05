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

    @GetMapping(value = "/getUser")
    public ResponseEntity<User> getUser(@RequestBody User user){
        User u=ur.findByCorreo(user.getCorreo());
        if(u.getContrasena().equals(user.getContrasena())){
            return ResponseEntity.ok(u);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/postUser/{DNI}")
    public String postUser(@RequestBody User user,@PathVariable String DNI){
        String mensaje="";
        List<User> us=ur.findAll();
        for(User uT:us){
            if (uT.getDNI().equals(DNI)) {
                mensaje = "Ese usuario ya existe";
                break;
            }else if(uT.getCorreo().equals(user.getCorreo())) {
                mensaje = "Ese usuario ya existe";
                break;
            }else{
                user.setDNI(DNI);
                ur.save(user);
                mensaje="Usuario guardado";
                }
            }
        return mensaje;
    }

    @DeleteMapping(value = "/deleteUser/{DNI}")
    public String deleteUser(@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        ur.delete(u);
        return "Usuario eliminado";
    }

    @GetMapping(value = "/{DNI}/getUserProducts")
    public List<Producto> getProducts(@PathVariable String DNI){
        User u=ur.findByDNI(DNI);
        return u.getProductsU();
    }
}
