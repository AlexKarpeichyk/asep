package com.asep.app.service;


import com.asep.app.entity.AuthUser;
import com.asep.app.entity.User;
import com.asep.app.repository.UserRepository;
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
        for(User u: userRepository.findAll()) {
            if(u.getName() != null && user.getName() != null) {
                if (u.getName().equalsIgnoreCase(user.getName())) {
                    return false;
                }
            }
        }

        userRepository.save(user);

        return true;
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

    public User getUserByEmail(AuthUser authUser) {
        for(User u: userRepository.findAll()) {
            if (u.getPassword() != null && u.getName() != null) {
                if (u.getPassword().equalsIgnoreCase(authUser.getPassword()) && u.getName().equalsIgnoreCase(authUser.getName())) {
                    return u;
                }
            }
        }

        return null;
    }


    public void deleteUserById(String id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

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