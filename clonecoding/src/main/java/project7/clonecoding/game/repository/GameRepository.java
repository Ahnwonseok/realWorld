package project7.clonecoding.game.repository;

import org.springframework.data.jpa.repository.Query;
import project7.clonecoding.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>, GameCustomRepository {

//    @Query("select p.gameTitle from Game p")
    List<Game> findAllByOrderByIdDesc();

    List<Game> findAllByOrderByStarDesc();
    List<Game> findAllByGamePriceEqualsOrderByIdDesc(int gamePrice);
    List<Game> findAllByGamePriceGreaterThanOrderByGamePriceDesc(int gamePrice);

}
