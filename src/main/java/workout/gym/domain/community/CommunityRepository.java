package workout.gym.domain.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import workout.gym.domain.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAll(Pageable pageable);
    Page<Community> findAll(Specification<Community> spec, Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from Community q "
            + "left outer join User u1 on q.user=u1 "
            + "left outer join CommunityAnswer a on a.community=q "
            + "left outer join User u2 on a.user=u2 "
            + "where "
            + "   q.communityTitle like %:kw% "
            + "   or q.communityContent like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Community> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

}