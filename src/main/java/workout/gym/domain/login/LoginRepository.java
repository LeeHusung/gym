package workout.gym.domain.login;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.gym.domain.entity.User;

public interface LoginRepository extends JpaRepository<User, Long> {
}
