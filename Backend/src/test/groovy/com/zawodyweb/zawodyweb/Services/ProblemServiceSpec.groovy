package com.zawodyweb.zawodyweb.services

import com.zawodyweb.zawodyweb.database.dto.ProblemCreateRequest
import com.zawodyweb.zawodyweb.database.dto.ProblemResponse
import com.zawodyweb.zawodyweb.database.mappers.ProblemMapper
import com.zawodyweb.zawodyweb.database.models.Contest
import com.zawodyweb.zawodyweb.database.models.Problem
import com.zawodyweb.zawodyweb.database.repositories.ContestRepository
import com.zawodyweb.zawodyweb.database.repositories.ProblemRepository
import com.zawodyweb.zawodyweb.exceptions.BusinessException
import spock.lang.Specification
import spock.lang.Subject

class ProblemServiceSpec extends Specification {

    def problemRepository = Mock(ProblemRepository)
    def contestRepository = Mock(ContestRepository)
    def problemMapper = Mock(ProblemMapper)

    @Subject
    def problemService = new ProblemService(problemRepository, contestRepository, problemMapper)

    def "should successfully add problem to contest"() {
        given: "a contest and a request"
        def contestId = 1L
        def request = new ProblemCreateRequest(name: "Sum", shortCode: "A")
        def contest = new Contest(id: contestId, name: "Contest 1")
        def problem = new Problem(name: "Sum")
        def response = new ProblemResponse(id: 10L, name: "Sum")

        when: "adding problem to contest"
        def result = problemService.addProblemToContest(contestId, request)

        then: "contest is looked up and problem is saved"
        1 * contestRepository.findById(contestId) >> Optional.of(contest)
        1 * problemMapper.toEntity(request) >> problem
        1 * problemRepository.save(_ as Problem) >> { Problem p ->
            assert p.contest == contest
            return p
        }
        1 * problemMapper.toDto(_) >> response
        result.id == 10L
    }

    def "should throw BusinessException when contest not found"() {
        given:
        def contestId = 999L
        contestRepository.findById(contestId) >> Optional.empty()

        when:
        problemService.addProblemToContest(contestId, new ProblemCreateRequest())

        then:
        def e = thrown(BusinessException)
        e.message == "Contest not found with id: 999"
    }
}