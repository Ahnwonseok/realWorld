package project7.clonecoding.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import project7.clonecoding.comment.dto.CommentRequestDto;
import project7.clonecoding.game.entity.Game;
import project7.clonecoding.game.entity.StringArrayConverter;
import project7.clonecoding.timestamp.Timestamp;
import project7.clonecoding.user.entity.Users;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //댓글내용
//    @Column(columnDefinition = "json")
//    @Convert(converter = StringArrayConverter.class)
    @Column(nullable = false)
    private String description;

    @Column
    private Boolean isSpoil;

    @Column
    private String userName;

    @Column
    private Integer stars;

    @ColumnDefault("0")
    private Integer likeCount;

    //유저와 다대일 매핑
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    //게임과 다대일 매핑
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    public Comment (CommentRequestDto commentDto, Users user, Game game) {
        this.description = commentDto.getComment();
        this.isSpoil = commentDto.getIsSpoil();
        this.stars = commentDto.getStars();
        this.userName = user.getUserName();
        this.user = user;
        this.game = game;
    }

    public void update(CommentRequestDto requestDto){
        this.description = requestDto.getComment();
    }

    public void updateLikeCount(int count){
        likeCount += count;
    }
}
