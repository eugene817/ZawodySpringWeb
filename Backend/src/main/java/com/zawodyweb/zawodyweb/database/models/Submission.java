package com.zawodyweb.zawodyweb.database.models;

import com.zawodyweb.zawodyweb.database.enums.SubmissionState;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.SortedSet;

@Entity
@Table(name = "submissions")
public class Submission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Instant submittedAt = Instant.now();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private SubmissionState state; // NEW, QUEUED, RUNNING, ACCEPTED, WA, ...

  @Lob
  @Column(nullable = false)
  private String sourceCode;

  @Column(length = 255)
  private String filename;

  @Column(length = 45)
  private String clientIp;

  @Column(nullable = false)
  private boolean visibleInRanking = true;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "problem_id")
  private Problem problem;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "language_id")
  private ProgrammingLanguage language;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
  private SortedSet<SubmissionResult> results;
}
