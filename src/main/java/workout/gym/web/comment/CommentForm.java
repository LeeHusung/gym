package workout.gym.web.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentForm {

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}
