package com.zawodyweb.zawodyweb.database.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "problems")
public class Problem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 80)
  private String name;

  @Column(nullable = false, length = 10)
  private String shortCode; // A, B, SUMA...

  @Lob
  @Column(nullable = false)
  private String statement; // (Markdown/HTML)

  @Column private Integer memoryLimitMb;

  @Column private Integer codeSizeLimitKb;

  @Column private Integer timeLimitMs;

  @Column private Integer maxPoints;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "contest_id")
  private Contest contest;

  @OneToMany(mappedBy = "problem")
  private List<TestCase> testCases;

  @OneToMany(mappedBy = "problem")
  private List<Submission> submissions;

  @OneToMany(mappedBy = "problem")
  private Set<ProblemLanguage> problemLanguages;

  @Column(nullable = false)
  private boolean visibleInRanking = true;
}
