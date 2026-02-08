package com.zawodyweb.zawodyweb.database.mappers;

import com.zawodyweb.zawodyweb.database.dto.ProblemCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ProblemResponse;
import com.zawodyweb.zawodyweb.database.models.Problem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "contest", ignore = true)
  @Mapping(target = "testCases", ignore = true)
  @Mapping(target = "submissions", ignore = true)
  @Mapping(target = "problemLanguages", ignore = true)
  Problem toEntity(ProblemCreateRequest request);

  @Mapping(source = "contest.id", target = "contestId")
  ProblemResponse toDto(Problem problem);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "contest", ignore = true)
  @Mapping(target = "testCases", ignore = true)
  @Mapping(target = "submissions", ignore = true)
  @Mapping(target = "problemLanguages", ignore = true)
  void updateEntityFromDto(ProblemCreateRequest request, @MappingTarget Problem problem);
}
