package solution.demo.domain.problem.dto;

import lombok.Getter;
@Getter
public class UpdateProblemDto {

    private String problem_uuid;
    private String category;
    private String text;
    private int tokenForSolve = 0;
}
