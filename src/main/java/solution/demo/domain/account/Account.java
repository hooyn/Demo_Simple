package solution.demo.domain.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import solution.demo.global.exception.CustomException;
import solution.demo.global.exception.ErrorCode;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean emailCheck;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column()
    private Integer tokenValue;

    @Builder
    public Account(String username, String userId, String password, String email, Boolean emailCheck, Role role) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.emailCheck = emailCheck;
        this.role = role;
        this.tokenValue = 0;
    }

    public void addTokenValue(int value) {
        this.tokenValue += value;
    }

    public void subTokenValue(int value) {
        if((this.tokenValue - value) < 0)
            throw new CustomException(ErrorCode.BAD_REQUEST, "tokenValue는 음수가 될 수 없습니다.");
        this.tokenValue -= value;
    }
}
