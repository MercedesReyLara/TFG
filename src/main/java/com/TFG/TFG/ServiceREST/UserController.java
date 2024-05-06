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
    public ResponseEntity<User> getUser(User user){
        /*if(ur.findByNombreU(user.getNombreU())!=null||ur.findByCorreo(user.getCorreo())!=null){No sé exactamente
        como lo voy a abordar
         */
        User u=ur.findByNombreU(user.getNombreU());
            if(u.getContrasena().equals(user.getContrasena())){
                return ResponseEntity.ok(u);
            }else{
                return ResponseEntity.notFound().build();
            }
        }

    @GetMapping(value = "/getUser")
    public ResponseEntity<User> getDNI(User user){
        User u=ur.findByDNI(user.getDNI());
        return ResponseEntity.ok(u);
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
