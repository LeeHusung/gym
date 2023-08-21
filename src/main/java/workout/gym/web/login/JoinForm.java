package workout.gym.web.login;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import workout.gym.domain.entity.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class JoinForm {

    private UserRole userRole;

    @NotBlank
    private String username;

    @NotBlank
    private String password1;

    @NotBlank
    private String password2;

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
