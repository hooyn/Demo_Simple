package solution.demo.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solution.demo.domain.account.Account;
import solution.demo.domain.account.repository.AccountRepository;
import solution.demo.domain.problem.Problem;
import solution.demo.domain.problem.dto.AccountFormDto;
import solution.demo.domain.problem.dto.CreateProblemRequestDto;
import solution.demo.domain.problem.dto.ReadDetailProblemResponseDto;
import solution.demo.domain.problem.dto.ReadProblemResponseDto;
import solution.demo.domain.problem.repository.ProblemRepository;
import solution.demo.global.exception.CustomException;
import solution.demo.global.exception.ErrorCode;
import solution.demo.global.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static solution.demo.global.util.UUIDUtil.StringToUUID;
import static solution.demo.global.util.UUIDUtil.UUIDToString;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final AccountRepository accountRepository;
    private final ProblemRepository problemRepository;

    //C
    public void createProblem(CreateProblemRequestDto dto) {
        Optional<Account> account = accountRepository.findById(StringToUUID(dto.getUuid()));
        if(account.isEmpty())
            throw new CustomException(ErrorCode.BAD_REQUEST, "등록되어 있지 않은 사용자입니다.");

        if(dto.getTokenForSolve()>10 || dto.getTokenForSolve()<0)
            throw new CustomException(ErrorCode.BAD_REQUEST, "토큰의 범위는 0부터 10까지 입니다.");


        Problem problem = Problem.builder()
                .category(dto.getCategory())
                .text(dto.getText())
                .tokenForSolve(dto.getTokenForSolve())
                .account(account.get())
                .build();
        problemRepository.save(problem);
    }

    //R
    public List<ReadProblemResponseDto> readProblems() {
        List<Problem> problems = problemRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        List<ReadProblemResponseDto> response = new ArrayList<>();

        for (Problem problem : problems) {
            AccountFormDto account = AccountFormDto.builder()
                    .uuid(UUIDToString(problem.getAccount().getUuid()))
                    .username(problem.getAccount().getUsername())
                    .userId(problem.getAccount().getUserId())
                    .email(problem.getAccount().getEmail())
                    .emailCheck(problem.getAccount().getEmailCheck())
                    .role(problem.getAccount().getRole().name())
                    .build();

            response.add(ReadProblemResponseDto.builder()
                    .problem_uuid(UUIDToString(problem.getProblem_uuid()))
                    .category(problem.getCategory().name())
                    .viewCount(problem.getViewCount())
                    .createdDate(DateUtil.getDateToString(problem.getCreatedDate()))
                    .account(account)
                    .build());
        }

        return response;
    }

    //DR
    @Transactional
    public ReadDetailProblemResponseDto readDetailProblem(String uuid) {
        Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(uuid));

        if(rawProblem.isEmpty())
            throw new CustomException(ErrorCode.BAD_REQUEST, "등록되어 있지 않은 불만입니다.");

        Problem problem = rawProblem.get();
        problem.plusViewCount();

        AccountFormDto account = AccountFormDto.builder()
                .uuid(UUIDToString(problem.getAccount().getUuid()))
                .username(problem.getAccount().getUsername())
                .userId(problem.getAccount().getUserId())
                .email(problem.getAccount().getEmail())
                .emailCheck(problem.getAccount().getEmailCheck())
                .role(problem.getAccount().getRole().name())
                .build();

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

    //U

    //D
}
