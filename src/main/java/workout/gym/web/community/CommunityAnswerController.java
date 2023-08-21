package workout.gym.web.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import workout.gym.common.security.PrincipalDetails;
import workout.gym.domain.community.CommunityService;
import workout.gym.domain.community.answer.CommunityAnswerService;
import workout.gym.domain.entity.Community;
import workout.gym.web.community.form.CommunityAnswerAddForm;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommunityAnswerController {

    private final CommunityService communityService;
    private final CommunityAnswerService communityAnswerService;

    @PostMapping ("/communityAnswer/create/{communityId}")
    public String createAnswer(@PathVariable("communityId") Long communityId, Model model,
                               @Validated @ModelAttribute("CommunityAnswerAddForm") CommunityAnswerAddForm communityAnswerAddForm, BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Community community = communityService.findById(communityId);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("community", community);
            return "community/viewCommunity";
        }
        communityAnswerService.create(community, communityAnswerAddForm.getContent(), principalDetails);
        model.addAttribute("user", principalDetails.getUser());
        return "redirect:/community/{communityId}";
    }
}
