package workout.gym.web.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.domain.community.Community;
import workout.gym.domain.community.CommunityFileService;
import workout.gym.domain.community.CommunityService;
import workout.gym.domain.user.User;
import workout.gym.domain.user.UserService;
import workout.gym.web.community.form.CommunityAddForm;
import workout.gym.web.community.form.CommunityUpdateForm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityFileService communityFileService;
    private final UserService userService;

    @GetMapping
    public String goCommunityMain(Model model) {
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList", communityList);
        return "community/communityMain";
    }

    @GetMapping("/new")
    public String addCommunity(@ModelAttribute CommunityAddForm communityAddForm) {
        return "community/addCommunity";
    }

    @PostMapping("/new")
//    @ExceptionHandler
    public String saveCommunity(@Validated @ModelAttribute("communityAddForm") CommunityAddForm communityAddForm,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/community/addCommunity";
        }

        Community community = new Community(); //사용자의 입력값을 db에 넣기위해 Community로 변환하는 작업
        community.setCommunityTitle(communityAddForm.getCommunityTitle());
        community.setCommunityCategory(communityAddForm.getCommunityCategory());
        community.setCommunityContent(communityAddForm.getCommunityContent());
        community.setCreatedDate(LocalDateTime.now()); //현재시간설정
        User user = userService.findById(communityAddForm.getUserId()); //입력폼에서 userId를 입력받으면(여기서는 session에서 가져옴) 그걸로 user를 찾고
                                                                        // community에 set해줌으로써 연관관계 설정 완료.
        community.setUser(user);//community는 많고 user는 하나니까 community마다 일일히 넣어줘야, 연관관계 주인에서만 값 설정 가능

        communityService.save(community);

        communityFileService.saveFiles(communityAddForm.getCommunityImageFiles(), community); //저장된 community와 이미지파일들 저장.

        redirectAttributes.addAttribute("communityId", community.getId());
        return "redirect:/community/{communityId}";
    }

    @GetMapping("/{id}")
    public String community(@PathVariable Long id, Model model) {
        Community community = communityService.findById(id);
        model.addAttribute("community", community);
        return "community/viewCommunity";
    }

    @GetMapping("/{id}/update")
    public String editCommunity(@PathVariable Long id, @ModelAttribute CommunityUpdateForm communityUpdateForm) {
        Community findCommunity = communityService.findById(id);
        communityUpdateForm.setCommunityCategory(findCommunity.getCommunityCategory());
        communityUpdateForm.setCommunityTitle(findCommunity.getCommunityTitle());
        communityUpdateForm.setCommunityContent(findCommunity.getCommunityContent());
        communityUpdateForm.setCommunityCurrentImageFiles(findCommunity.getCommunityImageFiles());
        return "/community/updateCommunity";
    }

    @PostMapping("/{id}/update")
    public String UpdateCommunity(@PathVariable Long id, @Validated @ModelAttribute("communityUpdateForm") CommunityUpdateForm communityUpdateForm,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/community/updateCommunity";
        }

        Community updateCommunity = communityService.findById(id);
        updateCommunity.setLastModifiedDate(LocalDateTime.now());
        updateCommunity.setCommunityTitle(communityUpdateForm.getCommunityTitle());
        updateCommunity.setCommunityCategory(communityUpdateForm.getCommunityCategory());
        updateCommunity.setCommunityContent(communityUpdateForm.getCommunityContent());

        // 새로 업로드된 이미지 파일이 있다면 추가
        List<MultipartFile> newImageFiles = communityUpdateForm.getCommunityImageFiles();
        if (newImageFiles != null && !newImageFiles.isEmpty()) {
            communityFileService.saveFiles(newImageFiles, updateCommunity);
        }

        redirectAttributes.addAttribute("communityId", updateCommunity.getId());
        return "redirect:/community/{communityId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + communityFileService.getFullPath(filename));
    }
}
