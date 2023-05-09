package project7.clonecoding.game.entity;

import lombok.extern.slf4j.Slf4j;

import project7.clonecoding.comment.entity.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
//@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class Game{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column//게임 이름
    private String gameTitle;

    @Column//게임 가격(예시: 4,000원)
    private Integer gamePrice;

    @Column//게임 점수(후시 4.9점)
    private String star;

    @Column//게임 난이도
    private String difficulty;

    //@Convert(converter = StringArrayConverter.class) // List<String> 때 사용
    @OneToMany(mappedBy = "game",cascade = CascadeType.REMOVE)
    private Set<Images> gameImg = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();
}
