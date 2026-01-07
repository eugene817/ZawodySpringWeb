package com.zawodyweb.zawodyweb.database.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestCreateRequest {
  private String name;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String accessPasssword;
  private boolean visible;
}
