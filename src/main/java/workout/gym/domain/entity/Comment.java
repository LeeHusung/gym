package workout.gym.domain.entity;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Comment(Community community, String content, User user, LocalDateTime createdAt) {
        this.community = community;
        this.content = content;
        this.user = user;
        this.setCreatedDate(createdAt);
    }

    public void updateComment(String content) {
        this.content = content;
        this.setLastModifiedDate(LocalDateTime.now());
    }

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
