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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workout.gym.domain.entity.Community;
import workout.gym.domain.community.CommunityFileService;
import workout.gym.domain.community.CommunityService;
import workout.gym.web.community.form.CommunityAddForm;
import workout.gym.web.community.form.CommunityUpdateForm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityFileService communityFileService;

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
    public String saveCommunity(@Validated @ModelAttribute("communityAddForm") CommunityAddForm communityAddForm,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/community/addCommunity";
        }
        Community community = communityService.save(communityAddForm);
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
        Community updateCommunity = communityService.UpdateCommunity(id, communityUpdateForm);
        redirectAttributes.addAttribute("communityId", updateCommunity.getId());
        return "redirect:/community/{communityId}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        return "redirect:/community";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + communityFileService.getFullPath(filename));
    }
}
