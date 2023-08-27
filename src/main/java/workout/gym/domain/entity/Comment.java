package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Comment extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "communityAnswer_id")
    private CommunityAnswer communityAnswer;

    public Long getQuestionId() {
        Long result = null;
        if (this.community != null) {
            result = this.community.getId();
        } else if (this.communityAnswer != null) {
            result = this.communityAnswer.getId();
        }
        return result;
    }
}
