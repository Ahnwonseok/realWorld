package project7.clonecoding.commentLike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project7.clonecoding.comment.entity.Comment;
import project7.clonecoding.user.entity.Users;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public CommentLike(Comment comment, Users user) {
        this.comment = comment;
        this.user = user;
    }

}
