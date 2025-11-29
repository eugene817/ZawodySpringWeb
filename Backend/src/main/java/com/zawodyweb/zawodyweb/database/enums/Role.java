package com.zawodyweb.zawodyweb.database.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_USER,
  ROLE_PROFESSOR,
  ROLE_MODERATOR;

  @Override
  public String getAuthority() {
    return name();
  }
}
