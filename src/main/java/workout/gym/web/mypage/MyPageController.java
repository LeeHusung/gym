package workout.gym.web.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import workout.gym.domain.entity.Order;
import workout.gym.domain.entity.User;
import workout.gym.domain.order.OrderService;
import workout.gym.domain.user.UserService;
import workout.gym.web.argumentresolver.Login;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final OrderService orderService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myPage")
    public String myPage(Model model, Principal principal) {
        User user = userService.getUser(principal.getName());
        model.addAttribute("user", user);
        List<Order> orderList = orderService.findByUsername(user.getUsername());
        model.addAttribute("orderList", orderList);
        return "myPage";
    }

}
