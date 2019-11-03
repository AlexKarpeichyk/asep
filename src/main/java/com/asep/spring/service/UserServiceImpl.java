package com.asep.spring.service;

import com.asep.spring.entity.User;
import com.asep.spring.repository.UserRepository;
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

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUser() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User update(User user, String l) {
        return userRepository.save(user);
    }

/*
    public void deleteUserById(String id) {

        userRepository.delete(id);
    }
*/

    public User updatePartially(User user, String id) {
        Optional<User> usr = findById(id);
        usr.get().setCity(user.getCity());
        usr.get().setName(user.getName());
        usr.get().setLocation(user.getLocation());
        usr.get().setCountry(user.getCountry());
        usr.get().setDevice(user.getDevice());
        return userRepository.save(usr.get());
    }


}
