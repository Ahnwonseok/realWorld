package project7.clonecoding.game.repository;

import project7.clonecoding.game.dto.GameResponseDto;

import java.util.List;

public interface GameCustomRepository {
    List<GameResponseDto> getGames();
    List<GameResponseDto> getGamesByStar();
    List<GameResponseDto> getFreeGame();
}
