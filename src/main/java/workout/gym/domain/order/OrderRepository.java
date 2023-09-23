package workout.gym.domain.order;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import workout.gym.domain.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager em;

    public Order save(Order order) {
        em.persist(order);
        return order;
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

    public List<Order> findByUsername(String username) {
        return em.createQuery("select o from Order o where o.user.username = :username", Order.class)
                .setParameter("username", username)
                .getResultList();
    }
}
