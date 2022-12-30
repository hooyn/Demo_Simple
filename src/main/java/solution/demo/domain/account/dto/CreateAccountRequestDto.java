package hooyn.base.domain.account.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class CreateAccountRequestDto {

    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "2 ~ 10자 한글, 영문만 사용 가능합니다.")
    @NotBlank(message = "필수 정보입니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{5,12}$", message = "5 ~ 12자 영문, 숫자만 사용 가능합니다.")
    @NotBlank(message = "필수 정보입니다.")
    private String userId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{10,16}$", message = "10 ~ 16자 영문, 숫자, 특수문자를 사용해야합니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식을 사용해야합니다.")
    @NotBlank(message = "필수 정보입니다.")
    private String email;
}
