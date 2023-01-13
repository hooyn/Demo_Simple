package solution.demo.domain.problem.dto;

import lombok.Builder;
import lombok.Getter;
import solution.demo.domain.account.Account;
import solution.demo.global.util.UUIDUtil;

@Getter
public class ReadCommentResponseDto {

    private String comment_uuid;
    private AccountData commenter;
    private String content;
    private boolean isWriter;
    private boolean isAdopted;
    private String createdDate;

    @Getter
    @Builder
    static class AccountData {
        private String uuid;
        private String username;
        private String userId;
    }

    @Builder
    public ReadCommentResponseDto(String comment_uuid, Account commenter, String content, boolean isWriter, boolean isAdopted, String createdDate) {
        this.comment_uuid = comment_uuid;
        this.commenter = AccountData.builder()
                .uuid(UUIDUtil.UUIDToString(commenter.getUuid()))
                .userId(commenter.getUserId())
                .username(commenter.getUsername())
                .build();
        this.content = content;
        this.isWriter = isWriter;
        this.isAdopted = isAdopted;
        this.createdDate = createdDate;
    }
}
