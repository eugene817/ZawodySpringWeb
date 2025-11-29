package com.zawodyweb.zawodyweb.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zawodyweb.zawodyweb.database.enums.Role;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 64, unique = true)
  private String login;

  @Column(nullable = false, length = 255)
  private String passwordHash;

  @Column(length = 40)
  private String firstName;

  @Column(length = 40)
  private String lastName;

  @Column(length = 120, unique = true)
  private String email;

  @Column(nullable = false, updatable = false)
  private Instant registeredAt = Instant.now();

  private Instant lastLoginAt;

  @Column(nullable = false)
  private boolean enabled = true;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Submission> submissions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }
}
