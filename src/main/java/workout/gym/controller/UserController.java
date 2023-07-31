package workout.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import workout.gym.domain.Address;
import workout.gym.domain.User;
import workout.gym.form.JoinForm;
import workout.gym.form.LoginForm;
import workout.gym.service.UserService;
import workout.gym.web.argumentresolver.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static workout.gym.SessionConst.LOGIN_USER;

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
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        User loginUser = userService.login(form.getUsername(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디와 비밀번호를 다시 확인해주세요.");
            return "login";
        }

        HttpSession session = request.getSession(); //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(LOGIN_USER, loginUser);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
//            log.info("세션 삭제 성공 = {}", session.get);
        }
        return "redirect:/";
    }

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
        userService.join(user);
        return "redirect:/main";
    }

    @GetMapping("/myPage")
    public String myPage(Model model, @Login User loginMember) {
        model.addAttribute("member", loginMember);
        return "myPage";
    }





    @ResponseBody
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @ResponseBody
    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/joinProc")
    @ResponseBody
    public String joinProc() {
        return "회원가입 완료";
    }
}
