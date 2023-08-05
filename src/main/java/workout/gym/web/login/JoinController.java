package workout.gym.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import workout.gym.domain.entity.Address;
import workout.gym.domain.user.User;
import workout.gym.domain.user.UserRepository;
import workout.gym.domain.user.UserService;

import javax.validation.Valid;

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
    public String join(@Valid JoinForm joinForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "join";
        }

        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());

        User user = new User();
        user.setUsername(joinForm.getUsername());
        user.setPassword(joinForm.getPassword());
        user.setRealname(joinForm.getRealname());
        user.setEmail(joinForm.getEmail());
        user.setNickname(joinForm.getNickname());
        user.setPhone(joinForm.getPhone());
        user.setAddress(address);
        userService.save(user);
        return "redirect:/main";
    }
}