package solution.demo.domain.problem.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private String uuid;
    private String problem_uuid;
    private String content;
}
