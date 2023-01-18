package solution.demo.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import solution.demo.domain.account.Account;
import solution.demo.domain.account.repository.AccountRepository;
import solution.demo.domain.problem.Comment;
import solution.demo.domain.problem.Problem;
import solution.demo.domain.problem.dto.*;
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
            Account account = validationAccount(dto.getUuid());
            Problem problem = validationProblem(dto.getProblem_uuid());

            boolean isWriter = account.getUuid().equals(problem.getAccount().getUuid());
            Comment comment = buildComment(dto, account, problem, isWriter);

            commentRepository.save(comment);
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    private static Comment buildComment(CreateCommentRequestDto dto, Account account, Problem problem, boolean isWriter) {
        return Comment.builder()
                .commenter(account)
                .problem(problem)
                .content(dto.getContent())
                .isWriter(isWriter)
                .build();
    }

    //R
    public List<ReadCommentResponseDto> readComment(String problem_uuid) {
        Problem problem = validationProblem(problem_uuid);

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
    @Transactional
    public void updateComment(UpdateCommentRequestDto dto) {
        try {
            Account account = validationAccount(dto.getUuid());
            Comment comment = validationComment(dto.getComment_uuid());

            confirmCommentAuth(account, comment);


            if(StringUtils.hasText(dto.getComment()))
                comment.updateContent(dto.getComment());
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    //D
    public void deleteComment(DeleteCommentRequestDto dto) {
        try {
            Account account = validationAccount(dto.getUuid());
            Comment comment = validationComment(dto.getComment_uuid());

            confirmCommentAuth(account, comment);

            commentRepository.delete(comment);
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    private static void confirmCommentAuth(Account account, Comment comment) {
        if(!account.getUuid().equals(comment.getCommenter().getUuid()))
            throw new CustomException(BAD_REQUEST, "댓글에 대한 권한이 없습니다.");
    }

    //Adopted
    @Transactional
    public void adoptComment(AdoptCommentRequestDto dto) {
        try {
            Account account = validationAccount(dto.getUuid());
            Problem problem = validationProblem(dto.getProblem_uuid());
            Comment comment = validationComment(dto.getComment_uuid());

            confirmAdoptionAuth(account, problem, comment);

            if(problem.getIsSolved())
                account.subTokenValue(problem.getTokenForSolve());
            else
                account.addTokenValue(problem.getTokenForSolve());


            comment.updateIsAdopted();
            problem.changeIsSolved();
        } catch (Exception e) {
            throw new CustomException(BAD_REQUEST, e.getMessage());
        }
    }

    private static void confirmAdoptionAuth(Account account, Problem problem, Comment comment) {
        if(problem.getIsSolved())
            throw new CustomException(BAD_REQUEST, "이미 채택 처리된 불만입니다.");
        if(!problem.getAccount().equals(account))
            throw new CustomException(BAD_REQUEST, "댓글을 채택할 권한이 없습니다.");
        if(comment.getCommenter().equals(account))
            throw new CustomException(BAD_REQUEST, "작성자의 댓글을 채택할 수 없습니다.");
    }

    private Comment validationComment(String comment_uuid) {
        Optional<Comment> rawComment = commentRepository.findById(StringToUUID(comment_uuid));
        if(rawComment.isEmpty())
            throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 댓글입니다.");
        return rawComment.get();
    }

    private Problem validationProblem(String problem_uuid) {
        Optional<Problem> rawProblem = problemRepository.findById(StringToUUID(problem_uuid));
        if(rawProblem.isEmpty())
            throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 불만입니다.");
        return rawProblem.get();
    }

    private Account validationAccount(String uuid) {
        Optional<Account> rawAccount = accountRepository.findById(StringToUUID(uuid));
        if(rawAccount.isEmpty())
            throw new CustomException(BAD_REQUEST, "등록되어 있지 않은 사용자입니다.");
        return rawAccount.get();
    }
}
