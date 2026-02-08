package com.zawodyweb.zawodyweb.database.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemCreateRequest {
  @NotBlank(message = "Name is mandatory")
  private String name;

  private String shortCode;
  private String statement;
  private Integer memoryLimitMb;
  private Integer codeSizeLimitKb;
  private Integer timeLimitMs;
  private Integer maxPoints;
  @Builder.Default private boolean visibleInRanking = true;
}
