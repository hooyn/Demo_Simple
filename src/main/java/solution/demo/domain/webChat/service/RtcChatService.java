package solution.demo.domain.webChat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import solution.demo.domain.webChat.dto.ChatRoomDto;
import solution.demo.domain.webChat.dto.ChatRoomMap;
import solution.demo.domain.webChat.dto.WebSocketMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RtcChatService {

    public ChatRoomDto createChatRoom(String roomName, int maxUserCnt) {
        // roomName 와 roomPwd 로 chatRoom 빌드 후 return
        ChatRoomDto room = ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .userCount(0) // 채팅방 참여 인원수
                .maxUserCount(maxUserCnt) // 최대 인원수 제한
                .build();

        room.setUserList(new HashMap<String, WebSocketSession>());

        // map 에 채팅룸 아이디와 만들어진 채팅룸을 저장
        ChatRoomMap.getInstance().getChatRooms().put(room.getRoomId(), room);

        return room;
    }

    public Map<String, WebSocketSession> getClients(ChatRoomDto room) {
        Optional<ChatRoomDto> roomDto = Optional.ofNullable(room);

        return (Map<String, WebSocketSession>) roomDto.get().getUserList();
    }

    public Map<String, WebSocketSession> addClient(ChatRoomDto room, String name, WebSocketSession session) {
        Map<String, WebSocketSession> userList = (Map<String, WebSocketSession>) room.getUserList();
        userList.put(name, session);
        return userList;
    }

    // userList 에서 클라이언트 삭제
    public void removeClientByName(ChatRoomDto room, String userUUID) {
        room.getUserList().remove(userUUID);
    }

    // 유저 카운터 return
    public boolean findUserCount(WebSocketMessage webSocketMessage){
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(webSocketMessage.getData());
        log.info("ROOM COUNT : [{} ::: {}]",room.toString(),room.getUserList().size());
        return room.getUserList().size() > 1;
    }
}
