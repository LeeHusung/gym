package workout.gym.domain.community;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.entity.Community;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<Community> findAll() {
        String jpql = "select c from Community c";

        TypedQuery<Community> query = em.createQuery(jpql, Community.class);
        return query.getResultList();
    }

    public void delete(Community community) {
        em.createQuery("delete from UploadFile uf where uf.community = :community")
                .setParameter("community", community)
                .executeUpdate();

        em.remove(community); //여기서 community가 삭제되는거야? ㅇㅇ 원리는?
    }

}