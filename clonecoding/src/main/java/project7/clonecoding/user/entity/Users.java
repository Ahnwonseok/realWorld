package project7.clonecoding.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project7.clonecoding.comment.entity.Comment;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import project7.clonecoding.timestamp.Timestamp;
import project7.clonecoding.user.dto.UserRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //댓글과 연관관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private int failCount;

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }



    public Users(UserRequestDto userRequestDto, String password, UserRoleEnum role){
        this.userName = userRequestDto.getUserName();
        this.email = userRequestDto.getEmail();
        this.password = password;
        this.role = UserRoleEnum.USER;
        this.failCount = 0;
    }
    public void changePassword(String password){
        this.password = password;
    }
}

