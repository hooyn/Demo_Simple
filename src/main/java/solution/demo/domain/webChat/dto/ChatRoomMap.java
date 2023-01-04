package solution.demo.domain.webChat.dto;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ChatRoomMap {

    private static ChatRoomMap chatRoomMap = new ChatRoomMap();
    private Map<String, ChatRoomDto> chatRooms = new LinkedHashMap<>();

    private ChatRoomMap(){}

    public static ChatRoomMap getInstance() { return chatRoomMap; }
}
