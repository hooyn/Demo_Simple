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

    //C
    @ApiOperation("댓글 등록")
    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequestDto dto) {
        commentService.createComment(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "댓글이 등록되었습니다.)", List.of()), HttpStatus.OK);
    }

    //R
    @ApiOperation("댓글 조회")
    @GetMapping("")
    public ResponseEntity<?> readComment(@RequestParam("problem_uuid") String problem_uuid) {
        List<ReadCommentResponseDto> comments = commentService.readComment(problem_uuid);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "댓글이 조회되었습니다.", comments), HttpStatus.OK);
    }

    //U
    @ApiOperation("댓글 업데이트")
    @PutMapping("/")
    public ResponseEntity<?> updateComment(@RequestBody UpdateCommentRequestDto dto) {
        commentService.updateComment(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "댓글이 업데이트 되었습니다.", List.of()), HttpStatus.OK);
    }

    //D
    @ApiOperation("댓글 삭제")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteComment(@RequestBody DeleteCommentRequestDto dto) {
        commentService.deleteComment(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "댓글이 삭제되었습니다.", List.of()), HttpStatus.OK);
    }

    //Adopted

}
