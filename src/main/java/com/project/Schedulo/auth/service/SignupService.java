package com.project.Schedulo.auth.service;

import com.project.Schedulo.auth.dto.SignupRequestDto;
import com.project.Schedulo.exception.UserAlreadyExistsException;
import com.project.Schedulo.user.model.UserModel;
import com.project.Schedulo.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService
{
    @Autowired
    private UserRepo repo;
    @Autowired
    private JwtService jwtService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public String registerUserAndReturnToken(SignupRequestDto dto) {
        if (repo.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        if (repo.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use.");
        }
        try {
            UserModel user = new UserModel();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
//            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setPassword(dto.getPassword());
            repo.save(user);
            return jwtService.generateToken(user.getEmail());

        } catch (Exception e) {
            throw new RuntimeException("Error generating token: " + e.getMessage());
        }
    }

}
