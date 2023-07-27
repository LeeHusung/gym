package workout.gym.repository;

import org.springframework.stereotype.Repository;
import workout.gym.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }
}
