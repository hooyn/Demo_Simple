package solution.demo.domain.webChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import solution.demo.domain.webChat.dto.ChatRoomDto;
import solution.demo.domain.webChat.service.ChatService;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final HttpServletRequest request;
    private final ChatService chatService;

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return

    // 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
    // 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
    @GetMapping("/")
    public ResponseEntity<?> goChatRoom(){

        List<ChatRoomDto> allRoom = chatService.findAllRoom();
        log.debug("SHOW ALL ChatList {}", allRoom);

        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "대화 참가 사용자 수 반환완료하였습니다.", allRoom), HttpStatus.OK);
    }

}
