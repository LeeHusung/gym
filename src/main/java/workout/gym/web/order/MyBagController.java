package workout.gym.web.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import workout.gym.domain.order.mybag.MyBagService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myBag")
public class MyBagController {

    private final MyBagService myBagService;

    @GetMapping
    public String viewMyBag(Model model) {
        model.addAttribute("myBagList", myBagService.findAll());
        return "myBag";
    }

    @PostMapping("/keepMyBack")
    public String keepMyBack(@RequestParam("itemId") Long itemId, @RequestParam("userId") Long userId, @RequestParam("count") int count) {
        myBagService.keepMyBag(userId, itemId, count);
        return "redirect:/myPage";
    }
}
