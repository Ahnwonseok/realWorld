package project7.clonecoding.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import project7.clonecoding.game.dto.*;
import project7.clonecoding.game.entity.Game;
import project7.clonecoding.game.repository.GameRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    //게임 정보 단건으로 보내기
    public GameResponseDto getGame(@PathVariable Long id) {
        Game game = gameRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );
        return new GameResponseDto(game);
    }

    //게임 정보 전체 조회
    public List<GameResponseDto> getGames() {
        List<GameResponseDto> list = gameRepository.getGames();
        return list;
    }

    //전체 게시글 조회하기(평점 순으로)
    public List<GameResponseDto> getGamesStar() {
        List<GameResponseDto> list = gameRepository.getGamesByStar();
        return list;
    }

    public List<GameResponseDto> getFreeGames() { //무료게임
        List<GameResponseDto> list = gameRepository.getFreeGame();
        return list;
    }

}