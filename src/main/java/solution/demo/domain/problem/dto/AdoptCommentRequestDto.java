package solution.demo.domain.problem.dto;

import lombok.Getter;

@Getter
public class AdoptCommentRequestDto {

    private String uuid;
    private String problem_uuid;
    private String comment_uuid;
}
