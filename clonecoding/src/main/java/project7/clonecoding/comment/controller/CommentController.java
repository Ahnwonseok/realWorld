package project7.clonecoding.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project7.clonecoding.comment.dto.CommentRequestDto;
import project7.clonecoding.comment.dto.CommentResponseDto;
import project7.clonecoding.comment.service.CommentService;
import project7.clonecoding.game.dto.ResponseDto;
import project7.clonecoding.security.UserDetailsImpl;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comments/{gameId}")
    public ResponseDto createComments(@PathVariable Long gameId,
                                      @RequestBody CommentRequestDto commentRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        return commentService.createComments(gameId, commentRequestDto, userDetails.getUser());
    }

    //댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseDto deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails.getUser());
    }

    //댓글 받기
    @GetMapping("/comments/{gameId}")
    public List<CommentResponseDto> getComment(@PathVariable Long gameId) {
        return commentService.getComment(gameId);
    }

}
