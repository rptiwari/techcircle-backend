package com.techcircle.controller;

import com.techcircle.dto.AuthResponseDto;
import com.techcircle.dto.UserLoginDto;
import com.techcircle.dto.UserRegistrationDTO;
import com.techcircle.dto.UserResponseDto;
import com.techcircle.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/auth/register")
  public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDto) {
    log.info("Attempting to register new user with email: {}", registrationDto.getEmail());

    UserResponseDto response = userService.registerUser(registrationDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/auth/login")
  public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserLoginDto loginDto) {
    log.info("Login attempt for user: {}", loginDto.getEmail());

    AuthResponseDto response = userService.login(loginDto);
    return ResponseEntity.ok(response);
  }
}

