package solution.demo.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solution.demo.domain.account.Account;
import solution.demo.domain.account.repository.AccountRepository;
import solution.demo.domain.problem.Problem;
import solution.demo.domain.problem.dto.*;
import solution.demo.domain.problem.repository.ProblemRepository;
import solution.demo.global.exception.CustomException;
import solution.demo.global.exception.ErrorCode;
import solution.demo.global.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static solution.demo.global.exception.ErrorCode.BAD_REQUEST;
import static solution.demo.global.util.UUIDUtil.StringToUUID;
import static solution.demo.global.util.UUIDUtil.UUIDToString;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final AccountRepository accountRepository;
    private final ProblemRepository problemRepository;

    //C
    public void createProblem(CreateProblemRequestDto dto) {
        try{
            Optional<Account> account = accountRepository.findById(StringToUUID(dto.getUuid()));
            if(account.isEmpty())
                throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 사용자입니다.");

            if(dto.getTokenForSolve()>10 || dto.getTokenForSolve()<0)
                throw new CustomException(BAD_REQUEST, "토큰의 범위는 0부터 10까지 입니다.");


            Problem problem = Problem.builder()
                    .category(dto.getCategory())
                    .text(dto.getText())
                    .tokenForSolve(dto.getTokenForSolve())
                    .account(account.get())
                    .build();
            problemRepository.save(problem);
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    //R
    public List<ReadProblemResponseDto> readProblems() {
        try {
            List<Problem> problems = problemRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
            List<ReadProblemResponseDto> response = new ArrayList<>();

            for (Problem problem : problems)
                response.add(buildReadProblemResponse(problem, buildAccountForm(problem)));

            return response;
        }  catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    private static ReadProblemResponseDto buildReadProblemResponse(Problem problem, AccountFormDto account) {
        return ReadProblemResponseDto.builder()
                .problem_uuid(UUIDToString(problem.getProblem_uuid()))
                .category(problem.getCategory().name())
                .viewCount(problem.getViewCount())
                .createdDate(DateUtil.getDateToString(problem.getCreatedDate()))
                .account(account)
                .build();
    }

    //DR
    @Transactional
    public ReadDetailProblemResponseDto readDetailProblem(String uuid) {
        try {
            Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(uuid));

            if(rawProblem.isEmpty())
                throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 불만입니다.");

            Problem problem = rawProblem.get();
            problem.plusViewCount();

            return buildDetailProblemResponse(problem, buildAccountForm(problem));
        }  catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    private static ReadDetailProblemResponseDto buildDetailProblemResponse(Problem problem, AccountFormDto account) {
        return ReadDetailProblemResponseDto.builder()
                .problem_uuid(UUIDToString(problem.getProblem_uuid()))
                .category(problem.getCategory().name())
                .text(problem.getText())
                .viewCount(problem.getViewCount())
                .tokenForSolve(problem.getTokenForSolve())
                .createdDate(DateUtil.getDateToString(problem.getCreatedDate()))
                .account(account)
                .build();
    }

    private static AccountFormDto buildAccountForm(Problem problem) {
        return AccountFormDto.builder()
                .uuid(UUIDToString(problem.getAccount().getUuid()))
                .username(problem.getAccount().getUsername())
                .userId(problem.getAccount().getUserId())
                .email(problem.getAccount().getEmail())
                .emailCheck(problem.getAccount().getEmailCheck())
                .role(problem.getAccount().getRole().name())
                .build();
    }

    //U
    @Transactional
    public void updateProblem(UpdateProblemDto dto) {
        try {
            Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(dto.getProblem_uuid()));
            if(rawProblem.isEmpty())
                throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 사용자입니다.");

            Problem problem = rawProblem.get();
            problem.updateProblem(dto);
        }  catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    //D
    public void deleteProblem(String problem_uuid){
        try {
            problemRepository.deleteById(StringToUUID(problem_uuid));
        }  catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }
}
