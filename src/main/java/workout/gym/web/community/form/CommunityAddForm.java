package workout.gym.web.community.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.user.User;

import java.util.List;

@Data
public class CommunityAddForm {

    private Long userId; // user와 community 연관관계 설정용

    private String communityTitle;
    private String communityCategory;
    private String communityContent;

    private List<MultipartFile> communityImageFiles;
}
