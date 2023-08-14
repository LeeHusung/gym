package workout.gym.web.order.form;

import lombok.Data;
import workout.gym.domain.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderAddForm {

    private User user;

    private List<Item> items = new ArrayList<>();

    private Delivery delivery;

    private OrderStatus orderStatus;

    private LocalDateTime orderDate;
}
