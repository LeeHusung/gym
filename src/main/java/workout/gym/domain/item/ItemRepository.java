package workout.gym.domain.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import workout.gym.domain.entity.Item;
import workout.gym.domain.entity.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

//    public List<OrderItem> findAllByItemId(Long itemId) {
//        return em.createQuery("select o from OrderItem o where o.id = :itemId", OrderItem.class).getResultList();
//    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    public void delete(Item item) {
        em.createQuery("delete UploadFile uf where uf.item = :item")
                .setParameter("item", item)
                .executeUpdate();
        em.remove(item);
    }
}
