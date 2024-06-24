package com.example.dndhub.services;

import com.example.dndhub.dtos.CurrentUserDto;
import com.example.dndhub.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public CurrentUserDto getUserById(int id) {
        return modelMapper.map(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found")), CurrentUserDto.class);
    }
}
