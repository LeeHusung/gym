package workout.gym.web.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.entity.Community;
import workout.gym.domain.community.CommunityFileService;
import workout.gym.domain.community.CommunityService;
import workout.gym.domain.entity.User;
import workout.gym.domain.user.UserRepository;
import workout.gym.domain.user.UserService;
import workout.gym.web.community.form.CommunityAddForm;
import workout.gym.web.community.form.CommunityAnswerAddForm;
import workout.gym.web.community.form.CommunityUpdateForm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
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
    public String goCommunityMain(Model model,
                                  @RequestParam(value = "start", defaultValue = "0") int start,
                                  @RequestParam(value = "end", defaultValue = "10") int end) {
        Page<Community> communityList = communityService.getList(start, end);
        model.addAttribute("communityList", communityList);
        return "community/communityMain";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/new")
    public String addCommunity(@ModelAttribute CommunityAddForm communityAddForm, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("user", principalDetails.getUser());
        return "community/addCommunity";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public String saveCommunity(@Validated @ModelAttribute("communityAddForm") CommunityAddForm communityAddForm,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/community/addCommunity";
        }
        Community community = communityService.save(communityAddForm, principal);
        redirectAttributes.addAttribute("communityId", community.getId());
        return "redirect:/community/{communityId}";
    }

    @GetMapping("/{id}")
    public String community(@PathVariable Long id, Model model, @ModelAttribute("CommunityAnswerAddForm") CommunityAnswerAddForm communityAnswerAddForm) {
        Community community = communityService.findById(id);
        model.addAttribute("community", community);
        return "community/viewCommunity";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/update")
    public String editCommunity(@PathVariable Long id, @ModelAttribute CommunityUpdateForm communityUpdateForm, Principal principal) {
        Community findCommunity = communityService.findById(id);
        if (!findCommunity.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        communityUpdateForm.setCommunityCategory(findCommunity.getCommunityCategory());
        communityUpdateForm.setCommunityTitle(findCommunity.getCommunityTitle());
        communityUpdateForm.setCommunityContent(findCommunity.getCommunityContent());
        communityUpdateForm.setCommunityCurrentImageFiles(findCommunity.getCommunityImageFiles());
        return "/community/updateCommunity";
    }

    @PostMapping("/{id}/update")
    public String UpdateCommunity(@PathVariable Long id, @Validated @ModelAttribute("communityUpdateForm") CommunityUpdateForm communityUpdateForm,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            return "/community/updateCommunity";
        }
        Community findCommunity = communityService.findById(id);
        if (!findCommunity.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        Community updateCommunity = communityService.UpdateCommunity(id, communityUpdateForm);
        redirectAttributes.addAttribute("communityId", updateCommunity.getId());
        return "redirect:/community/{communityId}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        Community findCommunity = communityService.findById(id);
        if (!findCommunity.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        communityService.delete(id);
        return "redirect:/community";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{communityId}/recommend")
    public String recommend(Principal principal, @PathVariable("communityId") Long communityId) {
        Community community = communityService.findById(communityId);
        User user = userService.getUser(principal.getName());
        communityService.recommend(community.getId(), user.getId());
        return "redirect:/community/{communityId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + communityFileService.getFullPath(filename));
    }
}
