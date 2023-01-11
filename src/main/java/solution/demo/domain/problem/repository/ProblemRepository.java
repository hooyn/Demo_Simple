package solution.demo.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solution.demo.domain.problem.Problem;

import java.util.UUID;

public interface ProblemRepository extends JpaRepository<Problem, UUID> {
}
