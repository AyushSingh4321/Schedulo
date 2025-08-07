package com.project.Schedulo.auth.controller;

import com.project.Schedulo.auth.dto.SignupRequestDto;
import com.project.Schedulo.auth.service.SignupService;
import com.project.Schedulo.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SignupController {
    @Autowired
    SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto userDetails) {
        try {
            String token = signupService.registerUserAndReturnToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("EXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed: " + e.getMessage());
        }
    }
}
