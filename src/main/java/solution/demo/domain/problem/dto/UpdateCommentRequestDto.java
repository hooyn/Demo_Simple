package solution.demo.domain.problem.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {

    private String uuid;
    private String comment_uuid;
    private String comment;
}
