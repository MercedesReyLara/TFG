package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.Model.Producto;
import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.ProductRepository;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository ur;
    @Autowired
    private ProductRepository pr;


    @GetMapping(value = "/getUser")
    public List<User> getUsers(){
        return ur.findAll();
    }

    @PostMapping(value = "/postUser")
    public String postUser(@RequestBody User user){
        ur.save(user);
        return "Usuario guardado";
    }

    @DeleteMapping(value = "/deleteUser/{id}")
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
