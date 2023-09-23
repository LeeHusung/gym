package workout.gym.domain.order.mybag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.entity.Item;
import workout.gym.domain.entity.MyBag;
import workout.gym.domain.entity.OrderItem;
import workout.gym.domain.entity.User;
import workout.gym.domain.item.ItemRepository;
import workout.gym.domain.user.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyBagService {

    private final MyBagRepository myBagRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void addMyBag(Long userId, Long itemId, int count) {
        User findUser = userRepository.findById(userId);
        Item item = itemRepository.findById(itemId);
        OrderItem orderItem = OrderItem.keepMyBag(item, item.getItemPrice(), count);

        findUser.getMyBag().addOrderItem(orderItem);
        userRepository.save(findUser);
    }

    //장바구니에 추가
    @Transactional
    public void addIntoMyBag(MyBag myBag, OrderItem... orderItems) {
        for (OrderItem orderItem : orderItems) {
            myBag.addOrderItem(orderItem);
        }
    }

    public List<MyBag> findAll() {
        return myBagRepository.findAll();
    }

    public MyBag findById(Long id) {
        return myBagRepository.findById(id).get();
    }
}
