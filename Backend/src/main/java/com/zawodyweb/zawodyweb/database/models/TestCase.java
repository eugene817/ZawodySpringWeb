package com.zawodyweb.zawodyweb.database.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_cases")
public class TestCase implements Comparable<TestCase> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id")
  private Problem problem;

  @Lob
  @Column(nullable = false)
  private String inputData;

  @Lob
  @Column(nullable = false)
  private String expectedOutput;

  @Column(length = 32)
  private String orderTag; // for sorting (A1, A2, 001 ...)

  @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL)
  private List<SubmissionResult> results;

  @Override
  public int compareTo(TestCase o) {
    return this.id.compareTo(o.id);
  }
}
