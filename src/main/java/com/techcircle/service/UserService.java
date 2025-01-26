package com.techcircle.service;

import com.techcircle.auth.JwtTokenProvider;
import com.techcircle.dto.AuthResponseDto;
import com.techcircle.dto.UserLoginDto;
import com.techcircle.dto.UserRegistrationDTO;
import com.techcircle.dto.UserResponseDto;
import com.techcircle.exception.UserAlreadyExistsException;
import com.techcircle.model.User;
import com.techcircle.repository.UserRepository;
import com.techcircle.utils.AuthUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public UserService(UserRepository userRepository,
                     JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public UserResponseDto registerUser(UserRegistrationDTO registrationDto) {
    if (userRepository.existsByEmail(registrationDto.getEmail())) {
      throw new UserAlreadyExistsException("Email already registered");
    }

    User user = new User();
    user.setEmail(registrationDto.getEmail());
    user.setPassword(AuthUtils.encode(registrationDto.getPassword()));
    user.setFullName(registrationDto.getFullName());
    user.setBio(registrationDto.getBio());
    User savedUser = userRepository.save(user); // Save user to database
    return convertToDto(savedUser);
  }

  public AuthResponseDto login(UserLoginDto loginDto) {
    User user = userRepository.findByEmail(loginDto.getEmail())
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    if (!AuthUtils.matches(loginDto.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    String authTokoen = jwtTokenProvider.generateToken(user.getEmail());
    UserResponseDto userDto = convertToDto(user);
    return new AuthResponseDto(authTokoen, userDto);
  }

  //TODO: Implement password reset functionality
  public void initiatePasswordReset(String email) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

   // String token = generatePasswordResetToken();
    // Store token with expiration in database
   // emailService.sendPasswordResetEmail(email, token);
  }

  public void resetPassword(String token, String newPassword) {
    // Validate token and update password
    // Implementation details
  }

  /**
   * Find user by email
   * @param email User's email
   * @return Optional of User
   */
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Find user by ID
   * @param userId User's unique identifier
   * @return Optional of User
   */
  public Optional<User> findById(String userId) {
    return userRepository.findById(userId);
  }

  /**
   * Delete user by ID
   * @param userId User's unique identifier
   */
  @Transactional
  public void deleteUser(String userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

    userRepository.delete(user);
  }

  private UserResponseDto convertToDto(User user) {
    UserResponseDto dto = new UserResponseDto(
      user.getId(),
      user.getEmail(),
      user.getFullName(),
      user.getBio(),
      user.getProfilePicture(),
      user.getCreatedAt()
    );
    return dto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
