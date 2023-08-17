package workout.gym.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import workout.gym.domain.entity.Address;
import workout.gym.domain.user.UserRepository;
import workout.gym.domain.user.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("joinForm") JoinForm joinForm) {
        return "join";
    }

    @PostMapping("/join")
    public String join(@Validated JoinForm joinForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "join";
        }
        if (!joinForm.getPassword1().equals(joinForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "join";
        }
        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());

        try {
            userService.save(joinForm.getUserRole(), address, joinForm.getUsername(), joinForm.getPassword1(), joinForm.getEmail(), joinForm.getRealname(), joinForm.getNickname(), joinForm.getPhone());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "join";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "join";
        }
        return "redirect:/";
    }
}