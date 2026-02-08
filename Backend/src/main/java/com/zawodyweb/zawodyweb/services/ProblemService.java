package com.zawodyweb.zawodyweb.services;


import com.zawodyweb.zawodyweb.database.dto.ProblemCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ProblemResponse;
import com.zawodyweb.zawodyweb.database.mappers.ProblemMapper;
import com.zawodyweb.zawodyweb.database.models.Contest;
import com.zawodyweb.zawodyweb.database.models.Problem;
import com.zawodyweb.zawodyweb.database.repositories.ContestRepository;
import com.zawodyweb.zawodyweb.database.repositories.ProblemRepository;
import com.zawodyweb.zawodyweb.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ContestRepository contestRepository;
    private final ProblemMapper problemMapper;

    @Transactional
    public ProblemResponse addProblemToContest(Long contestId, ProblemCreateRequest request) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new BusinessException("Contest not found with id: " + contestId));

        Problem problem = problemMapper.toEntity(request);
        problem.setContest(contest);

        return problemMapper.toDto(problemRepository.save(problem));
    }

    public ProblemResponse getProblem(Long id) {
        return problemRepository.findById(id)
                .map(problemMapper::toDto)
                .orElseThrow(() -> new BusinessException("Problem with id " + id + " not found"));
    }

    public List<ProblemResponse> getProblemsByContest(Long contestId) {
        if (!contestRepository.existsById(contestId)) {
            throw new BusinessException("Contest not found with id: " + contestId);
        }
        return problemRepository.findAllByContestId(contestId).stream()
                .map(problemMapper::toDto)
                .toList();
    }

    @Transactional
    public ProblemResponse updateProblem(Long id, ProblemCreateRequest request) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Problem not found"));

        problemMapper.updateEntityFromDto(request, problem);
        return problemMapper.toDto(problemRepository.save(problem));
    }

    @Transactional
    public void deleteProblem(Long id) {
        if (!problemRepository.existsById(id)) {
            throw new BusinessException("Cannot delete: Problem not found");
        }
        problemRepository.deleteById(id);
    }
}
