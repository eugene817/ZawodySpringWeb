package com.zawodyweb.zawodyweb.database.repositories;

import com.zawodyweb.zawodyweb.database.models.Problem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
  List<Problem> findAllByContestId(Long contestId);
}
