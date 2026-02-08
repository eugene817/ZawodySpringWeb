package com.zawodyweb.zawodyweb.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemResponse {
  private Long id;
  private String name;
  private String shortCode;
  private String statement;
  private Integer memoryLimitMb;
  private Integer codeSizeLimitKb;
  private Integer timeLimitMs;
  private Integer maxPoints;
  private Long contestId; // ID контеста вместо всей сущности
  private boolean visibleInRanking;
}
