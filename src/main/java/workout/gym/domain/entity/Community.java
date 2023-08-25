package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;
import workout.gym.web.community.form.CommunityAddForm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    private String communityTitle;
    private String communityCategory;
    private String communityContent;

    @OneToMany(mappedBy = "community", cascade = ALL) //cascade ALL을 하고 안하고 차이가 뭐임? uploadFile 먼저 delete하고 community delete 사이에 select있던데. 일단 캡쳐따놓음.
    private List<UploadFile> communityImageFiles;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "community", cascade = REMOVE) //질문 삭제하면 그에 달린 답변들도 삭제
    private List<CommunityAnswer> communityAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = ALL)
    private Set<Recommendation> recommendations = new HashSet<>();

    public Community() {
    }

    //생성 메서드
    public static Community createCommunity(CommunityAddForm communityAddForm, User user) {
        Community community = new Community();
        community.setCommunityTitle(communityAddForm.getCommunityTitle());
        community.setCommunityCategory(communityAddForm.getCommunityCategory());
        community.setCommunityContent(communityAddForm.getCommunityContent());
        community.setCreatedDate(LocalDateTime.now());
        community.setUser(user);
        return community;
    }
}
