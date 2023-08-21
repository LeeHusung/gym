package workout.gym.domain.community;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import workout.gym.common.exception.DataNotFoundException;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.User;
import workout.gym.domain.file.FileRepository;
import workout.gym.domain.user.UserRepository;
import workout.gym.web.community.form.CommunityAddForm;
import workout.gym.web.community.form.CommunityUpdateForm;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityFileService communityFileService;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Transactional
    public Community save(CommunityAddForm communityAddForm, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

        Community community = Community.createCommunity(communityAddForm);
//        User user = userRepository.findById(communityAddForm.getUserId());
//        community.setUser(user);
        User user = principalDetails.getUser();
        community.setUser(user);
        communityRepository.save(community);
        communityFileService.saveFiles(communityAddForm.getCommunityImageFiles(), community);
        return community;
    }

    @Transactional
    public Community UpdateCommunity(Long id, CommunityUpdateForm communityUpdateForm) throws IOException {
        Community findCommunity = communityRepository.findById(id);
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
        Community community = communityRepository.findById(id);
        if (community == null) {
            throw new DataNotFoundException("Community not found");
        }
        return community;
    }

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Community findCommunity = communityRepository.findById(id);
        if (findCommunity != null) {
            communityRepository.delete(findCommunity);
        }
    }
}
