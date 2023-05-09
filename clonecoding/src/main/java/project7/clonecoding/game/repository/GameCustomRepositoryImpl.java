package project7.clonecoding.game.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project7.clonecoding.game.dto.GameResponseDto;
import project7.clonecoding.game.entity.Game;
import project7.clonecoding.game.entity.QImages;
import static project7.clonecoding.game.entity.QImages.images;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static project7.clonecoding.game.entity.QGame.game;
import static project7.clonecoding.comment.entity.QComment.*;
import static project7.clonecoding.user.entity.QUsers.users;

public class GameCustomRepositoryImpl implements GameCustomRepository{

    private final JPAQueryFactory queryFactory;

    public GameCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GameResponseDto> getGames() {

        List<Game> gameList = queryFactory
                .selectDistinct(game)
                .from(game)
                .leftJoin(game.comments, comment).fetchJoin() //game과 연관된 comment를 한 번에
                .fetch();

        List<GameResponseDto> responseDtos = new ArrayList<>();

        for(Game game : gameList){
            responseDtos.add(new GameResponseDto(game));
        }

        return responseDtos;
    }

    @Override
    public List<GameResponseDto> getFreeGame() {
        List<Game> gameList = queryFactory
                .selectDistinct(game)
                .from(game)
                .leftJoin(game.comments, comment).fetchJoin()
                .where(game.gamePrice.eq(0))
                .fetch();

        List<GameResponseDto> responseDtos = new ArrayList<>();

        for(Game game : gameList){
            responseDtos.add(new GameResponseDto(game));
        }

        return responseDtos;
    }

    @Override
    public List<GameResponseDto> getGamesByStar() {
        List<Game> gameList = queryFactory
                .selectDistinct(game)
                .from(game)
                .leftJoin(game.comments, comment).fetchJoin()
                .orderBy(game.star.desc())
                .fetch();

        List<GameResponseDto> responseDtos = new ArrayList<>();

        for(Game game : gameList){
            responseDtos.add(new GameResponseDto(game));
        }

        return responseDtos;
    }
}