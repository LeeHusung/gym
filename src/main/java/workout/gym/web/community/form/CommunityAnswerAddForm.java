package workout.gym.web.community.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommunityAnswerAddForm {

    @NotBlank(message = "내용은 필수항목 입니다.")
    @Size(min = 5, message = "5글자 이상 입력해주세요.")
    private String content;
}
