package workout.gym.web.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import workout.gym.domain.entity.User;
import workout.gym.domain.order.OrderService;
import workout.gym.web.argumentresolver.Login;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final OrderService orderService;

    @GetMapping("/myPage")
    public String myPage(Model model, @Login User loginUser) {
        model.addAttribute("user", loginUser);
        model.addAttribute("orderList", orderService.findAll());
        return "myPage";
    }

}
