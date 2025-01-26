package com.techcircle.model;

public enum UserStatus {
  ACTIVE("Active", "Account is fully functional"),
  PENDING("Pending", "Account waiting for email verification"),
  INACTIVE("Inactive", "Account is temporarily disabled"),
  BLOCKED("Blocked", "Account is blocked by administrator"),
  DELETED("Deleted", "Account has been deleted");

  private final String displayName;
  private final String description;

  UserStatus(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public boolean isAccessible() {
    return this == ACTIVE;
  }

  public boolean isModifiable() {
    return this != DELETED;
  }

  public static UserStatus fromString(String status) {
    try {
      return UserStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unknown user status: " + status);
    }
  }
}
