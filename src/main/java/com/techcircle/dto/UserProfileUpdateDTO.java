package com.techcircle.dto;

import java.util.Set;

public class UserProfileUpdateDTO {
  private String fullName;
  private String profilePictureUrl;
  private String bio;
  private Set<String> skills;

  public String getFullName() {
    return fullName;
  }

  public UserProfileUpdateDTO setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getProfilePictureUrl() {
    return profilePictureUrl;
  }

  public UserProfileUpdateDTO setProfilePictureUrl(String profilePictureUrl) {
    this.profilePictureUrl = profilePictureUrl;
    return this;
  }

  public String getBio() {
    return bio;
  }

  public UserProfileUpdateDTO setBio(String bio) {
    this.bio = bio;
    return this;
  }

  public Set<String> getSkills() {
    return skills;
  }

  public UserProfileUpdateDTO setSkills(Set<String> skills) {
    this.skills = skills;
    return this;
  }
}
