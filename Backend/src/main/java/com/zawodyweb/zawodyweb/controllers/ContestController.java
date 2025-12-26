package com.zawodyweb.zawodyweb.controllers;

import com.zawodyweb.zawodyweb.database.dto.ContestCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ContestResponse;
import com.zawodyweb.zawodyweb.services.ContestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contests")
@SecurityRequirement(name = "bearerAuth")
public class ContestController {

  private final ContestService contestService;

  public ContestController(ContestService contestService) {
    this.contestService = contestService;
  }

  @PostMapping
  public ResponseEntity<String> createContest(@RequestBody ContestCreateRequest request) {
    contestService.createContest(request);
    return ResponseEntity.ok("Success");
  }

  @GetMapping
  public ResponseEntity<ContestResponse> getContest(@RequestParam Long id) {
    return ResponseEntity.ok(contestService.getContest(id));
  }

  @GetMapping("/all")
  public ResponseEntity<List<ContestResponse>> getAllContests() {
    return ResponseEntity.ok(contestService.getAllContests());
  }
}
