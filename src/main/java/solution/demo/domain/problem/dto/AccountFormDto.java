package solution.demo.domain.problem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountFormDto {

    private String uuid;
    private String username;
    private String userId;
    private String email;
    private boolean emailCheck;
    private String role;

    @Builder
    public AccountFormDto(String uuid, String username, String userId, String email, boolean emailCheck, String role) {
        this.uuid = uuid;
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.emailCheck = emailCheck;
        this.role = role;
    }
}