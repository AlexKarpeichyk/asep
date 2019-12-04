package com.asep.app.service;


import com.asep.app.entity.AuthUser;
import com.asep.app.entity.User;
import com.asep.app.exceptions.EmailNotFoundException;
import com.asep.app.exceptions.UserNotFoundException;
import com.asep.app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public boolean createUser(User user) {
        for (User u : userRepository.findAll()) {
            if (u.getName() != null && user.getName() != null) {
                if (u.getName().equalsIgnoreCase(user.getName())) {
                    return false;
                }
            }
        }
        userRepository.save(user);
        return true;
    }

    public List<User> getUser() {
        Iterable<User> user = userRepository.findAll();
        user.forEach(value -> {
            try {
                System.out.println("The Values from DB or not" + new ObjectMapper().writeValueAsString(value));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findById(String id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            try {
                System.out.println("Getting Mock User Via |Get User By Id Test Case|" + new ObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .writeValueAsString(
                                user.get()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return user;
        } else {
            throw new UserNotFoundException("USER NOT FOUND");
        }
    }

    public User update(User user, String l) {
        return userRepository.save(user);
    }

    public User getUserByEmail(AuthUser authUser) throws EmailNotFoundException {
        for (User u : userRepository.findAll()) {
            if (u.getPassword() != null && u.getName() != null) {
                if (u.getPassword().equalsIgnoreCase(authUser.getPassword()) && u.getName().equalsIgnoreCase(authUser.getName())) {
                    try {
                        System.out.println("Getting Mock User Via |Get User By Id Test Case|" + new ObjectMapper()
                                .enable(SerializationFeature.INDENT_OUTPUT)
                                .writeValueAsString(u));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return u;
                }
            }
        }
        throw new EmailNotFoundException("Could not find the User Email Id" + authUser.getName());
    }

    public void deleteUserById(String id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    public User updatePartially(User user, String id) throws UserNotFoundException {
        Optional<User> usr = findById(id);
        usr.get().setCity(user.getCity());
        usr.get().setName(user.getName());
        usr.get().setLocation(user.getLocation());
        usr.get().setCountry(user.getCountry());
        usr.get().setDevice(user.getDevice());
        return userRepository.save(usr.get());
    }
}