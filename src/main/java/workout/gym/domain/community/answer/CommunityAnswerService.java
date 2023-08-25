package workout.gym.domain.community.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.common.exception.DataNotFoundException;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.community.CommunityRepository;
import workout.gym.domain.community.RecommendationRepository;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.CommunityAnswer;
import workout.gym.domain.entity.Recommendation;
import workout.gym.domain.entity.User;
import workout.gym.domain.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityAnswerService {

    private final CommunityAnswerRepository communityAnswerRepository;
    private final RecommendationRepository recommendationRepository;
    private final UserService userService;

    @Transactional
    public void create(Community community, String content, User user) {
        CommunityAnswer communityAnswer = new CommunityAnswer();
        communityAnswer.setCommunity(community);
        communityAnswer.setContent(content);
        communityAnswer.setCreatedDate(LocalDateTime.now());
        communityAnswer.setCreatedBy(user.getNickname());
        communityAnswer.setUser(user);
        communityAnswerRepository.save(communityAnswer);
    }

    public CommunityAnswer findCA(Long id) {
        Optional<CommunityAnswer> findCA = communityAnswerRepository.findById(id);
        if (findCA.isPresent()) {
            return findCA.get();
        } else {
            throw new DataNotFoundException("CA NOT FOUND");
        }
    }

    @Transactional
    public void editCA(CommunityAnswer communityAnswer, String content) {
        communityAnswer.setContent(content);
        communityAnswer.setLastModifiedDate(LocalDateTime.now());
    }

    @Transactional
    public void delete(CommunityAnswer communityAnswer) {
        communityAnswerRepository.delete(communityAnswer);
    }

    @Transactional
    public void recommend(Long communityAnswerId, Long userId) {
        CommunityAnswer communityAnswer = communityAnswerRepository.findById(communityAnswerId).orElseThrow(() -> new EntityNotFoundException("CommunityAnswer Not Found"));
        User user = userService.findById(userId);

        Recommendation findRecommendation = recommendationRepository.findByCommunityAnswerAndUser(communityAnswer, user);
        if (findRecommendation != null) {
            return;
        }
        Recommendation recommendation = new Recommendation();
        recommendation.setCommunityAnswer(communityAnswer);
        recommendation.setUser(user);
        recommendationRepository.save(recommendation);
    }
}
