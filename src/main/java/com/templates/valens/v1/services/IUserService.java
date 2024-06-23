package com.templates.valens.v1.services;

import com.templates.valens.v1.dtos.requests.CreateUserDTO;
import com.templates.valens.v1.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User createUser(CreateUserDTO user);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
}
