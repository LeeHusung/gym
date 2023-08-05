package workout.gym.web.community;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.domain.community.Community;
import workout.gym.domain.community.CommunityFileService;
import workout.gym.domain.community.CommunityService;
import workout.gym.domain.file.UploadFile;
import workout.gym.domain.user.User;
import workout.gym.domain.user.UserService;
import workout.gym.web.community.form.CommunityAddForm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityFileService communityFileService;
    private final UserService userService;

    @GetMapping
    public String goCommunityMain() {
        return "community/communityMain";
    }

    @GetMapping("/new")
    public String addCommunity(@ModelAttribute CommunityAddForm communityAddForm) {
        return "community/addCommunity";
    }

    @PostMapping("/new")
    public String saveCommunity(@ModelAttribute CommunityAddForm communityAddForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        Community community = new Community(); //사용자의 입력값을 db에 넣기위해 Community로 변환하는 작업
        community.setCommunityTitle(communityAddForm.getCommunityTitle());
        community.setCommunityCategory(communityAddForm.getCommunityCategory());
        community.setCommunityContent(communityAddForm.getCommunityContent());

        User user = userService.findById(communityAddForm.getUserId()); //입력폼에서 userId를 입력받으면(여기서는 session에서 가져옴) 그걸로 user를 찾고
                                                                        // community에 set해줌으로써 연관관계 설정 완료.
        community.setUser(user);//community는 많고 user는 하나니까 community마다 일일히 넣어줘야해~

        communityService.save(community);

        communityFileService.saveFiles(communityAddForm.getCommunityImageFiles(), community); //저장된 community와 이미지파일들 저장.
//        community.setCommunityImageFiles(files); //연관관계 설정 //service에서 해도 됨. 결국 윗 라인 호출만 하면 됨.

        redirectAttributes.addAttribute("communityId", community.getId());
        return "redirect:/community/{communityId}";
    }

    @GetMapping("/{id}")
    public String community(@PathVariable Long id, Model model) {
        Community community = communityService.findById(id);
        model.addAttribute("community", community);
        return "community/viewCommunity";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + communityFileService.getFullPath(filename));
    }
}
