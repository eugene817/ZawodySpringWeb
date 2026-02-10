package com.zawodyweb.zawodyweb.controllers;

import com.zawodyweb.zawodyweb.database.dto.ProblemCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ProblemResponse;
import com.zawodyweb.zawodyweb.services.ProblemService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

  private final ProblemService problemService;

  @PostMapping("/contest/{contestId}")
  public ResponseEntity<ProblemResponse> createProblem(
      @PathVariable Long contestId, @Valid @RequestBody ProblemCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(problemService.addProblemToContest(contestId, request));
  }

  @Transactional(readOnly = true)
  @GetMapping("/contest/{contestId}")
  public ResponseEntity<List<ProblemResponse>> getContestProblems(@PathVariable Long contestId) {
    return ResponseEntity.ok(problemService.getProblemsByContest(contestId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProblemResponse> getProblem(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblem(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProblemResponse> updateProblem(
      @PathVariable Long id, @RequestBody ProblemCreateRequest request) {
    return ResponseEntity.ok(problemService.updateProblem(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
    problemService.deleteProblem(id);
    return ResponseEntity.noContent().build();
  }
}
