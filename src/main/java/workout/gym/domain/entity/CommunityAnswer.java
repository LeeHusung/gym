package workout.gym.domain.entity;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityAnswer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communityAnswer_id")
    private Long id;

    @Column(columnDefinition = "TEXT") //"내용"처럼 글자 수를 제한할 수 없는 경우에 사용
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(mappedBy = "communityAnswer", cascade = ALL)
    private Set<Recommendation> recommendations = new HashSet<>();

    @OneToMany(mappedBy = "communityAnswer", cascade = REMOVE)
    private List<Comment> comments = new ArrayList<>();

    //생성
    @Builder
    public CommunityAnswer(Community community, String content, LocalDateTime createdDate, String createdBy, User user) {
        this.community = community;
        this.content = content;
        this.setCreatedDate(createdDate);
        this.setCreatedBy(createdBy);
        this.user = user;
    }

    //수정
    public void updateCA(String content, LocalDateTime lastUpdateDate) {
        this.content = content;
        this.setLastModifiedDate(lastUpdateDate);
    }

}
