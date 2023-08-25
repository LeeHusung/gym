package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

@Getter @Setter
@Entity
public class CommunityAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public CommunityAnswer() {
    }

}
