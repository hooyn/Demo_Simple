package solution.demo.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solution.demo.domain.problem.Comment;
import solution.demo.domain.problem.Problem;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findCommentByProblem(Problem problem);
}
