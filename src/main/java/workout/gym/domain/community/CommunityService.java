package workout.gym.domain.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    public Community save(Community community) {
        communityRepository.save(community);
        return community;
    }

    public Community findById(Long id) {
        return communityRepository.findById(id);
    }

    public List<Community> findAll() {
        return communityRepository.findAll();
    }
}
