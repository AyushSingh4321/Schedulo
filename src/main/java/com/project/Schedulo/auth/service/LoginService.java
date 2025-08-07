package com.project.Schedulo.auth.service;

import com.project.Schedulo.auth.dto.LoginRequestDto;
import com.project.Schedulo.user.model.UserModel;
import com.project.Schedulo.user.repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> loginUserAndReturnToken(@Valid LoginRequestDto request) {
        Optional<UserModel> optionalUser = userRepo.findByEmail(request.getIdentifier());
        if (optionalUser.isEmpty()) {
            optionalUser = userRepo.findByUsername(request.getIdentifier());
        }

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid email/username");
        }

        UserModel user = optionalUser.get();

//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return ResponseEntity.badRequest().body("Invalid password");
//        }
        if (!request.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail()); // or user.getUsername()
        return ResponseEntity.ok(token);
    }
}
