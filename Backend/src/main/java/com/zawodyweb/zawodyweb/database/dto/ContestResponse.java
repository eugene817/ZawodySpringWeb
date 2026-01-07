package com.zawodyweb.zawodyweb.database.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestResponse {
  private Long id;
  private String name;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String accessPassword;
  private boolean visible;
  private List<ProblemResponse> problems;
}
