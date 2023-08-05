package workout.gym.domain.community;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workout.gym.domain.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Slf4j
public class CommunityRepository {

    @PersistenceContext
    private EntityManager em;

    public Community save(Community community) {
        em.persist(community);
        return community;
    }

    public Community findById(Long id) {
        Community community = em.find(Community.class, id);
        return community;
    }
}