package com.techcircle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserResponseDto {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("bio")
  private String bio;

  @JsonProperty("profile_picture")
  private String profilePicture;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;
}
