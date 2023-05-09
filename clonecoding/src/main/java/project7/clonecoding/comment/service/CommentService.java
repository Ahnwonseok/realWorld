package project7.clonecoding.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project7.clonecoding.comment.dto.CommentRequestDto;
import project7.clonecoding.comment.dto.CommentResponseDto;
import project7.clonecoding.comment.entity.Comment;
import project7.clonecoding.comment.repository.CommentRepository;
import project7.clonecoding.game.dto.ResponseDto;
import project7.clonecoding.game.entity.Game;
import project7.clonecoding.game.repository.GameRepository;
import project7.clonecoding.user.UserRepository;
import project7.clonecoding.user.entity.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Transactional
    public ResponseDto createComments(Long gameId, CommentRequestDto commentRequestDto, Users user) throws SQLException {
        // 사용자 확인하기
        Users userFind = userRepository.findByUserName(user.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
        Game game = gameRepository.findById(gameId).orElseThrow(
                () -> new IllegalArgumentException("해당 게임이 존재하지 않습니다.")
        );

        //댓글저장
        Comment comment = new Comment(commentRequestDto,userFind,game);

        commentRepository.save(comment);

        return new ResponseDto("댓글 작성 완료");
    }

    @Transactional
    public ResponseDto updateComment(Long commentId, CommentRequestDto requestDto, Users user) {

        //DB에서 댓글 찾아오기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        //본인이 쓴 댓글인지 확인
        if (user.getId() != comment.getUser().getId()) {
            throw new IllegalArgumentException("본인의 댓글만 수정 가능합니다");
        }

        comment.update(requestDto);

        return new ResponseDto("댓글 수정완료");
    }

    @Transactional
    public ResponseDto deleteComment(Long commentId, Users user) {

        //DB에서 댓글 찾아오기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글은 없습니다.")
        );

        //본인이 쓴 댓글인지 확인
        if (user.getId() != comment.getUser().getId()) {
            throw new IllegalArgumentException("본인의 댓글만 수정 가능합니다");
        }

        //댓글 삭제
        commentRepository.deleteById(commentId);
        return new ResponseDto("댓글 삭제 성공");
    }

    //게임 아이디에 맞는 댓글 전송
    public List<CommentResponseDto> getComment(Long gameId) {
        //해당 게임이 존재하는지 확인
        Game game = gameRepository.findById(gameId).orElseThrow(
                () -> new IllegalArgumentException("해당 게임이 존재하지 않습니다.")
        );

        //DB에서 댓글 찾아오기
        List<Comment> comments = commentRepository.findAllByGame(game);

        List<CommentResponseDto> list = new ArrayList<>();
        for(Comment comment : comments){
            list.add(new CommentResponseDto(comment));
        }

        return list;
    }
}