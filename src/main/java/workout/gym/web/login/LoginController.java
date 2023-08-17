package workout.gym.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import workout.gym.domain.login.LoginService;
import workout.gym.domain.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import static workout.gym.common.SessionConst.LOGIN_USER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
//                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
//
//        if (bindingResult.hasErrors()) {
//            return "login";
//        }
//
//        User loginUser = loginService.login(form.getUsername(), form.getPassword());
//
//        if (loginUser == null) {
//            bindingResult.reject("loginFail", "아이디와 비밀번호를 다시 확인해주세요.");
//            return "login";
//        }
//
//        HttpSession session = request.getSession(); //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
//        session.setAttribute(LOGIN_USER, loginUser);
//
//        return "redirect:" + redirectURL;
//    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
////            log.info("세션 삭제 성공 = {}", session.get);
//        }
//        return "redirect:/";
//    }
}
