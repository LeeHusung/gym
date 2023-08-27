package workout.gym.domain.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.common.exception.DataNotFoundException;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.CommunityAnswer;
import workout.gym.domain.entity.Recommendation;
import workout.gym.domain.entity.User;
import workout.gym.domain.file.FileRepository;
import workout.gym.domain.user.UserRepository;
import workout.gym.domain.user.UserService;
import workout.gym.web.community.form.CommunityAddForm;
import workout.gym.web.community.form.CommunityUpdateForm;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityFileService communityFileService;
    private final RecommendationRepository recommendationRepository;
    private final UserService userService;

    @Transactional
    public Community save(CommunityAddForm communityAddForm, Principal principal) throws IOException {
        User user = userService.getUser(principal.getName());
        Community community = Community.createCommunity(communityAddForm , user);
        communityRepository.save(community);
        if (communityAddForm.getCommunityImageFiles() != null) {
            communityFileService.saveFiles(communityAddForm.getCommunityImageFiles(), community);
        }
        return community;
    }

    @Transactional
    public Community UpdateCommunity(Long id, CommunityUpdateForm communityUpdateForm) throws IOException {
        log.info("커뮤니티 업데이트 시작");
        Community findCommunity = communityRepository.findById(id).get();
        findCommunity.setCommunityTitle(communityUpdateForm.getCommunityTitle());
        findCommunity.setCommunityCategory(communityUpdateForm.getCommunityCategory());
        findCommunity.setCommunityContent(communityUpdateForm.getCommunityContent());
        findCommunity.setLastModifiedDate(LocalDateTime.now());

        List<MultipartFile> newImageFiles = communityUpdateForm.getCommunityImageFiles();
        if (newImageFiles != null && !newImageFiles.isEmpty()) {
            communityFileService.saveFiles(newImageFiles, findCommunity);
        }
        return findCommunity;
    }

    public Community findById(Long id) {
        Community community = communityRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Community Not Found"));
        return community;
    }

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Community findCommunity = communityRepository.findById(id).get();
        if (findCommunity != null) {
            communityRepository.delete(findCommunity);
        }
    }

    public Page<Community> getList(int start, int end, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        if (start < 0) {
            start = 0;
        }
        PageRequest pageable = PageRequest.of(start, end, Sort.by(sorts));
//        PageRequest pageable = PageRequest.of(start, end, Sort.by(Sort.Direction.DESC, "username"));
        Specification<Community> spec = search(kw);
        return communityRepository.findAllByKeyword(kw, pageable);
    }

    private Specification<Community> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Community> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Community, User> u1 = q.join("user", JoinType.LEFT);
                Join<Community, CommunityAnswer> a = q.join("communityAnswers", JoinType.LEFT);
                Join<CommunityAnswer, User> u2 = a.join("user", JoinType.LEFT);
                return cb.or(cb.like(q.get("communityTitle"), "%" + kw + "%"), // 제목
                        cb.like(q.get("communityContent"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 커뮤니티 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    @Transactional
    public void recommend(Long communityId, Long userId) {
        Community community = communityRepository.findById(communityId).get();
        User user = userService.findById(userId);

        Recommendation findRecommendation = recommendationRepository.findByCommunityAndUser(community, user);
        if (findRecommendation != null) {
            // User has already recommended this community
            return;
        }
        Recommendation recommendation = new Recommendation();
        recommendation.setCommunity(community);
        recommendation.setUser(user);
        recommendationRepository.save(recommendation);
    }
}
