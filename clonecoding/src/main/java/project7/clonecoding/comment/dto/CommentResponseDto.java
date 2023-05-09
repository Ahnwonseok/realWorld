package project7.clonecoding.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project7.clonecoding.comment.entity.Comment;
import project7.clonecoding.game.entity.Game;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
//    private Optional<Comment> comment;
//    private Boolean isSoil;
    private Long id;
    private String comment;
    private Boolean isSpoil;
    private String user;
    private Integer stars;
    private String dates;


    public CommentResponseDto(Comment comment) {//평점순
        this.id = comment.getId();
        this.comment = comment.getDescription();
        this.isSpoil = comment.getIsSpoil();
        this.user = comment.getUserName();
        this.stars = comment.getStars();
        this.dates = comment.getCreatedAt();
    }

}
