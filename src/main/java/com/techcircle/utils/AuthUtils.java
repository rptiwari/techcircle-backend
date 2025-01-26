package com.techcircle.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthUtils {
  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public static String encode(CharSequence rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  public static boolean matches(CharSequence rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
