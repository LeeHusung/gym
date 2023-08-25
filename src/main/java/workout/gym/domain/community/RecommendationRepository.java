package workout.gym.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.CommunityAnswer;
import workout.gym.domain.entity.Recommendation;
import workout.gym.domain.entity.User;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Recommendation findByCommunityAndUser(Community community, User user);

    Recommendation findByCommunityAnswerAndUser(CommunityAnswer communityAnswer, User user);
}
