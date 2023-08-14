package workout.gym.domain.entity;

import lombok.Getter;
import lombok.Setter;
import workout.gym.web.community.form.CommunityAddForm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "community")
    private List<UploadFile> communityImageFiles;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Community() {
    }
    //생성 메서드

    public static Community createCommunity(CommunityAddForm communityAddForm) {
        Community community = new Community();
        community.setCommunityTitle(communityAddForm.getCommunityTitle());
        community.setCommunityCategory(communityAddForm.getCommunityCategory());
        community.setCommunityContent(communityAddForm.getCommunityContent());
        community.setCreatedDate(LocalDateTime.now());
        return community;
    }
}
