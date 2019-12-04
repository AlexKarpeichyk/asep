package com.asep.app.controller;

import com.asep.app.entity.AuthUser;
import com.asep.app.entity.User;
import com.asep.app.exceptions.UserNotFoundException;
import com.asep.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserById(@PathVariable("id") String id) throws UserNotFoundException {
        LOG.info("Fetching User with id " + id);
        Optional<User> user = userService.findById(id);
        if (user.equals(Optional.empty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserNotFoundException("USER DOES NOT EXIST"));
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity createUser(@RequestBody String jsonUser) {
        try {
            User user = mapper.readValue(jsonUser, User.class);
            LOG.info("Creating User " + user.getName());
            boolean created = userService.createUser(user);
            if (created) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse body");
        }

    }

    @GetMapping(value = "/get", headers = "Accept=application/json")
    public List<User> getAllUser() {
        List<User> tasks = userService.getUser();
        return tasks;
    }

    @PostMapping(value = "/getByEmail", headers = "Accept=application/json")
    public ResponseEntity getUserByEmail(@RequestBody String jsonAuthUser) {
        try {
            AuthUser aUser = mapper.readValue(jsonAuthUser, AuthUser.class);
            User user = userService.getUserByEmail(aUser);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User doesn't exists");
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse body");
        }
    }

    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    public ResponseEntity updateUser(@RequestBody String jsonCurrentUser, @PathVariable("id") String id) {
        try {
            User currentUser = mapper.readValue(jsonCurrentUser, User.class);
            LOG.info("Updating User " + currentUser.getName());
            Optional<User> user = userService.findById(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userService.update(currentUser, currentUser.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse body");
        }

    }


    @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") String id) throws UserNotFoundException {
        Optional<User> user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}", headers = "Accept=application/json")

    public ResponseEntity updateUserPartially(@PathVariable("id") String id, @RequestBody String jsonCurrentUser) {
        try {
            User currentUser = mapper.readValue(jsonCurrentUser, User.class);
            LOG.info("Partially Updating User " + currentUser.getName());
            Optional<User> user = userService.findById(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            User usr = userService.updatePartially(currentUser, id);
            return new ResponseEntity<>(usr, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse body");
        }
    }
}