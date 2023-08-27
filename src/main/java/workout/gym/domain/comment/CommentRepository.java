package workout.gym.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.gym.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
