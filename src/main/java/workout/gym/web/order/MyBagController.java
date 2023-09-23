package workout.gym.web.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import workout.gym.domain.entity.MyBag;
import workout.gym.domain.entity.User;
import workout.gym.domain.order.mybag.MyBagService;
import workout.gym.domain.user.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myBag")
public class MyBagController {

    private final MyBagService myBagService;
    private final UserService userService;

    @GetMapping
    public String viewMyBag(Model model, Principal principal) {
        User user = userService.getUser(principal.getName());
        MyBag myBag = myBagService.findById(user.getMyBag().getId());
        model.addAttribute("myBagList", myBag);
        return "myBag";
    }

    @PostMapping("/keepMyBack")
    public String keepMyBack(@RequestParam("itemId") Long itemId, @RequestParam("userId") Long userId, @RequestParam("count") int count) {
        myBagService.addMyBag(userId, itemId, count);
        return "redirect:/myPage";
    }
}
