package com.zawodyweb.zawodyweb.services;

import com.zawodyweb.zawodyweb.database.dto.ContestCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ContestResponse;
import com.zawodyweb.zawodyweb.database.mappers.ContestMapper;
import com.zawodyweb.zawodyweb.database.models.Contest;
import com.zawodyweb.zawodyweb.database.repositories.ContestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ContestService {

  private final ContestMapper contestMapper;
  private final ContestRepository contestRepository;

  public ContestService(ContestMapper contestMapper, ContestRepository contestRepository) {
    this.contestMapper = contestMapper;
    this.contestRepository = contestRepository;
  }

  public void createContest(ContestCreateRequest request) {
    Contest contest = contestMapper.toEntity(request);
    contestRepository.save(contest);
  }

  public List<ContestResponse> getAllContests() {
    return contestRepository.findAll().stream().map(contestMapper::toDto).toList();
  }

  public ContestResponse getContest(Long id) {
    Optional<Contest> contest = contestRepository.findById(id);
    if (contest.isPresent()) {
      return contestMapper.toDto(contest.get());
    }
    return new ContestResponse();
  }
}
