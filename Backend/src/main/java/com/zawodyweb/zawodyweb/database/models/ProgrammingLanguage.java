package com.zawodyweb.zawodyweb.database.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "languages")
public class ProgrammingLanguage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 40, unique = true)
  private String name; // C++, Java, Python, Go...

  @Column(nullable = false, length = 8)
  private String extension; // cpp, java, py, go

  @Lob private String config; // properties/JSON with compilation adn runing config

  @OneToMany(mappedBy = "language")
  private List<Submission> submissions;

  @OneToMany(mappedBy = "language")
  private Set<ProblemLanguage> problemLanguages;
}
