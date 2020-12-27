package com.springboot.demo.restwebservice.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDAO.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieverUserById(@PathVariable int id) {
        User user = userDAO.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        return user;

    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDAO.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDAO.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }

}
