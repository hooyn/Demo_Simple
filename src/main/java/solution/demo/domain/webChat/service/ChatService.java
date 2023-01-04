package solution.demo.domain.webChat.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import solution.demo.domain.webChat.dto.ChatRoomDto;
import solution.demo.domain.webChat.dto.ChatRoomMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class ChatService {
    private final RtcChatService rtcChatService;

    // 전체 채팅방 조회
    public List<ChatRoomDto> findAllRoom(){
        // 채팅방 생성 순서를 최근순으로 반환
        List<ChatRoomDto> chatRooms = new ArrayList<>(ChatRoomMap.getInstance().getChatRooms().values());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // roomID 기준으로 채팅방 찾기
    public ChatRoomDto findRoomById(String roomId){
        return ChatRoomMap.getInstance().getChatRooms().get(roomId);
    }

    // roomName 로 채팅방 만들기
    public ChatRoomDto createChatRoom(String roomName, int maxUserCnt){

        ChatRoomDto room;
        room = rtcChatService.createChatRoom(roomName, maxUserCnt);

        return room;
    }

//    // 채팅방 비밀번호 조회
//    public boolean confirmPwd(String roomId, String roomPwd) {
//        return roomPwd.equals(ChatRoomMap.getInstance().getChatRooms().get(roomId).getRoomPwd());
//
//    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId){
        log.info("cnt {}",ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        return room.getUserCount() + 1 <= room.getMaxUserCount();
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId){

        try {
            ChatRoomMap.getInstance().getChatRooms().remove(roomId);
            log.info("삭제 완료 roomId : {}", roomId);
        } catch (Exception e) { // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());
        }
    }
}
