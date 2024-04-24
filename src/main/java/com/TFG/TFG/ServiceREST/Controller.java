package com.TFG.TFG.ServiceREST;

import com.TFG.TFG.Model.User;
import com.TFG.TFG.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private UserRepository ur;

    /*@GetMapping(value = "/hola")
    public String prove(){
        return "Hola 🧏‍♀️🧏‍♀️🧏‍♀️";
    }*/

    @GetMapping(value = "/getUser")
    public List<User> getUsers(){
        return ur.findAll();
    }

    @PostMapping(value = "/postUser")
    public String postUser(@RequestBody User user){
        ur.save(user);
        return "Usuario guardado";
    }
}
