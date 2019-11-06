package com.asep.spring.service;

import com.asep.spring.entity.AuthUser;
import com.asep.spring.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    List<User> getUser();

    Optional<User> findById(String id);

    User update(User user, String l);

    public void deleteUserById(String userId);

    User updatePartially(User user, String id);

    User getUserByEmail(AuthUser authUser);
}
