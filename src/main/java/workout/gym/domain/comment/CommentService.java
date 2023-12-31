package workout.gym.domain.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.entity.Comment;
import workout.gym.domain.entity.Community;
import workout.gym.domain.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment createInCommunity(Community community, String content, User user) {
        Comment comment = Comment.builder()
                .user(user)
                .content(content)
                .community(community)
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void updateCommentInCommunity(Long commentId, String content) {
        log.info("updateComment 시작");
        Comment comment = commentRepository.findById(commentId).get();
        comment.updateComment(content);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
