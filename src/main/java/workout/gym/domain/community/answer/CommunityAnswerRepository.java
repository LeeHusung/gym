package workout.gym.domain.community.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.gym.domain.entity.CommunityAnswer;

public interface CommunityAnswerRepository extends JpaRepository<CommunityAnswer, Long> {
}
