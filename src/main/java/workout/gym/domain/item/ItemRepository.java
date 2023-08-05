package workout.gym.domain.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Slf4j
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;

    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    public Item findById(Long id) {
        Item item = em.find(Item.class, id);
        return item;
    }
}
