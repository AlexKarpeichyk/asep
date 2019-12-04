package com.asep.app.service;

import com.asep.app.entity.AuthUser;
import com.asep.app.entity.User;
import com.asep.app.exceptions.EmailNotFoundException;
import com.asep.app.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(User user);

    List<User> getUser();

    Optional<User> findById(String id) throws UserNotFoundException;

    User update(User user, String l);

    void deleteUserById(String userId);

    User updatePartially(User user, String id) throws UserNotFoundException;

    User getUserByEmail(AuthUser authUser) throws EmailNotFoundException;
}
