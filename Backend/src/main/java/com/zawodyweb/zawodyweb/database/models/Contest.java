package com.zawodyweb.zawodyweb.database.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "contests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
  @Builder.Default
  private boolean visible = true;

  @Nullable
  @OneToMany(mappedBy = "contest")
  private List<Problem> problems;
}
