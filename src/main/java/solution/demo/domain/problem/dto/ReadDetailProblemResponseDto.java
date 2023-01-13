package solution.demo.domain.problem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadDetailProblemResponseDto {

    private String problem_uuid;
    private String category;
    private String text;
    private long viewCount;
    private int tokenForSolve;
    private String createdDate;
    private AccountFormDto account;
    private boolean isSolved;

    @Builder
    public ReadDetailProblemResponseDto(String problem_uuid, String category, String text, long viewCount, int tokenForSolve, String createdDate, AccountFormDto account, boolean isSolved) {
        this.problem_uuid = problem_uuid;
        this.category = category;
        this.text = text;
        this.viewCount = viewCount;
        this.tokenForSolve = tokenForSolve;
        this.createdDate = createdDate;
        this.account = account;
        this.isSolved = isSolved;
    }
}
