package solution.demo.domain.account.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "필수 정보입니다.")
    private String userId;
    @NotBlank(message = "필수 정보입니다.")
    private String password;
}
