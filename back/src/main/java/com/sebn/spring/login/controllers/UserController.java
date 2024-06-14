package com.sebn.spring.login.controllers;

import com.sebn.spring.login.models.ERole;
import com.sebn.spring.login.models.User;
import com.sebn.spring.login.repository.UserRepository;
import com.sebn.spring.login.models.*;
import com.sebn.spring.login.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/test/admin/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping()
    public void save(@RequestBody List<User> users) {
        userRepository.saveAll(users);
    }

    @GetMapping("/user-by-name-and-role/{name}/{role}")
    public List<User> getUserByNameAndRole(@PathVariable("name") String name, @PathVariable("role") ERole role) {
        return userRepository.findByNameContainingIgnoreCaseAndRole_Name(name, role);
    }

    @GetMapping("/user-by-role/{role}")
    public List<User> getUserByRole(@PathVariable("role") ERole role) {
        return userRepository.findByRole_Name(role);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteUserById(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "Deleted Successfully";
    }

}
