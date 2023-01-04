package solution.demo.domain.webChat.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {

    private String roomId;
    private String roomName;
    private int userCount;
    private int maxUserCount;
    private Map<String, ?> userList;
}
