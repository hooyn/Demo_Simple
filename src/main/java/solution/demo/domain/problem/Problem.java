package solution.demo.domain.problem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;
import solution.demo.domain.account.Account;
import solution.demo.domain.problem.dto.UpdateProblemDto;
import solution.demo.global.util.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem extends BaseEntity {

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
    private Long viewCount;

    // 해결하는 사람에게 줄 토큰 (1~10)
    @Column() // 0$ ~ 10$
    private Integer tokenForSolve;

    // 문제를 제기한 사용자 정보
    @ManyToOne
    @JoinColumn(name = "Account")
    private Account account;

    @Builder
    public Problem(ProblemCategory category, String text, Integer tokenForSolve, Account account) {
        this.category = category;
        this.text = text;
        this.viewCount = 0L;
        this.tokenForSolve = tokenForSolve;
        this.account = account;
    }

    public void plusViewCount() {
        this.viewCount++;
    }

    public void updateProblem(UpdateProblemDto dto) {
        if(dto.getCategory()!=null)
            this.category = ProblemCategory.valueOf(dto.getCategory());

        if(StringUtils.hasText(dto.getText()))
            this.text = dto.getText();

        if(dto.getTokenForSolve()!=0)
            this.tokenForSolve = dto.getTokenForSolve();
    }
}
