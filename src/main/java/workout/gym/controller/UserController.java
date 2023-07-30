package workout.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import workout.gym.domain.User;
import workout.gym.form.JoinForm;
import workout.gym.form.LoginForm;
import workout.gym.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        User loginUser = userService.login(form.getUsername(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디와 비밀번호를 다시 확인해주세요.");
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/users/new")
    public String joinForm(Model model) {
        model.addAttribute("userForm", new JoinForm());
        return "";
    }

    @PostMapping("/users/new")
    public String join(@Validated JoinForm joinForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "";
        }

        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        userService.join(user);
        return "";
    }
}
