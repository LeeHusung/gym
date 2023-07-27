package workout.gym.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class TestController {

    @GetMapping("/workout/info")
    public String info() {
        return "allWorkoutInfo";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/join")
    public String join() {
        return "join";
    }
}
