package solution.demo.domain.problem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import solution.demo.domain.account.Account;
import solution.demo.global.util.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID comment_uuid;

    @Column()
    private String content;

    @ManyToOne
    @JoinColumn(name = "Problem")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "Account")
    private Account commenter;

    @Column
    private Boolean isWriter;

    // 작성자에게 채택되었는지
    @Column
    private Boolean isAdopted;


    @Builder
    public Comment(String content, Problem problem, Account commenter, Boolean isWriter) {
        this.content = content;
        this.problem = problem;
        this.commenter = commenter;
        this.isWriter = isWriter;
        this.isAdopted = Boolean.FALSE;
    }
}
