package solution.demo.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solution.demo.domain.problem.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
