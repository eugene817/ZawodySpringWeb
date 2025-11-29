package com.zawodyweb.zawodyweb.database.models;

import jakarta.persistence.*;

@Entity
@Table(name = "problem_languages")
public class ProblemLanguage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id")
  private Problem problem;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  private ProgrammingLanguage language;
}
