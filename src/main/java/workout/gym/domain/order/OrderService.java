package workout.gym.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.entity.*;
import workout.gym.domain.item.ItemRepository;
import workout.gym.domain.user.UserRepository;
import workout.gym.web.order.form.OrderAddForm;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Order createOrder(Long userId, Long itemId, int count) {
        User user = userRepository.findById(userId);
        Item item = itemRepository.findById(itemId);
        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getItemPrice(), count);

        Order order = Order.createOrder(user, delivery, orderItem);

        return orderRepository.save(order);
    }


    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void keepMyBack() {
    }
}
