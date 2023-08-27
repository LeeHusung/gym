package workout.gym.web.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import workout.gym.common.exception.DataNotFoundException;
import workout.gym.domain.comment.CommentService;
import workout.gym.domain.community.CommunityService;
import workout.gym.domain.entity.Comment;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.User;
import workout.gym.domain.user.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommunityService communityService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/community/{id}")
    public String createCommunityCommentForm(CommentForm commentForm) {
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/community/{id}")
    public String createCommunityComment(@PathVariable("id") Long id, @ModelAttribute("commentForm") @Validated CommentForm commentForm, BindingResult bindingResult,
                                         Principal principal) {
        Community community = communityService.findById(id);
        User user = userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            log.info("bindingResult error = {}", bindingResult);
            return "comment_form";
        }
        Comment comment = commentService.createInCommunity(community, commentForm.getContent(), user);
        return "redirect:/community/" + comment.getQuestionId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/community/{id}")
    public String updateCommunityCommentForm(CommentForm commentForm, @PathVariable("id") Long id, Principal principal) {
        Comment findComment = commentService.findById(id).orElseThrow(() -> new DataNotFoundException("Comment Not Found"));
        if (!findComment.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 수정 권한이 없습니다.");
        }
        commentForm.setContent(findComment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/community/{id}")
    public String updateCommunityComment(@PathVariable("id") Long id, @ModelAttribute("commentForm") @Validated CommentForm commentForm, BindingResult bindingResult,
                                         Principal principal) {
        Comment findComment = commentService.findById(id).orElseThrow(() -> new DataNotFoundException("Comment Not Found"));
        if (!findComment.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 수정 권한이 없습니다.");
        }
        commentService.updateCommentInCommunity(id, commentForm.getContent());
        return "redirect:/community/" + findComment.getQuestionId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/community/{id}")
    public String deleteCommunityComment(@PathVariable("id") Long id, Principal principal) {
        Comment findComment = commentService.findById(id).orElseThrow(() -> new DataNotFoundException("Comment Not Found"));
        if (!findComment.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 삭제 권한이 없습니다.");
        }
        commentService.delete(findComment);
        return "redirect:/community/" + findComment.getQuestionId();
    }
}
