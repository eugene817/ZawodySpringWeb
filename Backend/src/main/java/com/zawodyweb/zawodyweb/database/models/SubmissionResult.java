package com.zawodyweb.zawodyweb.database.models;

import com.zawodyweb.zawodyweb.database.enums.TestStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "submission_results")
public class SubmissionResult implements Comparable<SubmissionResult> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "submission_id")
  private Submission submission;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "test_case_id")
  private TestCase testCase;

  @Column private Integer points;

  @Column private Integer runtimeMs;

  @Column private Integer memoryKb;

  @Enumerated(EnumType.STRING)
  @Column(length = 32)
  private TestStatus status; // OK, WA, TLE, MLE, RE...

  @Lob private String notes;

  @Override
  public int compareTo(SubmissionResult o) {
    return this.id.compareTo(o.id);
  }
}
