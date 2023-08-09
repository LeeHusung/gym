package workout.gym.web.community.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommunityAddForm {

    private Long userId; // user와 community 연관관계 설정용

    @NotBlank
    @Size(min = 2, message = "두 글자 이상 적어주세요.")
    private String communityTitle;

    @NotBlank
    private String communityCategory;

    @NotBlank
    @Size(min = 5, message = "다섯 글자 이상 적어주세요.")
    private String communityContent;

    private List<MultipartFile> communityImageFiles;
}
