package solution.demo.domain.problem.dto;

import lombok.Getter;
import solution.demo.domain.problem.ProblemCategory;

@Getter
public class CreateProblemRequestDto {

    private String uuid;
    private ProblemCategory category;
    private String text;
    private int tokenForSolve;
}
