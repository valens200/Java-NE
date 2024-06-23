package com.templates.valens.v1.services.serviceImpl;
import com.templates.valens.v1.dtos.requests.CreateUserDTO;
import com.templates.valens.v1.exceptions.BadRequestException;
import com.templates.valens.v1.exceptions.NotFoundException;
import com.templates.valens.v1.models.User;
import com.templates.valens.v1.repositories.IUserRepository;
import com.templates.valens.v1.services.IUserService;
import com.templates.valens.v1.utils.ExceptionsUtils;
import com.templates.valens.v1.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        try {
            return userRepository.findById(id).orElseThrow(() ->new  NotFoundException("The user with the provided id doesn't exist"));
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    @Override
    public User createUser(CreateUserDTO dto) {
        try {
            if(userRepository.existsByEmail(dto.getEmail())) throw new BadRequestException("The user with the provided email already exists");
            User user = Mapper.getUserFromDTO(dto);
            return userRepository.save(user);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }

    @Override
    public User updateUser(UUID id, User user) {
        // Check if the user with given id exists
        if (userRepository.existsById(id)) {
            user.setId(id); // Ensure the correct ID is set for update
            return userRepository.save(user);
        }
        return null; // Return null or throw exception based on your business logic
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
