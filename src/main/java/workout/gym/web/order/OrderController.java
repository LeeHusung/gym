package workout.gym.web.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import workout.gym.domain.entity.Item;
import workout.gym.domain.entity.User;
import workout.gym.domain.item.ItemService;
import workout.gym.domain.order.OrderService;
import workout.gym.domain.user.UserService;
import workout.gym.web.order.form.OrderAddForm;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/new")
    public String viewOrder(@ModelAttribute OrderAddForm orderAddForm) {
        return "order/createOrder";
    }

    @PostMapping("/new")
    public String createOrder(@RequestParam("itemId") Long itemId, @RequestParam("userId") Long userId, @RequestParam("count") int count) {
        orderService.createOrder(userId, itemId, count);
        return "redirect:/myPage";
    }

}
