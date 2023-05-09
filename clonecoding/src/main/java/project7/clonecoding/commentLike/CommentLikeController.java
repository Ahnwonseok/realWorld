package project7.clonecoding.commentLike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project7.clonecoding.security.UserDetailsImpl;
import project7.clonecoding.user.dto.ResponseMsgDto;

@Controller
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/commentLike/{commentId}")
    public ResponseEntity<ResponseMsgDto> commentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentLikeService.commentLike(commentId,userDetails.getUser()));
    }
}
