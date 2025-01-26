package com.techcircle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserLoginDto {
  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;

  public @NotNull @Email String getEmail() {
    return email;
  }

  public UserLoginDto setEmail(@NotNull @Email String email) {
    this.email = email;
    return this;
  }

  public @NotNull String getPassword() {
    return password;
  }

  public UserLoginDto setPassword(@NotNull String password) {
    this.password = password;
    return this;
  }
}
