package project7.clonecoding.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project7.clonecoding.jwt.JwtUtil;
import project7.clonecoding.user.dto.ResponseMsgDto;
import project7.clonecoding.user.dto.UserRequestDto;
import project7.clonecoding.user.entity.Users;
import project7.clonecoding.user.entity.UserRoleEnum;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public ResponseMsgDto signup(UserRequestDto userRequestDto, HttpServletResponse response) {

        String password = userRequestDto.getPassword();
        String passwordCheck = userRequestDto.getPasswordCheck();

        UserRoleEnum role = UserRoleEnum.USER;

        // 비밀번호 길이 체크 & 비밀번호와 비밀번호 체크 입력값 확인 후 같을 경우 암호화
        if(password.length()<8){
            throw new IllegalArgumentException("8글자 이상으로 만들어주세요.");
        }

        if(!matches(password,passwordCheck)){
            throw new IllegalArgumentException("비밀번호가 서로 다릅니다.");
        }

        String encodingPassword = passwordEncoder.encode(password);


        //아이디&이메일 중복 확인
        Optional<Users> found = userRepository.findByUserName(userRequestDto.getUserName());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Users emails = userRepository.findByEmail(userRequestDto.getEmail());
        if (emails != null) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }


        //제한 사항
        String nameCheck = userRequestDto.getUserName();
        String nameRegexp = "^[가-힣a-zA-Z0-9._-]{2,20}$";
        if(!nameCheck.matches(nameRegexp)){
            throw new IllegalArgumentException("아이디는 2~20자 내외여야합니다.");
        }

        String emailCheck = userRequestDto.getEmail();
        String emailRegexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!emailCheck.matches(emailRegexp)){
            throw new IllegalArgumentException("이메일 형식으로 등록해주세요.");
        }


        Users signRequest = new Users(userRequestDto, encodingPassword, role);
        userRepository.save(signRequest);

        response.setHeader("Access-Control-Allow-Origin", "http://hanghea99cloncoding7.s3-website.ap-northeast-2.amazonaws.com");

        return new ResponseMsgDto("회원 가입 성공", HttpStatus.OK.value());
    }

    @Transactional
    public ResponseMsgDto login(UserRequestDto userRequestDto, HttpServletResponse response) {

        String password = userRequestDto.getPassword();

        Users users = userRepository.findByEmail(userRequestDto.getEmail());//이메일 오류 시
        if (users == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }

        if (!passwordEncoder.matches(password, users.getPassword())) { // 비밀번호 입력 오류 시
        users.setFailCount(users.getFailCount()+1); //로그인 실패 시 실패 횟수 1 늘림
        return new ResponseMsgDto("비밀번호가 일치하지 않습니다. "+ iDStop(users.getFailCount()),HttpStatus.BAD_REQUEST.value());
        }

        // 토큰 헤더에 올리고 응답해주기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(users.getUserName(), users.getRole()));

        int clear = users.getFailCount();users.setFailCount(0); //로그인 성공 시 유저가 가지고 있던 로그인 실패횟수를 리셋시킴
        return new ResponseMsgDto("로그인 성공.마지막 로그인 전까지 로그인 실패횟수는 "+clear+" 회 입니다.",HttpStatus.OK.value());
    }

    @Transactional
    public Integer changeData(Long id, UserRequestDto userRequestDto,Users users) {

        String password = userRequestDto.getPassword();

        if(password.length()<8)
            return HttpStatus.BAD_REQUEST.value();

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        users = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        users.changePassword(encodedPassword);

        return HttpStatus.OK.value();
    }

    @Transactional
    public Integer deleteUsersData(Long id, UserRequestDto userRequestDto,Users users){
        users = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(users.getUserName().equals(userRequestDto.getUserName())) {
            userRepository.delete(users);
        }
        return HttpStatus.OK.value();
    }

    public String iDStop(int i){
        if (i>4){
            return ("현재 실패 횟수는 "+i+" 입니다. 본인이 맞으신가요?");}else{
            return ("현재 실패 횟수는 "+ i+" 입니다.");
        }
    }
}
