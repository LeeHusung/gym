package workout.gym.domain.community;

import lombok.Getter;
import lombok.Setter;
import workout.gym.domain.file.UploadFile;
import workout.gym.domain.user.User;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Community {

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
}
