package com.zawodyweb.zawodyweb.database.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "contests")
@Data
public class Contest {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false, length = 120)
  private String name;

  @Column(length = 2000)
  private String description;

  @Column private LocalDateTime startTime;

  @Column private LocalDateTime endTime;

  @Column(length = 64)
  private String accessPassword; // null = public

  @Column(nullable = false)
  private boolean visible = true;

  @OneToMany(mappedBy = "contest")
  private List<Problem> problems;
}
