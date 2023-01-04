package solution.demo.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String uuid;
    private String userId;
    private String username;
    private String email;
    private String role;
}
