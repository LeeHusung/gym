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
        CommunityAnswer communityAnswer = CommunityAnswer.builder()
                .community(community)
                .content(content)
                .createdBy(user.getNickname())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();

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
    public void editCA(Long id, String content) {
        CommunityAnswer findCA = findCA(id);
        findCA.updateCA(content, LocalDateTime.now());
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
        Recommendation recommendation = Recommendation.createRecommendationInCA(communityAnswer, user);
        recommendationRepository.save(recommendation);
    }
}
