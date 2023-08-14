package workout.gym.domain.order.mybag;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.gym.domain.entity.MyBag;

public interface MyBagRepository extends JpaRepository<MyBag, Long> {
}
