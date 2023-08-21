package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter @Setter
@Entity
public class CommunityAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT") //"내용"처럼 글자 수를 제한할 수 없는 경우에 사용
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;
}
