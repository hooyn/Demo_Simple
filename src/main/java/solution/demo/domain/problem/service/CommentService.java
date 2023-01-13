package solution.demo.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import solution.demo.domain.account.Account;
import solution.demo.domain.account.repository.AccountRepository;
import solution.demo.domain.problem.Comment;
import solution.demo.domain.problem.Problem;
import solution.demo.domain.problem.dto.CreateCommentRequestDto;
import solution.demo.domain.problem.dto.ReadCommentResponseDto;
import solution.demo.domain.problem.repository.CommentRepository;
import solution.demo.domain.problem.repository.ProblemRepository;
import solution.demo.global.exception.CustomException;
import solution.demo.global.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static solution.demo.global.exception.ErrorCode.BAD_REQUEST;
import static solution.demo.global.util.UUIDUtil.StringToUUID;
import static solution.demo.global.util.UUIDUtil.UUIDToString;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AccountRepository accountRepository;
    private final ProblemRepository problemRepository;
    private final CommentRepository commentRepository;

    //C
    public void createComment(CreateCommentRequestDto dto) {
        try {
            Optional<Account> rawAccount = accountRepository.findById(StringToUUID(dto.getUuid()));
            if(rawAccount.isEmpty())
                throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 사용자입니다.");

            Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(dto.getProblem_uuid()));
            if(rawProblem.isEmpty())
                throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 불만입니다.");

            Account account = rawAccount.get();
            Problem problem = rawProblem.get();
            boolean isWriter = account.getUuid().equals(problem.getAccount().getUuid());

            Comment comment = Comment.builder()
                    .commenter(account)
                    .problem(problem)
                    .content(dto.getContent())
                    .isWriter(isWriter)
                    .build();

            commentRepository.save(comment);
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    //R
    public List<ReadCommentResponseDto> readComment(String problem_uuid) {
        Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(problem_uuid));
        if(rawProblem.isEmpty())
            throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 불만입니다.");

        Problem problem = rawProblem.get();
        List<Comment> comments = commentRepository.findCommentByProblem(problem);
        List<ReadCommentResponseDto> response = new ArrayList<>();

        for (Comment comment : comments)
            response.add(buildReadCommentResponse(comment));

        return response;
    }

    private static ReadCommentResponseDto buildReadCommentResponse(Comment comment) {
        return ReadCommentResponseDto.builder()
                .comment_uuid(UUIDToString(comment.getComment_uuid()))
                .commenter(comment.getCommenter())
                .content(comment.getContent())
                .isWriter(comment.getIsWriter())
                .isAdopted(comment.getIsAdopted())
                .createdDate(DateUtil.getDateToString(comment.getCreatedDate()))
                .build();
    }

    //U

    //D

    //Adopted
    public void adoptComment(String problem_uuid) {
        try {
            Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(problem_uuid));
            if(rawProblem.isEmpty())
                throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 불만입니다.");

            // TODO: 2023-01-13 Comment 채택 처리
            rawProblem.get().changeIsSolved();
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }
}
