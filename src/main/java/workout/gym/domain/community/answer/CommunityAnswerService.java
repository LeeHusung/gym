package workout.gym.domain.community.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.CommunityAnswer;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommunityAnswerService {

    private final CommunityAnswerRepository communityAnswerRepository;

    public void create(Community community, String content, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        CommunityAnswer communityAnswer = new CommunityAnswer();
        communityAnswer.setCommunity(community);
        communityAnswer.setContent(content);
        communityAnswer.setCreatedDate(LocalDateTime.now());
        communityAnswer.setCreatedBy(principalDetails.getUser().getNickname());
        communityAnswerRepository.save(communityAnswer);
    }
}
