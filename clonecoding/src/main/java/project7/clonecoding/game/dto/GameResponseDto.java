package project7.clonecoding.game.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import project7.clonecoding.comment.entity.Comment;
import project7.clonecoding.game.entity.Game;
import project7.clonecoding.game.entity.Images;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class GameResponseDto {
    private Long id;
    private String gameTitle;
    private String star;
    private Set<Images> images;
    private Set<Comment> comments;

    public GameResponseDto(Game game) {//평점순
        id = game.getId();
        gameTitle = game.getGameTitle();
        star = game.getStar();
        images = game.getGameImg();
        comments = game.getComments();
    }

}
