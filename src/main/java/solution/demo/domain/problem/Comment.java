package solution.demo.domain.problem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import solution.demo.domain.account.Account;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

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
    private boolean isWriter;
}
