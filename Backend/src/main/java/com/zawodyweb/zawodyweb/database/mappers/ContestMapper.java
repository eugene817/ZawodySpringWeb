package com.zawodyweb.zawodyweb.database.mappers;

import com.zawodyweb.zawodyweb.database.dto.ContestCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ContestResponse;
import com.zawodyweb.zawodyweb.database.models.Contest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContestMapper {

  Contest toEntity(ContestCreateRequest request);

  ContestResponse toDto(Contest contest);
}
