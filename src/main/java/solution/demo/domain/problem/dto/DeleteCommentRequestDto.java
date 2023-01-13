package solution.demo.domain.problem.dto;

import lombok.Getter;

@Getter
public class DeleteCommentRequestDto {

    private String uuid;
    private String comment_uuid;
}
