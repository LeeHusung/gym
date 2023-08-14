package workout.gym.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import workout.gym.domain.entity.User;
import workout.gym.web.argumentresolver.Login;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index(@Login User loginUser, Model model) {

        if (loginUser == null) {
            return "noLoginMain";
        }

        model.addAttribute("user", loginUser);
        return "main";
    }

    @GetMapping("/main")
    public String home() {

//        if (loginUser == null) {
//            return "login";
//        }
//
//        model.addAttribute("user", loginUser);
        return "main";
    }



}
