package workout.gym.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import workout.gym.domain.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User findById(Long id) {
        User user = em.find(User.class, id);
        return user;
    }

    public Optional<User> findByUsername(String username) {
//        TypedQuery<User> query = em.createQuery(
//                "SELECT u FROM User u WHERE u.username = :username", User.class
//        );
//        query.setParameter("username", username);
//        return query.getSingleResult();
        return findAll().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByRealName(String realname) {
        return em.createQuery("select u from User u where u.realName = :realname", User.class)
                .setParameter("realname", realname).getResultList();
    }
}
