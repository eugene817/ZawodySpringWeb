package com.zawodyweb.zawodyweb.database.repositories;

import com.zawodyweb.zawodyweb.database.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByContestId(Long contestId);
}