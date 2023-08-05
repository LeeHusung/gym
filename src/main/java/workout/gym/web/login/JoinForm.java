package workout.gym.web.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class JoinForm {

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String realname;

    @NotBlank
    private String nickname;

    @Email @NotBlank
    private String email;

    private String city;
    private String street;
    private String zipcode;

    @NotBlank
    private String phone;
}
