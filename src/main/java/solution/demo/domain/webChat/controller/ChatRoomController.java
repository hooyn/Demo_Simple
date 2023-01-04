package solution.demo.domain.webChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import solution.demo.domain.webChat.dto.ChatRoomDto;
import solution.demo.domain.webChat.service.ChatService;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final HttpServletRequest request;
    private final ChatService chatService;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public ResponseEntity<?> createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType) {

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;
        room = chatService.createChatRoom(name, Integer.parseInt(maxUserCnt));


        log.info("CREATE Chat Room [{}]", room);
        return new ResponseEntity<>(new ResponseWrapper(request, HttpStatus.OK,
                true, "채팅방 생성이 완료되었습니다.", room), HttpStatus.OK);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId){
        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatService.delChatRoom(roomId);

        return "redirect:/";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){
        return chatService.chkRoomUserCnt(roomId);
    }
}
