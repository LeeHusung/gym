package workout.gym.web.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.community.CommunityService;
import workout.gym.domain.community.answer.CommunityAnswerService;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.CommunityAnswer;
import workout.gym.domain.entity.User;
import workout.gym.domain.user.UserService;
import workout.gym.web.community.form.CommunityAnswerAddForm;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/communityAnswer")
public class CommunityAnswerController {

    private final CommunityService communityService;
    private final CommunityAnswerService communityAnswerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping ("/create/{communityId}")
    public String createAnswer(@PathVariable("communityId") Long communityId, Model model,
                               @Validated @ModelAttribute("CommunityAnswerAddForm") CommunityAnswerAddForm communityAnswerAddForm, BindingResult bindingResult,
                               Principal principal) {
        Community community = communityService.findById(communityId);
        User user = userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("community", community);
            return "community/viewCommunity";
        }
        communityAnswerService.create(community, communityAnswerAddForm.getContent(), user);
        return "redirect:/community/{communityId}";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{communityAnswerId}/edit")
    public String editCA(@PathVariable("communityAnswerId") Long communityAnswerId, @ModelAttribute("communityAnswerAddForm") CommunityAnswerAddForm communityAnswerAddForm,
                         Principal principal) {
        CommunityAnswer findCA = communityAnswerService.findCA(communityAnswerId);
        if (!findCA.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        return "community/editCommunityAnswer";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{communityAnswerId}/edit")
    public String edit(@Validated @ModelAttribute("communityAnswerAddForm") CommunityAnswerAddForm communityAnswerAddForm, BindingResult bindingResult,
                               @PathVariable("communityAnswerId") Long communityAnswerId, Principal principal) {
        log.info("CA UPDATE START");
        if (bindingResult.hasErrors()) {
            return "community/editCommunityAnswer";
        }
        CommunityAnswer communityAnswer = communityAnswerService.findCA(communityAnswerId);
        if (!communityAnswer.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        communityAnswerService.editCA(communityAnswer, communityAnswerAddForm.getContent());
        return "redirect:/community/" + communityAnswer.getCommunity().getId();
    }

    @GetMapping("/{communityAnswerId}/delete")
    public String delete(@PathVariable("communityAnswerId") Long communityAnswerId, Principal principal) {
        CommunityAnswer communityAnswer = communityAnswerService.findCA(communityAnswerId);
        if (!communityAnswer.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다");
        }
        communityAnswerService.delete(communityAnswer);
        return "redirect:/community/" + communityAnswer.getCommunity().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{communityAnswerId}/recommend")
    public String recommend(Principal principal, @PathVariable("communityAnswerId") Long communityAnswerId) {
        CommunityAnswer communityAnswer = communityAnswerService.findCA(communityAnswerId);
        User user = userService.getUser(principal.getName());
        communityAnswerService.recommend(communityAnswer.getId(), user.getId());
        return "redirect:/community/" + communityAnswer.getCommunity().getId();
    }
}

