package rca.ne.java.v1.services;

import rca.ne.java.v1.dtos.requests.CreateUserDTO;
import rca.ne.java.v1.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User createAdmin(CreateUserDTO user);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
    public  boolean isUserLoggedIn();
    public User getLoggedInUser();
    public boolean isNotUnique(User user);
}
