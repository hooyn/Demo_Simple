package solution.demo.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String uuid;
    private String userId;
    private String username;
    private String email;
    private String role;

    @Builder
    public LoginResponseDto(String uuid, String userId, String username, String email, String role) {
        this.uuid = uuid;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
