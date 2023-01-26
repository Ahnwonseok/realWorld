package project7.clonecoding.game.controller;

import project7.clonecoding.game.dto.GameRequestDto;
import project7.clonecoding.game.dto.GameResponseDto;
import project7.clonecoding.game.dto.ResponseDto;
import project7.clonecoding.game.dto.StarRequestDto;
import project7.clonecoding.game.service.GameService;
import project7.clonecoding.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = {"Game"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameService gameService;

    //게시글 작성
    @ApiOperation(value = "게시글 추가(게임등록)", notes = "게임을 올린다.")
    @PostMapping("/games")
    public ResponseDto createGame(@RequestBody GameRequestDto gameRequestDto) {
//        log.info(gameRequestDto.getImageResponseDto().getFullName());
        return gameService.createGame(gameRequestDto);
    }
    //전체 목록 조회
    @ApiOperation(value = "게임 목록 전체 조회", notes = "등록된 게시글 목록 전체를 조회한다.")
    @GetMapping("/games/list")
    public List<GameResponseDto> getGames() {
        return gameService.getGames();
    }
    //게시글 id로 게시글 단건 조회
    @ApiOperation(value = "게시글 단일 상세 조회", notes = "게시글의 id로 목록을 상세 조회한다.")
        @GetMapping("/games/{id}")
    public GameResponseDto getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }

    //게시글 id로 게시글 수정하기
    @ApiOperation(value = "게시글 수정", notes = "게시글의 id로 찾아 내용을 수정한다.")
    @PutMapping("/games/{id}")
    public ResponseDto updateGame(@PathVariable Long id, @RequestBody GameRequestDto request) {
        return gameService.updateGame(id,request);
    }
    //게시글 id로 게시글 수정하기
    @ApiOperation(value = "평점 매기기", notes = "게시글의 id로 찾아서 그것의 별점을 부여한다.")
    @PatchMapping("/games/{id}")
    public ResponseDto rateStar(@PathVariable Long id, @RequestBody StarRequestDto star) {
        return gameService.rateStar(id,star);
    }
    //id로 게시물 삭제
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 게시글의 id로 찾아 삭제한다.")
    @DeleteMapping("/games/{id}")
    public ResponseDto deleteGame(@PathVariable Long id) {
        return gameService.DeleteGame(id);
    }
}
