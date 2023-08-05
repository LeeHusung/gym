package workout.gym.web.community.form;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CommunityUpdateForm {

    private String communityCategory;
    private String communityComment;

    private List<MultipartFile> communityImageFiles;
}
