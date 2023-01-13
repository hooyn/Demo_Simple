package solution.demo.domain.problem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadProblemResponseDto {

    private String problem_uuid;
    private String category;
    private long viewCount;
    private String createdDate;
    private AccountFormDto account;
    private boolean isSolved;

    @Builder
    public ReadProblemResponseDto(String problem_uuid, String category, long viewCount, String createdDate, AccountFormDto account, boolean isSolved) {
        this.problem_uuid = problem_uuid;
        this.category = category;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.account = account;
        this.isSolved = isSolved;
    }
}