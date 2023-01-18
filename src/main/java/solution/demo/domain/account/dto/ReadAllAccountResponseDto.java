package solution.demo.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAllAccountResponseDto {

    private String uuid;
    private String username;
    private String userId;
    private String email;
    private boolean emailCheck;
    private String role;
    private int tokenValue;
}
