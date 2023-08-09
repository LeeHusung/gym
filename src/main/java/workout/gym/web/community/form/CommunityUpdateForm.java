package workout.gym.web.community.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.domain.file.UploadFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CommunityUpdateForm {

    @NotBlank
    @Size(min = 2, message = "두 글자 이상 적어주세요.")
    private String communityTitle;

    @NotBlank
    private String communityCategory;

    @NotBlank
    @Size(min = 5, message = "다섯 글자 이상 적어주세요.")
    private String communityContent;

    private List<UploadFile> communityCurrentImageFiles;
    private List<MultipartFile> communityImageFiles;
}
