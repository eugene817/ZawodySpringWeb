package com.zawodyweb.zawodyweb.database.mappers;

import com.zawodyweb.zawodyweb.database.dto.ContestCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.ContestResponse;
import com.zawodyweb.zawodyweb.database.dto.ProblemResponse;
import com.zawodyweb.zawodyweb.database.models.Contest;
import com.zawodyweb.zawodyweb.database.models.Problem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-05T19:02:02+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.8 (N/A)"
)
@Component
public class ContestMapperImpl implements ContestMapper {

    @Override
    public Contest toEntity(ContestCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Contest.ContestBuilder contest = Contest.builder();

        contest.name( request.getName() );
        contest.description( request.getDescription() );
        contest.startTime( request.getStartTime() );
        contest.endTime( request.getEndTime() );
        contest.visible( request.isVisible() );

        return contest.build();
    }

    @Override
    public ContestResponse toDto(Contest contest) {
        if ( contest == null ) {
            return null;
        }

        ContestResponse.ContestResponseBuilder contestResponse = ContestResponse.builder();

        contestResponse.id( contest.getId() );
        contestResponse.name( contest.getName() );
        contestResponse.description( contest.getDescription() );
        contestResponse.startTime( contest.getStartTime() );
        contestResponse.endTime( contest.getEndTime() );
        contestResponse.accessPassword( contest.getAccessPassword() );
        contestResponse.visible( contest.isVisible() );
        contestResponse.problems( problemListToProblemResponseList( contest.getProblems() ) );

        return contestResponse.build();
    }

    protected ProblemResponse problemToProblemResponse(Problem problem) {
        if ( problem == null ) {
            return null;
        }

        String name = null;

        name = problem.getName();

        ProblemResponse problemResponse = new ProblemResponse( name );

        return problemResponse;
    }

    protected List<ProblemResponse> problemListToProblemResponseList(List<Problem> list) {
        if ( list == null ) {
            return null;
        }

        List<ProblemResponse> list1 = new ArrayList<ProblemResponse>( list.size() );
        for ( Problem problem : list ) {
            list1.add( problemToProblemResponse( problem ) );
        }

        return list1;
    }
}
