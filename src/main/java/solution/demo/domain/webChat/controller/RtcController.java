package solution.demo.domain.webChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import solution.demo.domain.webChat.dto.WebSocketMessage;
import solution.demo.domain.webChat.service.RtcChatService;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RtcController {

    private final HttpServletRequest request;
    private final RtcChatService rtcChatService;

    @PostMapping("/webrtc/usercount")
    public ResponseEntity<?> webRTC(@ModelAttribute WebSocketMessage webSocketMessage) {
        log.info("MESSAGE : {}", webSocketMessage.toString());

        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "대화 참가 사용자 수 반환완료하였습니다.", rtcChatService.findUserCount(webSocketMessage)), HttpStatus.OK);
    }
}

