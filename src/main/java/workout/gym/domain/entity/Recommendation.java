package workout.gym.domain.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "communityAnswer_id")
    private CommunityAnswer communityAnswer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //생성
    public static Recommendation createRecommendationInCA(CommunityAnswer communityAnswer, User user) {
        Recommendation recommendation = new Recommendation();
        recommendation.communityAnswer = communityAnswer;
        recommendation.user = user;
        return recommendation;
    }

    public static Recommendation createRecommendationInCommunity(Community community, User user) {
        Recommendation recommendation = new Recommendation();
        recommendation.community = community;
        recommendation.user = user;
        return recommendation;
    }
}
