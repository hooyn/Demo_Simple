package solution.demo.domain.problem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import solution.demo.domain.account.Account;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID problem_uuid;

    @Enumerated(EnumType.STRING)
    private ProblemCategory category;

    // 자신의 문제, 불만, 짜증을 이야기해주세요.
    @Column()
    private String text;

    // 사람들이 확인한 수
    @Column()
    private long viewCount;

    // 해결하는 사람에게 줄 토큰 (1~10)
    @Column() // 0$ ~ 10$
    private int tokenForSolve;

    // 문제를 제기한 사용자 정보
    @ManyToOne
    @JoinColumn(name = "Account")
    private Account account;

    @Builder
    public Problem(ProblemCategory category, String text, int tokenForSolve, Account account) {
        this.category = category;
        this.text = text;
        this.viewCount = 0;
        this.tokenForSolve = tokenForSolve;
        this.account = account;
    }
}
