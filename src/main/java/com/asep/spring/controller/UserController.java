package com.asep.spring.controller;

import com.asep.spring.entity.AuthUser;
import com.asep.spring.entity.User;
import com.asep.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/", "/user"})
public class UserController {
    @Autowired
    UserService userService;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/message")
    public String message() {
        return "Hello";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        LOG.info("Fetching User with id " + id);
        Optional<User> user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("Creating User " + user.getName());
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/get", headers = "Accept=application/json")
    public List<User> getAllUser() {
        List<User> tasks = userService.getUser();
        return tasks;
    }

    @PostMapping(value = "/getByEmail", headers = "Accept=application/json")
    public ResponseEntity<User> getUserByEmail(@RequestBody AuthUser aUser) {
        User user = userService.getUserByEmail(aUser);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> updateUser(@RequestBody User currentUser, @PathVariable("id") String id) {
        LOG.info("Updating User " + currentUser.getName());
        Optional<User> user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.update(currentUser, currentUser.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {
        Optional<User> user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<User> updateUserPartially(@PathVariable("id") String id, @RequestBody User currentUser) {
        LOG.info("Partially Updating User " + currentUser.getName());
        Optional<User> user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User usr = userService.updatePartially(currentUser, id);
        return new ResponseEntity<>(usr, HttpStatus.OK);
    }
}
