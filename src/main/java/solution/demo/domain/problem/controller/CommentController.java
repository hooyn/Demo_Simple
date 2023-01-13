package solution.demo.domain.problem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solution.demo.domain.problem.dto.*;
import solution.demo.domain.problem.service.CommentService;
import solution.demo.domain.problem.service.ProblemService;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final HttpServletRequest request;
    private final CommentService commentService;

    @ApiOperation("댓글 등록")
    @PostMapping("")
    public ResponseEntity<?> createProblem(@RequestBody CreateCommentRequestDto dto) {
        commentService.createComment(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "댓글이 등록되었습니다.)", List.of()), HttpStatus.OK);
    }

    @ApiOperation("댓글 조회")
    @GetMapping("")
    public ResponseEntity<?> readProblem(@RequestParam("problem_uuid") String problem_uuid) {
        List<ReadCommentResponseDto> comments = commentService.readComment(problem_uuid);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "댓글이 조회되었습니다.", comments), HttpStatus.OK);
    }
}
