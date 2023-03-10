package solution.demo.domain.problem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solution.demo.domain.problem.dto.CreateProblemRequestDto;
import solution.demo.domain.problem.dto.ReadDetailProblemResponseDto;
import solution.demo.domain.problem.dto.ReadProblemResponseDto;
import solution.demo.domain.problem.dto.UpdateProblemDto;
import solution.demo.domain.problem.service.ProblemService;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem")
public class ProblemController {

    private final HttpServletRequest request;
    private final ProblemService problemService;


    @ApiOperation("불만 등록")
    @PostMapping("")
    public ResponseEntity<?> createProblem(@RequestBody CreateProblemRequestDto dto) {
        problemService.createProblem(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "불만이 등록되었습니다. 감사합니다:)", List.of()), HttpStatus.OK);
    }

    @ApiOperation("불만 조회")
    @GetMapping("")
    public ResponseEntity<?> readProblem() {
        List<ReadProblemResponseDto> problems = problemService.readProblems();
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "불만이 조회되었습니다.", problems), HttpStatus.OK);
    }

    @ApiOperation("불만 상세조회")
    @GetMapping("/detail")
    public ResponseEntity<?> readProblem(@RequestParam("problem_uuid") String problem_uuid) {
        ReadDetailProblemResponseDto detailProblem = problemService.readDetailProblem(problem_uuid);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "불만이 상세 조회되었습니다.", detailProblem), HttpStatus.OK);
    }

    @ApiOperation("불만 업데이트")
    @PutMapping("/")
    public ResponseEntity<?> updateProblem(@RequestBody UpdateProblemDto dto) {
        problemService.updateProblem(dto);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "불만이 업데이트 되었습니다.", List.of()), HttpStatus.OK);
    }

    @ApiOperation("불만 삭제")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteProblem(@RequestParam("problem_uuid") String problem_uuid) {
        problemService.deleteProblem(problem_uuid);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "불만이 삭제되었습니다.", List.of()), HttpStatus.OK);
    }
}
